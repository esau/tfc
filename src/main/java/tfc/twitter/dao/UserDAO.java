package tfc.twitter.dao;

import org.springframework.stereotype.Repository;
import tfc.twitter.TwitterManager;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.api.FriendsFollowersResources;
import twitter4j.api.UsersResources;

/**
 * TFC
 * User: Esaú González
 * Date: 27/04/14
 * Time: 17:56
 */
@Repository("TUserDAO")
public class UserDAO {
    Twitter twitter;
    UsersResources usersResources;
    FriendsFollowersResources friendsFollowersResources;

    public UserDAO() {
        twitter = TwitterFactory.getSingleton();
        usersResources = twitter.users();
        friendsFollowersResources = twitter.friendsFollowers();
    }
    
    public User findUserById(String pId) throws InterruptedException {
        User toReturn= null;

        do {
            try {
                toReturn = usersResources.showUser(new Long(pId));
            } catch (TwitterException e) {
                TwitterManager.handleTwitterException(e);
            }
        } while (toReturn==null);
        return toReturn;
    }

}
