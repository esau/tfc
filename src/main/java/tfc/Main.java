package tfc;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import tfc.consumer.handler.MessageHandler;
import tfc.producer.QueryHandler;

/**
 * TDF
 * User: Esaú González
 * Date: 3/05/14
 * Time: 16:31
 */
public class Main {
    
    public static void main (String[] args){
        if (args.length>0) {
            String query = args[0];
            int limit = -1;
            if (args.length>1)  limit = Integer.parseInt(args[1]);

            final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("Application-Spring-conf.xml");
            context.start();
            MessageHandler messageHandler = (MessageHandler) context.getBean("messageHandler");
            new Thread(messageHandler).start();
            QueryHandler queryHandler = (QueryHandler) context.getBean("queryHandler");
            queryHandler.handleQuery(query,limit);


        } else {
            System.out.println("ERROR: Twitter query required by param");
            System.exit(-1);
        }
    }
    
}
