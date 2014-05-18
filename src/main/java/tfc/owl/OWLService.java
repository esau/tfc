package tfc.owl;

import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import tfc.dto.TweetDTO;
import tfc.dto.UrlDTO;
import tfc.dto.UserDTO;
import tfc.owl.dao.OWLDAO;

import java.util.ArrayList;
import java.util.Date;

/**
 * TDF
 * User: Esaú González
 * Date: 27/04/14
 * Time: 17:53
 */
@Service
public class OWLService {
    @Autowired
    private OWLDAO OWLDAO;


    public void store(TweetDTO tweet) throws OWLOntologyStorageException {
        //process Tweet and store it and all it's inner classes into OWL:
        OWLIndividual owlIndividualTweet = OWLDAO.storeFullTweet(tweet);

        //store Tweet replied (with all its entities) and link with this tweet
        OWLIndividual owlIndividualTweetReplied = OWLDAO.storeFullTweet(tweet.getTweetReplied());
        OWLDAO.setObjectProperty("inReplyOfTweet", owlIndividualTweet, owlIndividualTweetReplied);

        //store Tweet retweeted (with all its entities) and link with this tweet
        OWLIndividual owlIndividualTweetRetweeted = OWLDAO.storeFullTweet(tweet.getRetweeted());
        OWLDAO.setObjectProperty("isRetweet", owlIndividualTweet, owlIndividualTweetRetweeted);

        System.out.println("Storing Tweet in OWL");
        OWLDAO.saveOntology();
    }


    public static void main(String[] args){
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("Application-Spring-conf.xml");
        context.start();
        
        TweetDTO tweetDTO = new TweetDTO();
        UserDTO userDTO = new UserDTO();
        userDTO.setCreated(new Date(System.currentTimeMillis()));
        userDTO.setDescription("Test user");
        userDTO.setFavouritesCount(3);
        userDTO.setFriendsCount(1);
        userDTO.setFollowersCount(1);
        userDTO.setId("1111111111");
        userDTO.setListedCount(0);
        userDTO.setLocation("Mi casa");
        userDTO.setName("test");
        userDTO.setProtectedUser(false);
        userDTO.setScreenName("test");
        String follower = "123456777";
        ArrayList<String> followersList = new ArrayList<String>();
        followersList.add(follower);
        userDTO.setFollowers(followersList);
        String friend = "123456789";
        ArrayList<String> friends = new ArrayList<String>();
        friends.add(friend);
        userDTO.setUserFollowed(friends);
        UrlDTO urlDTO = new UrlDTO();
        urlDTO.setDisplayUrl("http://www.url.extension");
        urlDTO.setMediaUrl("http://www.url.extension");
        urlDTO.setIndexA(0);
        urlDTO.setIndexB(24);
        urlDTO.setComesFromUserId(userDTO.getId());
        userDTO.setUrl(urlDTO);

        tweetDTO.setAuthor(userDTO);
        OWLService owlService = (OWLService) context.getBean("OWLService");
        try {
            owlService.store(tweetDTO);
        } catch (OWLOntologyStorageException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
