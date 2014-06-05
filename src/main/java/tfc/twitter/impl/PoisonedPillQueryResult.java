package tfc.twitter.impl;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.RateLimitStatus;
import twitter4j.Status;

import java.util.List;

/**
 * TFC
 * User: Esaú González
 * Date: 30/05/14
 * Time: 20:19
 */
public class PoisonedPillQueryResult implements QueryResult {
    @Override
    public long getSinceId() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long getMaxId() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getRefreshURL() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getCount() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public double getCompletedIn() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getQuery() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Status> getTweets() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Query nextQuery() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean hasNext() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public RateLimitStatus getRateLimitStatus() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getAccessLevel() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
