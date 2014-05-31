package tfc.producer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tfc.messages.MessageSender;
import tfc.producer.querier.Querier;
import tfc.twitter.TwitterManager;
import tfc.twitter.impl.PoisonedPillQueryResult;
import tfc.twitter.impl.PoisonedPillStatus;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.TwitterException;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * TDP Grup6
 * User: Esaú González
 * Date: 27/04/14
 * Time: 17:36
 */
@Component
public class QueryHandler {

    private static Logger log = Logger.getLogger(QueryHandler.class);

    final LinkedBlockingQueue<QueryResult> workQueue = new LinkedBlockingQueue<QueryResult>(50);
    @Autowired
    MessageSender messageSender;
    @Autowired
    Querier twitterQuerier;

    public QueryHandler() {
        log.debug("QueryHandler created");
    }
    
    class QueryResultProducer extends Thread{
        
        private Query query;
        private int queryLimit;
        private int resultsCount=50;
        
        protected void setQuery(String pQuery){
            query = new Query(pQuery);
            //todo: think the number of responses in query to match with the limit of request -balance
            query.setCount(resultsCount);
            //query.setCount(1);
        }
        
        public void run(){
            boolean noMoreResults=false;
            QueryResult result;
            try {
                int resultsProcessed=0;
                while (!noMoreResults && queryLimit!=-1?resultsProcessed<queryLimit:true) {

                    try {
                        result = twitterQuerier.executeQuery(query);
                        if (result.hasNext()) query=result.nextQuery();
                        resultsProcessed = resultsProcessed + result.getCount();
                        noMoreResults=!result.hasNext();
                        //TODO remove this comments TESTING ONLY:
                        //noMoreResults=true;
                        workQueue.put(result);
                    }catch (TwitterException e) {
                        TwitterManager.handleTwitterException(e);
                    }
                }
                //sending no more results signal
                result = new PoisonedPillQueryResult();
                workQueue.put(result);
            } catch (InterruptedException e) {
                log.error("Interrupted! ", e);
                this.interrupt();
            }
        }

        public void setQueryLimit(int limit) {
            queryLimit = limit;
        }
    }

    class QueryResultConsumer extends Thread{
        public void run(){
            while (true){
                try {
                    QueryResult result = workQueue.take();
                    if (result.getTweets()!=null){
                        sendMessages(result.getTweets());
                    } else {
                        log.info("No more results");
                        //sending no more results signal
                        sendMessages(result.getTweets());
                        break;
                    }
                } catch (InterruptedException e) {
                    log.error("Interrupted! ", e);
                    this.interrupt();
                }
            }
        }

        private void sendMessages(List<Status> processedResults) throws InterruptedException {
            int messagesSent=0;
            if (processedResults!=null) {
                for (Status processedResult : processedResults) {
                    messageSender.sendMessage(processedResult);
                    messagesSent++;
                }
                log.info("Sent " + messagesSent + " messages to the Queue.");
            } else {
                log.info("No more messages to send");
                messageSender.sendMessage(new PoisonedPillStatus());
            }
        }
    }

    /**
     * Handles the Twitter Search Query given by param with a limit of results. If limit=-1 there's no limit.
     * @param query String query to execute with Twitter Search Query
     * @param limit int max number of results for the query
     */
    public void handleQuery(String query, int limit) {
        log.info("Handling query: [" + query + "]");
        QueryResultProducer producer = new QueryResultProducer();
        producer.setQuery(query);
        producer.setQueryLimit(limit);
        producer.start();
        
        QueryResultConsumer consumer = new QueryResultConsumer();
        consumer.start();

        while (true){
            if (!producer.isAlive()&&!consumer.isAlive()) break;
        }
        log.info("Query handle done.");
    }
}
