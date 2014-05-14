package tfc.twitter.dao;

import org.springframework.stereotype.Repository;
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
    
    public User findUserById(String pId) throws TwitterException {

            return usersResources.showUser(new Long(pId));

    }
    
    public List<String> findUserFollowers(String pId) throws TwitterException {
        List<String> followers = new ArrayList<String>();
        IDs t4jIDs;
        long cursor = -1;

        do {
            t4jIDs = friendsFollowersResources.getFollowersIDs(new Long(pId), cursor);
            long[] ids = t4jIDs.getIDs();
            for (long id : ids) {
                followers.add(Long.toString(id));
            }
            cursor = t4jIDs.getNextCursor();
        } while (t4jIDs.hasNext());
        return followers;
    }
    
    public List<String> findUserFriends(String pId) throws TwitterException {
        List<String> friends = new ArrayList<String>();
        IDs t4jIDs;
        long cursor = -1;

        do {
            t4jIDs = friendsFollowersResources.getFriendsIDs(new Long(pId), cursor);
            long[] ids = t4jIDs.getIDs();
            for (long id : ids) {
                friends.add(Long.toString(id));
            }
            cursor = t4jIDs.getNextCursor();
        } while (t4jIDs.hasNext());
        return friends;
    }
}
