package tfc.owl;

import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tfc.dto.TweetDTO;
import tfc.owl.dao.OWLDAO;

/**
 * TFC
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
        if (tweet.getTweetReplied()!=null) {
            OWLIndividual owlIndividualTweetReplied = OWLDAO.storeFullTweet(tweet.getTweetReplied());
            OWLDAO.setObjectProperty("inReplyOfTweet", owlIndividualTweet, owlIndividualTweetReplied);
        }

        //store Tweet retweeted (with all its entities) and link with this tweet
        if (tweet.getRetweeted()!=null) {
            OWLIndividual owlIndividualTweetRetweeted = OWLDAO.storeFullTweet(tweet.getRetweeted());
            OWLDAO.setObjectProperty("isRetweet", owlIndividualTweet, owlIndividualTweetRetweeted);
        }

        OWLDAO.saveOntology();
    }


    public void saveAllChangesBecauseInterruption() throws OWLOntologyStorageException {
        OWLDAO.saveOntology();
    }
}
