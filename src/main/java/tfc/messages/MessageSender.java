package tfc.messages;

import twitter4j.Status;

/**
 * TDF
 * User: Esaú González
 * Date: 27/04/14
 * Time: 17:40
 * Abstract message sender class. Extend it to send messages to the recipient you need.
 */

public interface MessageSender {
    
    public abstract void sendMessage(Status pMessage) throws InterruptedException;
}
