package orderservice.controller;



import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import orderservice.common.TransactionRequest;
import orderservice.common.TransactionResponse;
import orderservice.configuration.RabbitMQConfig;
import orderservice.entity.Product;
import orderservice.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {
	
  @Autowired
  private OrderService service;

  @Autowired
  private RabbitTemplate template;

  // SEARCH BY KEYWORD
  
  
  @GetMapping("/")
  public List<Product> getProduct() {
    return service.getProducts();
  }

  @GetMapping("/test")
  public  String testingMessage() {
    return "Testing endpoint";
  }

  @PostMapping("/")
  public TransactionResponse bookOrder(@RequestBody TransactionRequest request) {
	  System.out.println("hhhhhhhhere inside bookorde");
    return service.saveOrder(request);
  }
  
  @PostMapping("/publish")
  public String publishMessage() {

    template.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY, "hello from Hasanain Alsabonchi");

    return "Message Published";
  }
  
  
  // ADD TO CART  SAVE - TABLE EACH ROW IS A USER CART
  
  // REMOVE FRON CART 
  
  // LIST OF ITEM  IN THE CART


}
