package tfc.messages;


import twitter4j.Status;

/**
 * TFC
 * User: Esaú González
 * Date: 3/05/14
 * Time: 18:03
 * Abstract message reader class. Extend it to read messages from the recipient you need.
 */

public interface MessageReader {

    public abstract Status readMessage() throws InterruptedException;
}
