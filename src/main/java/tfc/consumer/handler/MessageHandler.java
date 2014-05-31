package tfc.consumer.handler;

import org.apache.log4j.Logger;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tfc.dto.TweetDTO;
import tfc.messages.MessageReader;
import tfc.owl.OWLService;
import tfc.twitter.TwitterManager;
import twitter4j.Status;

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
        boolean readingMessages = true;
        while (readingMessages){
            try {
                log.debug("Starting to read messages");
                Status message = messageReader.readMessage();
                log.debug("Message read: " + message);
                if (message.getUser()!=null) {
                    StringBuilder logLine = new StringBuilder();
                    logLine.append("Author: ").append(message.getUser().getScreenName()).append(" Text: ");

                    if (message.getText().length()>30){
                        logLine.append(message.getText().substring(0,30)).append("...");
                    } else {
                        logLine.append(message.getText());
                    }
                    log.debug(logLine.toString());
                    processMessage(message);
                    Thread.sleep(1000l);
                } else {
                    log.info("No more results to process");
                    readingMessages=false;
                }
            } catch (InterruptedException e) {
                log.error("Interrupted!", e);
                try {
                    owlService.saveAllChangesBecauseInterruption();
                } catch (OWLOntologyStorageException e1) {
                    log.error("Can't save ontology after interruption.");
                }
                Thread.interrupted();
            } catch (OWLOntologyStorageException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }

    private void processMessage(Status message) throws InterruptedException, OWLOntologyStorageException {
            TweetDTO tweet = twitterManager.process(message);
            log.info("Processed tweet: "+tweet.getTweetExtract());
            owlService.store(tweet);
            log.info("Tweet stored.");
        log.debug("Processing message: " + message);
    }
}
