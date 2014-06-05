package tfc.producer.querier;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.TwitterException;

/**
 * TFC
 * User: Esaú González
 * Date: 27/04/14
 * Time: 17:37
 */
public interface Querier {
    
    public QueryResult executeQuery(Query pQuery) throws TwitterException;
}
