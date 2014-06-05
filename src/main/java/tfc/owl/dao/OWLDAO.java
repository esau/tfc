package tfc.owl.dao;

import org.semanticweb.owlapi.model.*;
import org.springframework.stereotype.Repository;
import tfc.dto.*;
import uk.ac.manchester.cs.owl.owlapi.OWLDataPropertyImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * TFC
 * User: Esaú González
 * Date: 27/04/14
 * Time: 17:55
 */
@Repository()
public class OWLDAO extends BaseDAO{

    protected OWLDAO() throws OWLOntologyCreationException {
        super();
    }

    public void saveOntology() throws OWLOntologyStorageException {
        manager.saveOntology(twitterOWL, IRI.create(ontologyFile));
    }

    /**
     * Creates a instance of Tweet OWL class and fills its dataType properties. Creates User, Media list, Hashtag list,
     * Url list, UserMention list, Place and Coordinates OWL classes by the info contained in tweetDTO, links them to it
     * using its Object Properties and stores all in ontology.
     * @param tweet TweetDTO representing the Tweet to store
     * @return OWLIndividual representing the OWL class Tweet instance created and stored in ontology
     */
    public OWLIndividual storeFullTweet(TweetDTO tweet) {
        //create OLW Tweet Class instance, fill all its DataType properties and store it in ontology
        OWLIndividual owlIndividualTweet = storeTweet(tweet);
        //create OLW User Class instance, fill all its DataType properties and store it in ontology
        OWLIndividual owlIndividualUser = storeUser(tweet.getAuthor());
        //set Tweet.authoredBy Object property with the User instance
        setObjectProperty("authoredBy",owlIndividualTweet,owlIndividualUser);
        //set User.isAuthor Object property with the Tweet instance
        setObjectProperty("isAuthor",owlIndividualUser,owlIndividualTweet);
        //store all Medi entities extracted from Tweet and sets the Tweet.hasMedia and Media.comesFrom object properties
        if (tweet.getMedias()!=null) {
            List<OWLIndividual> owlIndividualMediaList = storeMedias(tweet.getMedias());
            for (OWLIndividual owlIndividualMedia : owlIndividualMediaList) {
                setObjectProperty("hasMedia",owlIndividualTweet,owlIndividualMedia);
                setObjectProperty("comesFrom",owlIndividualMedia,owlIndividualTweet);
            }
        }
        //store all Hashtag entities extracted from Tweet and sets the Tweet.hasHashtag and Hashtag.comesFrom object properties
        if (tweet.getHashtags()!=null) {
            List<OWLIndividual> owlIndividualHashtagList = storeHashtags(tweet.getHashtags());
            for (OWLIndividual owlIndividualHashtag : owlIndividualHashtagList) {
                setObjectProperty("hasHashtag", owlIndividualTweet,owlIndividualHashtag);
                setObjectProperty("comesFrom",owlIndividualHashtag,owlIndividualTweet);
            }
        }
        //store all Url entities extracted from Tweet and sets the Tweet.hasUrls and Url.comesFrom object properties
        if (tweet.getUrls()!=null) {
            List<OWLIndividual> owlIndividualUrlList = storeUrls(tweet.getUrls());
            for (OWLIndividual owlIndividualUrl : owlIndividualUrlList) {
                setObjectProperty("hasUrls",owlIndividualTweet,owlIndividualUrl);
                setObjectProperty("comesFrom",owlIndividualUrl,owlIndividualTweet);
            }
        }
        //store all UserMention entities extracted from Tweet and sets the Tweet.hasUserMention and UserMention.comesFrom object properties
        if (tweet.getUserMentions()!=null) {
            List<OWLIndividual> owlIndividualUserMentionList = storeUserMentions(tweet.getUserMentions());
            for (OWLIndividual owlIndividualUserMention : owlIndividualUserMentionList) {
                setObjectProperty("hasUserMention",owlIndividualTweet,owlIndividualUserMention);
                setObjectProperty("comesFrom",owlIndividualUserMention,owlIndividualTweet);
            }
        }

        //store Place and set the Tweet.relatedToPlace and Place.tweetRelated object properties
        if (tweet.getRelatedPlace()!=null) {
            OWLIndividual owlIndividualPlace = storePlace(tweet.getRelatedPlace());
            setObjectProperty("relatedToPlace",owlIndividualTweet,owlIndividualPlace);
            setObjectProperty("tweetRelated",owlIndividualPlace,owlIndividualTweet);
        }
        //store Coordinates and sets the Tweet.hasCoordinates and Coordinates.inTweet object properties
        if (tweet.getCoordinates()!=null) {
            OWLIndividual owlIndividualCoordinates = storeCoordinates(tweet.getCoordinates());
            setObjectProperty("hasCoordinates",owlIndividualTweet,owlIndividualCoordinates);
            setObjectProperty("inTweet",owlIndividualCoordinates,owlIndividualTweet);
        }

        return owlIndividualTweet;
    }

    /**
     * Creates a instance of Tweet OWL class, fills its DataType properties and stores it in the ontology
     * @param tweet TweetDTO representing the Tweet to store
     * @return OWLIndividual instance of Tweet OWL class stored in the ontology
     */
    private OWLIndividual storeTweet(TweetDTO tweet) {
        OWLIndividual owlIndividualTweet = createNewClassIndividual("Tweet", tweet.toString());

        //createdAt
        setDateTimeDataProperty("createdAt", tweet.getCreated().toString(), owlIndividualTweet);
        //favouritesCount
        setIntDataTypeProperty("favouritesCount", tweet.getFavouritesCount(), owlIndividualTweet);
        //private String id;
        setStringDataTypeProperty("id", tweet.getId(),owlIndividualTweet);
        //lang
        setStringDataTypeProperty("lang",tweet.getLang(),owlIndividualTweet);
        //retweetsCount;
        setIntDataTypeProperty("retweetsCount", tweet.getRetweetsCount(), owlIndividualTweet);
        //source;
        setStringDataTypeProperty("source", tweet.getSource(),owlIndividualTweet);
        //private String text;
        setStringDataTypeProperty("text",tweet.getText(),owlIndividualTweet);

        return owlIndividualTweet;
    }

    private OWLIndividual storeUser(UserDTO author) {
        //creates OWL instance of User
        OWLIndividual owlIndividualUser = createNewClassIndividual("User", author.getScreenName());
        //filling all properties:
        //id
        setStringDataTypeProperty("id", author.getId(),owlIndividualUser);
        //createdAt
        setDateTimeDataProperty("createdAt",author.getCreated().toString(),owlIndividualUser);
        //description
        setStringDataTypeProperty("description", author.getDescription(),owlIndividualUser);
        //favouritesCount
        setIntDataTypeProperty("favouritesCount", author.getFavouritesCount(), owlIndividualUser);
        //followersCount
        setIntDataTypeProperty("followersCount", author.getFollowersCount(), owlIndividualUser);
        //user id of users followed
        //follows: multiple User
        findAndSetUserFollowers(author, owlIndividualUser);
        //friendsCount
        setIntDataTypeProperty("friendsCount",author.getFriendsCount(),owlIndividualUser);
        //hasUrl: single Url
        setUrlObjectProperty(author.getUrl(),owlIndividualUser);
        //isFollowedBy: multiple User
        findAndSetUserFriends(author, owlIndividualUser);
        //isVerified
        setBooleanDataTypeProperty("isVerified", author.isVerified(),owlIndividualUser);
        //listedCount
        setIntDataTypeProperty("listedCount", author.getListedCount(), owlIndividualUser);
        //location
        setStringDataTypeProperty("location", author.getLocation(),owlIndividualUser);
        //find all UserMention where this user is mentioned
        findAndSetUserMentions(author, owlIndividualUser);
        //name
        setStringDataTypeProperty("name", author.getName(),owlIndividualUser);
        //protected
        setBooleanDataTypeProperty("protected",author.isProtectedUser(),owlIndividualUser);
        //screenName
        setStringDataTypeProperty("screenName", author.getScreenName(), owlIndividualUser);
        //timeZone
        setStringDataTypeProperty("timeZone", author.getTimeZone(), owlIndividualUser);
        //tweetCount
        setIntDataTypeProperty("tweetCount",author.getTweetCount(),owlIndividualUser);

        return owlIndividualUser;
    }

    private List<OWLIndividual> storeMedias(List<MediaDTO> medias) {
        List<OWLIndividual> owlIndividualMediaList = new ArrayList<OWLIndividual>();
        for (MediaDTO media : medias) {
            OWLIndividual owlIndividualMedia = storeMedia(media);
            owlIndividualMediaList.add(owlIndividualMedia);
        }
        return owlIndividualMediaList;
    }

    private OWLIndividual storeMedia(MediaDTO mediaDTO) {
        OWLIndividual owlIndividualMedia = createNewClassIndividual("Media", mediaDTO.toString());

        setIntDataTypeProperty("indexA", mediaDTO.getIndexA(),owlIndividualMedia);
        setIntDataTypeProperty("indexB", mediaDTO.getIndexB(),owlIndividualMedia);
        setStringDataTypeProperty("displayUrl", mediaDTO.getDisplayUrl(), owlIndividualMedia);
        setStringDataTypeProperty("mediaUrl", mediaDTO.getMediaUrl(),owlIndividualMedia);
        setStringDataTypeProperty("id",mediaDTO.getId(),owlIndividualMedia);
        setStringDataTypeProperty("type",mediaDTO.getMediaType(),owlIndividualMedia);

        return owlIndividualMedia;
    }

    private List<OWLIndividual> storeUserMentions(List<UserMentionDTO> userMentions) {
        List<OWLIndividual> owlIndividualUserMentionList = new ArrayList<OWLIndividual>();
        for (UserMentionDTO userMention : userMentions) {
            OWLIndividual owlIndividualHashtag = storeUserMention(userMention);
            owlIndividualUserMentionList.add(owlIndividualHashtag);
        }
        return owlIndividualUserMentionList;
    }

    private OWLIndividual storeUserMention(UserMentionDTO userMentionDTO) {
        OWLIndividual owlIndividualUserMention = createNewClassIndividual("UserMention", userMentionDTO.toString());

        setIntDataTypeProperty("indexA", userMentionDTO.getIndexA(), owlIndividualUserMention);
        setIntDataTypeProperty("indexB", userMentionDTO.getIndexB(), owlIndividualUserMention);
        setStringDataTypeProperty("screenName",userMentionDTO.getMentionsScreenName(),owlIndividualUserMention);
        //todo set ObjectProperty mentionsUser

        return owlIndividualUserMention;
    }

    private List<OWLIndividual> storeHashtags(List<HashtagDTO> hashtags) {
        List<OWLIndividual> owlIndividualHashtagList = new ArrayList<OWLIndividual>();
        for (HashtagDTO hashtag : hashtags) {
            OWLIndividual owlIndividualHashtag = storeHashtag(hashtag);
            owlIndividualHashtagList.add(owlIndividualHashtag);
        }
        return owlIndividualHashtagList;
    }

    private OWLIndividual storeHashtag(HashtagDTO hashtagDTO) {
        OWLIndividual owlIndividualMedia = createNewClassIndividual("Hashtag", hashtagDTO.toString());

        setIntDataTypeProperty("indexA", hashtagDTO.getIndexA(), owlIndividualMedia);
        setIntDataTypeProperty("indexB", hashtagDTO.getIndexB(), owlIndividualMedia);
        setStringDataTypeProperty("text", hashtagDTO.getText(), owlIndividualMedia);

        return owlIndividualMedia;
    }


    private List<OWLIndividual> storeUrls(List<UrlDTO> urls) {
        List<OWLIndividual> owlIndividualUrlList = new ArrayList<OWLIndividual>();
        for (UrlDTO url : urls) {
            OWLIndividual owlIndividualUrl = storeUrl(url);
            owlIndividualUrlList.add(owlIndividualUrl);
        }
        return owlIndividualUrlList;
    }

    private OWLIndividual storeUrl(UrlDTO urlDTO) {
        OWLIndividual owlIndividualUrl = createNewClassIndividual("Url",urlDTO.toString() );

        setIntDataTypeProperty("indexA", urlDTO.getIndexA(),owlIndividualUrl);
        setIntDataTypeProperty("indexB", urlDTO.getIndexB(),owlIndividualUrl);
        setStringDataTypeProperty("displayUrl", urlDTO.getDisplayUrl(), owlIndividualUrl);
        setStringDataTypeProperty("mediaUrl", urlDTO.getMediaUrl(),owlIndividualUrl);

        return owlIndividualUrl;

    }

    private OWLIndividual storePlace(PlaceDTO relatedPlace) {
        OWLIndividual owlIndividualPlace = createNewClassIndividual("Place",relatedPlace.getFullName());

        //set Place DataType properties
        setStringDataTypeProperty("attributes", relatedPlace.getAttributes(),owlIndividualPlace);
        setStringDataTypeProperty("countryCode", relatedPlace.getCountryCode(),owlIndividualPlace);
        setStringDataTypeProperty("countryName", relatedPlace.getCountryName(),owlIndividualPlace);
        setStringDataTypeProperty("fullName", relatedPlace.getFullName(),owlIndividualPlace);
        setStringDataTypeProperty("id",relatedPlace.getId(),owlIndividualPlace);
        setStringDataTypeProperty("type",relatedPlace.getType(),owlIndividualPlace);
        setStringDataTypeProperty("url",relatedPlace.getUrl(),owlIndividualPlace);

        //store Coordinates from the boundingBox and set Place.boundingBox and Coordinates.inPlace object properties
        List<CoordinatesDTO> coordinatesDTOList = relatedPlace.getBoundingBox();
        for (CoordinatesDTO coordinatesDTO : coordinatesDTOList) {
            OWLIndividual owlIndividualCoordinates = storeCoordinates(coordinatesDTO);
            setObjectProperty("boundingBox",owlIndividualPlace,owlIndividualCoordinates);
            setObjectProperty("inPlace",owlIndividualCoordinates,owlIndividualPlace);
        }

        return owlIndividualPlace;
    }

    private OWLIndividual storeCoordinates(CoordinatesDTO coordinatesDTO) {
        OWLIndividual owlIndividualCoordinates = createNewClassIndividual("Coordinates", coordinatesDTO.toString());

        setFloatDataTypeProperty("latitude", new Float(coordinatesDTO.getLatitude()), owlIndividualCoordinates);
        setFloatDataTypeProperty("longitude", new Float(coordinatesDTO.getLongitude()), owlIndividualCoordinates);

        return owlIndividualCoordinates;
    }

    private void findAndSetUserFollowers(UserDTO author, OWLIndividual owlIndividualUser) {
        if (author.getFollowers()!=null) {
            List<String> followers = author.getFollowers();
            Set<OWLIndividual> individuals = findAllClassIndividuals("User");
            OWLDataPropertyImpl userIdProperty = findDataProperty("id");
            for (OWLIndividual individual : individuals) {
                Set<OWLLiteral> literals = findAllDataPropertyLiterals(individual,userIdProperty);
                for (OWLLiteral literal : literals) {
                    String id = literal.getLiteral();
                    if(followers.contains(id)){
                        //setting the "isFollowedBy" Object property
                        setObjectProperty("isFollowedBy",owlIndividualUser,individual);
                        //setting the inverse Object property "follows"
                        setObjectProperty("follows",individual,owlIndividualUser);
                        break;
                    }
                }
            }
        }

    }

    private void setUrlObjectProperty(UrlDTO urlDTO, OWLIndividual owlIndividualUser) {
        if(urlDTO!=null){
            OWLIndividual owlIndividualUrl = storeUrl(urlDTO);
            setObjectProperty("comesFromUser", owlIndividualUrl,owlIndividualUser);
            setObjectProperty("hasURl",owlIndividualUser,owlIndividualUrl);
        }
    }


    private void findAndSetUserFriends(UserDTO author, OWLIndividual owlIndividualUser) {
        if(author.getUserFollowed()!=null){
            List<String> friends = author.getUserFollowed();
            Set<OWLIndividual> individuals = findAllClassIndividuals("User");
            OWLDataPropertyImpl userIdProperty = findDataProperty("id");
            for (OWLIndividual individual : individuals) {
                Set<OWLLiteral> literals = findAllDataPropertyLiterals(individual,userIdProperty);
                for (OWLLiteral literal : literals) {
                    String id = literal.getLiteral();
                    if(friends.contains(id)){
                        //setting the inverse Object property "follows"
                        setObjectProperty("follows",owlIndividualUser,individual);
                        //setting the "isFollowedBy" Object property
                        setObjectProperty("isFollowedBy",individual,owlIndividualUser);
                        break;
                    }
                }
            }
        }
    }

    /**
     * Finds all UserMention in ontology mentioning this User and sets the User.mentionedIn and UserMention.mentionsUser properties
     * @param author UserDTO we're processing
     * @param owlIndividualUser OWLNamedIndividual we're creating and storing in the ontology
     */
    private void findAndSetUserMentions(UserDTO author, OWLIndividual owlIndividualUser) {
        Set<OWLIndividual> individuals = findAllClassIndividuals("UserMention");
        OWLDataPropertyImpl screenNameProperty = findDataProperty("screenName");
        for (OWLIndividual individual : individuals) {
            Set<OWLLiteral> literals = findAllDataPropertyLiterals(individual,screenNameProperty);
            for (OWLLiteral literal : literals) {
                String screenName = literal.getLiteral();
                if (screenName.equals(author.getScreenName())){
                    setMentionedInObjectProperty(owlIndividualUser,individual);
                    setMentionsUserObjectProperty(individual,owlIndividualUser);
                }
            }
        }
    }

    private void setMentionsUserObjectProperty(OWLIndividual owlIndividualUserMention, OWLIndividual owlIndividualUser) {
        setObjectProperty("mentionsUser", owlIndividualUserMention,owlIndividualUser);
    }

    private void setMentionedInObjectProperty(OWLIndividual owlIndividualUser, OWLIndividual owlIndividualUserMention) {
        setObjectProperty("mentionedIn", owlIndividualUser, owlIndividualUserMention);
    }

}
