package tfc.producer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tfc.messages.MessageSender;
import tfc.producer.querier.Querier;
import twitter4j.*;

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
        
        protected void setQuery(String pQuery){
            query = new Query(pQuery);
            //todo: think the number of responses in query to match with the limit of request -balance
            query.setCount(50);
            //query.setCount(1);
        }
        
        public void run(){
            boolean noMoreResults=false;

            try {
                while (!noMoreResults) {
                    QueryResult result = null;
                    try {
                        result = twitterQuerier.executeQuery(query);
                        if (result.hasNext()) query=result.nextQuery();
                        noMoreResults=!result.hasNext();
                        //TODO remove this comments TESTING ONLY:
                        noMoreResults=true;
                        workQueue.put(result);
                    }catch (TwitterException e) {
                        log.error("Error asking Twitter API ", e);
                        RateLimitStatus rateLimitStatus = e.getRateLimitStatus();
                        long timeToRetry = rateLimitStatus.getSecondsUntilReset() * 1000l;
                        Thread.sleep(timeToRetry);
                    }
                }
            } catch (InterruptedException e) {
                log.error("Interrupted! ", e);
                this.interrupt();
            }
        }
        
    }

    class QueryResultConsumer extends Thread{
        public void run(){
            while (true){
                try {
                    QueryResult result = workQueue.take();
                    sendMessages(result.getTweets());
                } catch (InterruptedException e) {
                    log.error("Interrupted! ", e);
                    this.interrupt();
                }
            }
        }

        private void sendMessages(List<Status> processedResults) throws InterruptedException {
            int messagesSent=0;
            for (Status processedResult : processedResults) {
                messageSender.sendMessage(processedResult);
                messagesSent++;
            }
            log.info("Sent " + messagesSent + " messages to the Queue.");
        }
    }

    public void handleQuery(String query) {
        log.info("Handling query " + query);
        QueryResultProducer producer = new QueryResultProducer();
        producer.setQuery(query);
        producer.start();
        
        QueryResultConsumer consumer = new QueryResultConsumer();
        consumer.start();

        while (true){
            if (!producer.isAlive()&&!consumer.isAlive()) break;
        }
        log.info("Query handle done.");
    }
}
