package tfc.queue;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import twitter4j.Status;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * TDF
 * User: Esaú González
 * Date: 3/05/14
 * Time: 17:59
 * Singleton class acting like a message queue system, you can send and read messages
 */
@Component
public class SingletonQueue {
    private static Logger log = Logger.getLogger(SingletonQueue.class);
    private static SingletonQueue ourInstance = new SingletonQueue();
    
    private ArrayBlockingQueue<Status> messageQueue;

    public static SingletonQueue getInstance() {
        return ourInstance;
    }

    private SingletonQueue() {
       messageQueue = new ArrayBlockingQueue<Status>(1024);
    }
    
    public void put(Status pMessage) throws InterruptedException {
        messageQueue.put(pMessage);
        log.debug("message in q");
        log.debug(messageQueue.toString());
    }

    /**
     * Read a message from queue
     * @return String message read from the queue
     * @throws InterruptedException
     */
    public Status read() throws InterruptedException {
        log.debug("Reading message from q");
        return messageQueue.take();
    }
}
