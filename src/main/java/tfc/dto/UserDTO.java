package tfc.dto;


import java.util.Date;
import java.util.List;

/**
 * TDF
 * User: Esaú González
 * Date: 27/04/14
 * Time: 18:04
 */
public class UserDTO {

    private String id;
    private Date created;
    private String description;
    private int favouritesCount;
    private int followersCount;
    //user id of users followed
    private List<String> userFollowed;
    private int friendsCount;
    private UrlDTO url;
    //user id of followers
    private List<String> followers;
    private boolean verified;
    private int listedCount;
    private String location;
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

    public List<String> getUserFollowed() {
        return userFollowed;
    }

    public void setUserFollowed(List<String> userFollowed) {
        this.userFollowed = userFollowed;
    }

    public int getFriendsCount() {
        return friendsCount;
    }

    public void setFriendsCount(int friendsCount) {
        this.friendsCount = friendsCount;
    }

    public UrlDTO getUrl() {
        return url;
    }

    public void setUrl(UrlDTO url) {
        this.url = url;
    }

    public List<String> getFollowers() {
        return followers;
    }

    public void setFollowers(List<String> followers) {
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
