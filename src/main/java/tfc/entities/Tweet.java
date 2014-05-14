package tfc.entities;

import java.util.Date;
import java.util.List;

/**
 * TDF
 * User: Esaú González
 * Date: 27/04/14
 * Time: 18:04
 * This class represents the entity Tweet
 */
public class Tweet {
    private User author;
    private Date created;
    private int favouritesCount;
    private Coordinates coordinates;
    private List<Media> medias;
    private List<Hashtag> hashtags;
    private List<Url> urls;
    private List<UserMention> userMentions;
    private String id;
    private Tweet tweetReplied;
    private Tweet retweeted;
    private String lang;
    private Place relatedPlace;
    private int retweetsCount;
    private String source;
    private String text;

    public boolean hasCoordinates(){
        return coordinates!=null;
    }

    public boolean hasEntities(){
        return (medias!=null&&medias.size()>0) ||
               (medias!=null&&medias.size()>0) ||
               (medias!=null&&medias.size()>0) ||
               (medias!=null&&medias.size()>0);
    }

    public boolean isReply(){
        return tweetReplied!=null;
    }

    public boolean isRetweet(){
        return retweeted!=null;
    }

    public boolean isRelatedToPlace(){
        return relatedPlace!=null;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public int getFavouritesCount() {
        return favouritesCount;
    }

    public void setFavouritesCount(int favouritesCount) {
        this.favouritesCount = favouritesCount;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public List<Media> getMedias() {
        return medias;
    }

    public void setMedias(List<Media> medias) {
        this.medias = medias;
    }

    public List<Hashtag> getHashtags() {
        return hashtags;
    }

    public void setHashtags(List<Hashtag> hashtags) {
        this.hashtags = hashtags;
    }

    public List<Url> getUrls() {
        return urls;
    }

    public void setUrls(List<Url> urls) {
        this.urls = urls;
    }

    public List<UserMention> getUserMentions() {
        return userMentions;
    }

    public void setUserMentions(List<UserMention> userMentions) {
        this.userMentions = userMentions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Tweet getTweetReplied() {
        return tweetReplied;
    }

    public void setTweetReplied(Tweet tweetReplied) {
        this.tweetReplied = tweetReplied;
    }

    public User getUserReplied() {
        return (tweetReplied!=null)?tweetReplied.getAuthor():null;
    }

    public Tweet getRetweeted() {
        return retweeted;
    }

    public void setRetweeted(Tweet retweeted) {
        this.retweeted = retweeted;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Place getRelatedPlace() {
        return relatedPlace;
    }

    public void setRelatedPlace(Place relatedPlace) {
        this.relatedPlace = relatedPlace;
    }

    public int getRetweetsCount() {
        return retweetsCount;
    }

    public void setRetweetsCount(int retweetsCount) {
        this.retweetsCount = retweetsCount;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
