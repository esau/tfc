package tfc.twitter.dao;

import org.springframework.stereotype.Repository;
import tfc.twitter.TwitterManager;
import twitter4j.*;
import twitter4j.api.FriendsFollowersResources;
import twitter4j.api.UsersResources;

import java.util.ArrayList;
import java.util.List;

/**
 * TDF
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

    /**
     * Finds all user's follower id's. If the Twitter request limit is reached it will wait until more requests are available
     * and repeat the request.
     * @param pId String user id
     * @return List<String> containing the id's of the followers
     * @throws InterruptedException if the execution is interrupted.
     */
    public List<String> findUserFollowers(String pId) throws InterruptedException {
        List<String> followers = new ArrayList<String>();
        IDs t4jIDs = null;
        long cursor = -1;
        int tries = 0;
        
        do {
            
            //repeat call until the response is not null: just in case the Twitter request limit is reached.
            do {
                try {
                    //todo que pasa si user no tiene followers?
                    t4jIDs = friendsFollowersResources.getFollowersIDs(new Long(pId), cursor);
                } catch (TwitterException e) {
                    TwitterManager.handleTwitterException(e);
                }
                tries++;
            } while (t4jIDs==null || tries<4);
            if (t4jIDs==null) break;
            long[] ids = t4jIDs.getIDs();
            for (long id : ids) {
                followers.add(Long.toString(id));
            }
            cursor = t4jIDs.getNextCursor();
        } while (t4jIDs.hasNext());
        return followers;
    }

    /**
     * Finds all user's friends (users who follows this user) id's. It will
     * @param pId String user id
     * @return List<String> containing the id's of the followers
     * @throws InterruptedException if the execution is interrupted.
     */
    public List<String> findUserFriends(String pId) throws InterruptedException {
        List<String> friends = new ArrayList<String>();
        IDs t4jIDs=null;
        long cursor = -1;
        int tries = 0;
        
        do {
            
            do {
                try {
                    t4jIDs = friendsFollowersResources.getFriendsIDs(new Long(pId), cursor);
                } catch (TwitterException e) {
                    TwitterManager.handleTwitterException(e);
                }

            }while (t4jIDs==null || tries<4);
            if (t4jIDs==null) break;
            long[] ids = t4jIDs.getIDs();
            for (long id : ids) {
                friends.add(Long.toString(id));
            }
            cursor = t4jIDs.getNextCursor();
        } while (t4jIDs.hasNext());
        return friends;
    }
}
