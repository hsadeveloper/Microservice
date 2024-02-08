package shipmentservice.configuration;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import shipmentservice.entity.Order;

@Component
public class MessageListener {

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void listener(Order message) {
    	System.out.println("consumer -->"+message);
        System.out.println(message);
    }

}
