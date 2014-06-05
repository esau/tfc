package tfc.messages.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tfc.messages.MessageReader;
import tfc.queue.SingletonQueue;
import twitter4j.Status;

/**
 * TFC
 * User: Esaú González
 * Date: 3/05/14
 * Time: 18:05
 */
@Service
public class SingletonQueueMessageReader implements MessageReader{
    @Autowired
    private SingletonQueue queue;

    public SingletonQueueMessageReader() {
        queue = SingletonQueue.getInstance();
    }

    public Status readMessage() throws InterruptedException {
        return queue.read();
    }
}
