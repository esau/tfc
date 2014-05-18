package tfc.twitter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tfc.dto.*;
import tfc.twitter.dao.*;
import twitter4j.*;

import java.util.ArrayList;
import java.util.List;

/**
 * TDF
 * User: Esaú González
 * Date: 27/04/14
 * Time: 17:56
 */
@Service
public class TwitterManager {
    private static Logger log = Logger.getLogger(TwitterManager.class);
    @Autowired
    private CoordinatesDAO coordinatesDAO;
    @Autowired
    private HashtagDAO hashtagDAO;
    @Autowired
    private MediaDAO mediaDAO;
    @Autowired
    private PlaceDAO placeDAO;
    @Autowired
    private TweetDAO tweetDAO;
    @Autowired
    private UrlDAO urlDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private UserMentionDAO userMentionDAO;

    public TweetDTO process(Status message) throws TwitterException {
        return parseStatusToTweetDTO(message, false);
    }

    /**
     * Parse Status to TweetDTO. If pIsRetweetOrReply is true don't go forward in recursivity to find the original Status or user
     * @param pOriginalStatus Status to parse to TweetDTO
     * @param pIsRetweetOrReply boolean tells if it's the second level of recursivity and it doesn't go further.
     * @return TweetDTO parsed from data within pOriginalStatus
     * @throws TwitterException if there're problems with the Twitter API responses.
     */
    private TweetDTO parseStatusToTweetDTO(Status pOriginalStatus, boolean pIsRetweetOrReply) throws TwitterException {

        TweetDTO tweet = new TweetDTO();
        tweet.setAuthor(parseAuthor(pOriginalStatus.getUser()));
        log.debug("User: " +pOriginalStatus.getUser());

        tweet.setCreated(pOriginalStatus.getCreatedAt());
        log.debug("Created: " +pOriginalStatus.getCreatedAt());

        tweet.setFavouritesCount(pOriginalStatus.getFavoriteCount());
        log.debug("Favs: " + pOriginalStatus.getFavoriteCount());

        tweet.setCoordinates(parseCoordinates(pOriginalStatus));
        log.debug("Coordinates: " + pOriginalStatus.getGeoLocation());

        log.debug("Handling Entities");
        tweet.setMedias(fetchMediaEntities(pOriginalStatus));
        tweet.setHashtags(fetchHashtagEntities(pOriginalStatus));
        tweet.setUrls(fetchUrlEntities(pOriginalStatus));
        tweet.setUserMentions(fetchUserMentionEntities(pOriginalStatus));

        tweet.setId("" + pOriginalStatus.getId());
        log.debug("id: " + pOriginalStatus.getId());


        if (!pIsRetweetOrReply) tweet.setTweetReplied(parseReplyStatus(pOriginalStatus));

        if (!pIsRetweetOrReply) tweet.setRetweeted(parseRetweetedStatus(pOriginalStatus));

        tweet.setLang(pOriginalStatus.getLang());
        log.debug("Lang: " + pOriginalStatus.getLang());

        tweet.setRelatedPlace(parsePlace(pOriginalStatus));

        tweet.setRetweetsCount(pOriginalStatus.getRetweetCount());
        log.debug("Retweets count: " + pOriginalStatus.getRetweetCount());

        tweet.setSource(pOriginalStatus.getSource());
        log.debug("Source: " + pOriginalStatus.getSource());

        tweet.setText(pOriginalStatus.getText());
        log.debug("Text: " + pOriginalStatus.getText());

        return tweet;
    }

    private TweetDTO parseReplyStatus(Status pStatus) throws TwitterException {
        TweetDTO toReturn=null;
        long repliedStatusId= pStatus.getInReplyToStatusId();
        log.debug("Replied: "+pStatus.getInReplyToStatusId());
        if(repliedStatusId!=-1){
            //retweet
            Status repliedStatus = findStatusById(repliedStatusId);
            toReturn = parseStatusToTweetDTO(repliedStatus, true);
            log.debug("Replied Status: "+ repliedStatus);
        }
        return toReturn;
    }

    private Status findStatusById(long repliedStatusId) throws TwitterException {
        return tweetDAO.findStatusById(repliedStatusId);
    }

    private PlaceDTO parsePlace(Status pStatus) throws TwitterException {
        PlaceDTO toReturn=null;
        Place place = pStatus.getPlace();
        if (place!=null) {
            Place fullPlace = findPlaceById(place.getId());
            toReturn = new PlaceDTO();
            toReturn.setAttributes(fullPlace.getStreetAddress());
            toReturn.setCountryCode(fullPlace.getCountryCode());
            toReturn.setCountryName(fullPlace.getCountry());
            toReturn.setFullName(fullPlace.getFullName());
            toReturn.setId(fullPlace.getId());
            toReturn.setType(fullPlace.getPlaceType());
            toReturn.setUrl(fullPlace.getURL());
            GeoLocation[][] boundingBox =place.getBoundingBoxCoordinates();
            if (boundingBox!=null) {
                for (GeoLocation[] geoLocations : boundingBox) {
                    for (GeoLocation geoLocation : geoLocations) {
                        CoordinatesDTO coordinatesDTO = new CoordinatesDTO();
                        coordinatesDTO.setLongitude(geoLocation.getLongitude());
                        coordinatesDTO.setLatitude(geoLocation.getLatitude());
                        coordinatesDTO.setPlaceId(fullPlace.getId());
                        toReturn.addCoordinatesDTO(coordinatesDTO);

                    }
                }
            }
            GeoLocation[][] geometryCoordinates = place.getGeometryCoordinates();
            if (geometryCoordinates!=null) {
                for (GeoLocation[] geometryCoordinate : geometryCoordinates) {
                    for (GeoLocation geoLocation : geometryCoordinate) {
                        CoordinatesDTO coordinatesDTO = new CoordinatesDTO();
                        coordinatesDTO.setLongitude(geoLocation.getLongitude());
                        coordinatesDTO.setLatitude(geoLocation.getLatitude());
                        coordinatesDTO.setPlaceId(fullPlace.getId());
                        toReturn.addCoordinatesDTO(coordinatesDTO);
                    }
                }
            }
        }

        log.debug("Place: "+place);
        return toReturn;
    }
    
    public Place findPlaceById(String pPlaceId) throws TwitterException {
        return placeDAO.findPlaceById(pPlaceId);
    }

    private TweetDTO parseRetweetedStatus(Status pStatus) throws TwitterException {
        TweetDTO toReturn=null;
        log.debug("Retweeted: "+pStatus.isRetweet());
        if(pStatus.isRetweet() || pStatus.getRetweetedStatus()!=null){
            //retweet
            Status retweetedStatus = pStatus.getRetweetedStatus();
            toReturn = parseStatusToTweetDTO(pStatus, true);
            log.debug("Retweeted Status: "+ retweetedStatus);
        }
        return toReturn;
    }

    private List<UserMentionDTO> fetchUserMentionEntities(Status pStatus) {
        UserMentionEntity[] userMentionEntities = pStatus.getUserMentionEntities();
        List<UserMentionDTO> userMentionDTOs = null;
        if(userMentionEntities.length>0) {
            String tweetId = ""+pStatus.getId();
            userMentionDTOs = new ArrayList<UserMentionDTO>(userMentionEntities.length);
            for (UserMentionEntity o :userMentionEntities) {
                UserMentionDTO userMentionDTO = new UserMentionDTO();
                userMentionDTO.setComesFromTweetId(tweetId);
                userMentionDTO.setMentionsUserId("" + o.getId()); //todo go find User in Twitter
                userMentionDTO.setMentionsScreenName(o.getScreenName());
                userMentionDTO.setIndexA(o.getStart());
                userMentionDTO.setIndexB(o.getEnd());
                userMentionDTOs.add(userMentionDTO);
                log.debug("UserMention: " + o);
            }
        }
        return userMentionDTOs;
    }

    private List<UrlDTO> fetchUrlEntities(Status pStatus) {
        URLEntity[] urlEntities = pStatus.getURLEntities();
        List<UrlDTO> urlDTOs = null;
        if(urlEntities.length>0) {
            String tweetId = ""+pStatus.getId();
            urlDTOs = new ArrayList<UrlDTO>(urlEntities.length);
            for (URLEntity o :urlEntities) {
                UrlDTO urlDTO = new UrlDTO();
                urlDTO.setComesFromUserId(tweetId);
                urlDTO.setDisplayUrl(o.getDisplayURL());
                urlDTO.setMediaUrl(o.getURL());
                urlDTO.setIndexA(o.getStart());
                urlDTO.setIndexB(o.getEnd());
                urlDTOs.add(urlDTO);
                log.debug("Url: " + o);
            }
        }
        return urlDTOs;
    }

    private List<HashtagDTO> fetchHashtagEntities(Status pStatus) {
        HashtagEntity[] hashtagEntities = pStatus.getHashtagEntities();
        List<HashtagDTO> hashtagDTOs = null;
        if(hashtagEntities.length>0){
            String tweetId = ""+pStatus.getId();
            hashtagDTOs = new ArrayList<HashtagDTO>(hashtagEntities.length);
            for (HashtagEntity o :hashtagEntities) {
                HashtagDTO hashtagDTO = new HashtagDTO();
                hashtagDTO.setComesFromTweetId(tweetId);
                hashtagDTO.setText(o.getText());
                hashtagDTO.setIndexA(o.getStart());
                hashtagDTO.setIndexB(o.getEnd());
                hashtagDTOs.add(hashtagDTO);
                log.debug("Hashtag: " + o);
            }
        }

        return hashtagDTOs;
    }

    private List<MediaDTO> fetchMediaEntities(Status pStatus) {
        MediaEntity[] mediaEntities = pStatus.getMediaEntities();
        List<MediaDTO> mediaDTOs = null;
        if(mediaEntities.length>0){
            String tweetId = ""+pStatus.getId();
            mediaDTOs = new ArrayList<MediaDTO>(mediaEntities.length);
            for (MediaEntity o : mediaEntities) {
                MediaDTO mediaDTO = new MediaDTO();
                mediaDTO.setComesFromTweetId(tweetId);
                mediaDTO.setDisplayUrl(o.getDisplayURL());
                mediaDTO.setId("" + o.getId());
                mediaDTO.setMediaType(o.getType());
                mediaDTO.setMediaUrl(o.getMediaURL());
                mediaDTO.setIndexA(o.getStart());
                mediaDTO.setIndexB(o.getEnd());
                log.debug("Media: " + o);
            }
        }

        return mediaDTOs;
    }


    /**
     * If Status has geolocation it returns CoordinatesDTO with geolocation values, null elsewhere
     * @param pStatus Status with Tweet content
     * @return CoordinatesDTO if Status has Geolocation, null elsewhere
     */
    private CoordinatesDTO parseCoordinates(Status pStatus) {
        CoordinatesDTO coordinatesDTO = null;
        if(pStatus.getGeoLocation()!=null){
            coordinatesDTO = new CoordinatesDTO();
            coordinatesDTO.setTweetId(""+pStatus.getId());
            coordinatesDTO.setLatitude(pStatus.getGeoLocation().getLatitude());
            coordinatesDTO.setLongitude(pStatus.getGeoLocation().getLongitude());
        }

        return coordinatesDTO;
    }

    private UserDTO parseAuthor(twitter4j.User user) throws TwitterException {
        //todo pasar Status como parametro en lugar de User??
        UserDTO toReturn = new UserDTO();
        toReturn.setId(""+user.getId());
        toReturn.setName(user.getName());
        toReturn.setScreenName(user.getScreenName());
        toReturn.setLocation(user.getLocation());
        toReturn.setDescription(user.getDescription());
        setUrlEntities(user, toReturn);
        toReturn.setProtectedUser(user.isProtected());
        toReturn.setFollowersCount(user.getFollowersCount());
        toReturn.setFriendsCount(user.getFriendsCount());
        toReturn.setCreated(user.getCreatedAt());
        toReturn.setFavouritesCount(user.getFavouritesCount());
        toReturn.setTimeZone(user.getTimeZone());
        toReturn.setTweetCount(user.getStatusesCount());
        toReturn.setVerified(user.isVerified());
        toReturn.setListedCount(user.getListedCount());
        toReturn.setUserFollowed(userDAO.findUserFriends(""+user.getId()));
        toReturn.setFollowers(userDAO.findUserFollowers(""+ user.getId()));
        return null;
    }

    private void setUrlEntities(User user, UserDTO toReturn) throws TwitterException {
        User fetchedUser = userDAO.findUserById(""+user.getId());
        URLEntity[] urlEntities = fetchedUser.getDescriptionURLEntities();
        for (URLEntity urlEntity : urlEntities) {
            if(urlEntity.getURL().equals(user.getURL())) {
                toReturn.setUrl(buildUrlDTO(urlEntity, toReturn));
                break;
            }
        }
    }

    private UrlDTO buildUrlDTO(URLEntity url, UserDTO userDTO) {
        UrlDTO toReturn = new UrlDTO();
        toReturn.setComesFromUserId(userDTO.getId());
        toReturn.setDisplayUrl(url.getDisplayURL());
        toReturn.setMediaUrl(url.getURL());
        toReturn.setIndexA(url.getStart());
        toReturn.setIndexB(url.getEnd());
        return toReturn;
    }
}
