package tfc.messages.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tfc.messages.MessageSender;
import tfc.queue.SingletonQueue;
import twitter4j.Status;

/**
 * TFC
 * User: Esaú González
 * Date: 3/05/14
 * Time: 17:57
 * MessageSender implementation to use with a queue system implemented by a Singleton with BloquingQueue
 */
@Service
public class SingletonQueueMessageSender implements MessageSender{
    private static Logger log = Logger.getLogger(SingletonQueueMessageSender.class);
    @Autowired
    private SingletonQueue queue;

    public SingletonQueueMessageSender() {
        queue = SingletonQueue.getInstance();
    }

    public void sendMessage(Status pMessage) throws InterruptedException {
        log.debug("Message sent: " +pMessage);
        queue.put(pMessage);
    }
    
}
