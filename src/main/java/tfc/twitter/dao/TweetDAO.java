package tfc.twitter.dao;

import org.springframework.stereotype.Repository;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.api.TweetsResources;

/**
 * TDF
 * User: Esaú González
 * Date: 27/04/14
 * Time: 17:55
 */
@Repository("TTweetDAO")
public class TweetDAO {

    Twitter twitter;
    TweetsResources tweetsResources;

    public TweetDAO() {
        twitter = TwitterFactory.getSingleton();
        tweetsResources = twitter.tweets();
    }

    public Status findStatusById(long repliedStatusId) throws TwitterException {
        return tweetsResources.showStatus(repliedStatusId);
    }
}