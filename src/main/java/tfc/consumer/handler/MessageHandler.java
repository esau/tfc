package tfc.consumer.handler;

import org.apache.log4j.Logger;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tfc.dto.TweetDTO;
import tfc.messages.MessageReader;
import tfc.owl.OWLService;
import tfc.twitter.TwitterManager;
import twitter4j.RateLimitStatus;
import twitter4j.Status;
import twitter4j.TwitterException;

/**
 * TDF
 * User: Esaú González
 * Date: 27/04/14
 * Time: 17:42
 */
@Component
public class MessageHandler implements Runnable{
    private static Logger log = Logger.getLogger(MessageHandler.class);

    @Autowired
    private MessageReader messageReader;
    @Autowired
    private OWLService owlService;
    @Autowired
    private TwitterManager twitterManager;

    public MessageHandler() {
        log.debug("MessageHandler created");
    }

    public void run() {
        while (true){
            try {
                log.debug("Empiezo a leer");
                Status message = messageReader.readMessage();
                log.debug("Message read: " + message);
                processMessage(message);
                Thread.sleep(10000l);
            } catch (InterruptedException e) {
                log.error("Interrupted!", e);
                Thread.interrupted();
            } catch (OWLOntologyStorageException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }

    private void processMessage(Status message) throws InterruptedException, OWLOntologyStorageException {
        try {
            TweetDTO tweet = twitterManager.process(message);
            //todo: if replied ask twitterManager for the Tweet
            owlService.store(tweet);
        } catch (TwitterException e) {
            log.error("Exception handling with Twitter API: ", e);
            RateLimitStatus rateLimitStatus = e.getRateLimitStatus();
            long timeToRetry = rateLimitStatus.getSecondsUntilReset() * 1000l;
            Thread.sleep(timeToRetry);
        }
        log.debug("Processing message: " + message);
    }
}
