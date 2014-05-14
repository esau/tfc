package tfc.producer.querier.impl;

import org.springframework.stereotype.Service;
import tfc.producer.querier.Querier;
import twitter4j.*;

/**
 * TDF
 * User: Esaú González
 * Date: 27/04/14
 * Time: 17:39
 */
@Service
public class TwitterQuerierImpl implements Querier{
    private static Logger log = Logger.getLogger(TwitterQuerierImpl.class);
    private Twitter twitter;

    public TwitterQuerierImpl() {
        twitter = TwitterFactory.getSingleton();
    }

    @Override
    public QueryResult executeQuery(Query pQuery) throws TwitterException {
        log.debug("Starting query");


        int resultNum=0;

        QueryResult result = null;
        log.debug("Querying: " + pQuery.getQuery());
        result = twitter.search(pQuery);
        log.debug("Results: " +result.getCount());
        log.debug("Query done");
        log.debug("Has next? " + result.hasNext());


            

        return result;
    }
}
