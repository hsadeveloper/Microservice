package shipmentservice.configuration;

import java.nio.charset.StandardCharsets;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import com.rabbitmq.client.Channel;
import shipmentservice.entity.Invoice;
import shipmentservice.entity.Order;
import shipmentservice.entity.RecievedMessage;
import org.springframework.amqp.support.AmqpHeaders;


@Component
public class MessageListener  {

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void listener(Invoice message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
       Invoice invoice = new Invoice();
  	 
	   invoice.setOrderName(message.getOrderName());
       invoice.setPrice(message.getPrice()); 
	 
       
      try {
 
          invoice.setOrderName(message.getOrderName());
          invoice.setPrice(message.getPrice());
          
          // Log the message if it contains the full address
          if (message.getFullAdrress() != null) {
              System.out.println("Shipment-service read the message --> " + invoice);
          } else {
              // Handle cases where the message is missing some data
              System.out.println("Message not received or missing some data");
          }
          
          // Acknowledge the message
          channel.basicAck(deliveryTag, false);
          
      } catch (Exception e) {
          // Handle exceptions and nack the message
          System.err.println("Failed to process message: " + e.getMessage());
          try {
              // Negative acknowledgment
              channel.basicNack(deliveryTag, false, true);
          } catch (Exception nackEx) {
              System.err.println("Failed to nack message: " + nackEx.getMessage());
          }
      }
    }
}


	


