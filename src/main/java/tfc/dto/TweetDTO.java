package tfc.dto;

import java.util.Date;
import java.util.List;

/**
 * TFC
 * User: Esaú González
 * Date: 27/04/14
 * Time: 18:04
 */
public class TweetDTO {

    private UserDTO author;
    private Date created;
    private int favouritesCount;
    private CoordinatesDTO coordinates;
    private List<MediaDTO> medias;
    private List<HashtagDTO> hashtags;
    private List<UrlDTO> urls;
    private List<UserMentionDTO> userMentions;
    private String id;
    //if reply, Status doesn't contains replied Status, only the id
    private TweetDTO tweetReplied;
    private TweetDTO retweeted;
    private String lang;
    private PlaceDTO relatedPlace;
    private int retweetsCount;
    private String source;
    private String text;

    public UserDTO getAuthor() {
        return author;
    }

    public void setAuthor(UserDTO author) {
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

    public CoordinatesDTO getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(CoordinatesDTO coordinates) {
        this.coordinates = coordinates;
    }

    public List<MediaDTO> getMedias() {
        return medias;
    }

    public void setMedias(List<MediaDTO> medias) {
        this.medias = medias;
    }

    public List<HashtagDTO> getHashtags() {
        return hashtags;
    }

    public void setHashtags(List<HashtagDTO> hashtags) {
        this.hashtags = hashtags;
    }

    public List<UrlDTO> getUrls() {
        return urls;
    }

    public void setUrls(List<UrlDTO> urls) {
        this.urls = urls;
    }

    public List<UserMentionDTO> getUserMentions() {
        return userMentions;
    }

    public void setUserMentions(List<UserMentionDTO> userMentions) {
        this.userMentions = userMentions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TweetDTO getTweetReplied() {
        return tweetReplied;
    }

    public void setTweetReplied(TweetDTO tweetReplied) {
        this.tweetReplied = tweetReplied;
    }

    public TweetDTO getRetweeted() {
        return retweeted;
    }

    public void setRetweeted(TweetDTO retweeted) {
        this.retweeted = retweeted;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public PlaceDTO getRelatedPlace() {
        return relatedPlace;
    }

    public void setRelatedPlace(PlaceDTO relatedPlace) {
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

    @Override
    public String toString() {
        return "TweetId: "+id;
    }
    
    public String getTweetExtract(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("User ").append(author.getScreenName());
        stringBuilder.append(" at ").append(created).append(" wrote: \"").append(text);
        return stringBuilder.toString();
    }
}
