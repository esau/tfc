package tfc.entities;

import java.util.Date;
import java.util.List;

/**
 * TDF
 * User: Esaú González
 * Date: 27/04/14
 * Time: 18:04
 * This class represents the entity User
 */
public class User {
    
    private String id;
    private Date created;
    private String description;
    private int favouritesCount;
    private int followersCount;
    private List<User> userFollowed;
    private int friendsCount;
    private Url url;
    private List<Tweet> tweetsAuthored;
    private List<User> followers;
    private boolean verified;
    private int listedCount;
    private String location;
    private List<Tweet> mentionedIn;
    private String name;
    private boolean protectedUser;
    private String screenName;
    private String timeZone;
    private int tweetCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFavouritesCount() {
        return favouritesCount;
    }

    public void setFavouritesCount(int favouritesCount) {
        this.favouritesCount = favouritesCount;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    public List<User> getUserFollowed() {
        return userFollowed;
    }

    public void setUserFollowed(List<User> userFollowed) {
        this.userFollowed = userFollowed;
    }

    public int getFriendsCount() {
        return friendsCount;
    }

    public void setFriendsCount(int friendsCount) {
        this.friendsCount = friendsCount;
    }

    public Url getUrl() {
        return url;
    }

    public void setUrl(Url url) {
        this.url = url;
    }

    public List<Tweet> getTweetsAuthored() {
        return tweetsAuthored;
    }

    public void setTweetsAuthored(List<Tweet> tweetsAuthored) {
        this.tweetsAuthored = tweetsAuthored;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public int getListedCount() {
        return listedCount;
    }

    public void setListedCount(int listedCount) {
        this.listedCount = listedCount;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Tweet> getMentionedIn() {
        return mentionedIn;
    }

    public void setMentionedIn(List<Tweet> mentionedIn) {
        this.mentionedIn = mentionedIn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isProtectedUser() {
        return protectedUser;
    }

    public void setProtectedUser(boolean protectedUser) {
        this.protectedUser = protectedUser;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public int getTweetCount() {
        return tweetCount;
    }

    public void setTweetCount(int tweetCount) {
        this.tweetCount = tweetCount;
    }
}
