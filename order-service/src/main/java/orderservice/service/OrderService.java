package orderservice.service;





import java.util.Arrays;
import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import orderservice.common.Payment;
import orderservice.common.TransactionRequest;
import orderservice.common.TransactionResponse;
import orderservice.configuration.RabbitMQConfig;
import orderservice.entity.Order;
import orderservice.entity.Product;
import orderservice.repository.OrderRepository;

@Service
public class OrderService {

 private OrderRepository repository;
 
 
 @Autowired
 private RabbitTemplate rbmqTemplate;
   
   
public OrderService(OrderRepository repository) {
	super();
	this.repository = repository;
}


public List<Product> getProducts() {

    // Create a RestTemplate instance
    RestTemplate restTemplate = new RestTemplate();
    // Define the URL you want to send the GET request to
    String url = "http://localhost:8087/product/all";
    // Send the GET request and receive the response
    ResponseEntity<Product[]> products = restTemplate.getForEntity(url, Product[].class);
    Product[] product = products.getBody();
    return Arrays.asList(product);

  }


  public TransactionResponse saveOrder(TransactionRequest request) {

    String response = "";
    Order order = request.getOrder();
    Payment payment = request.getPayment();
    payment.setOrderId(order.getId());
    payment.setAmount(order.getPrice());
    RestTemplate restTemplate = new RestTemplate();
    System.out.println("Inside Trasction paymen");
    Payment paymentResponse = restTemplate.postForObject("http://localhost:8088/payment/doPayment",
        payment, Payment.class);

    response =
        paymentResponse.getPaymentStatus().equals("success") ? "payment processing successful"
            : "there is a failure";

    repository.save(order);

    System.out.println("order -->" + order);
    System.out.println("Amount -->" + paymentResponse.getAmount());
    System.out.println("TransactionId -->" + paymentResponse.getTransactionId());
    
    String apiUrl = "http://localhost:8087/product";
    HttpHeaders headers = new HttpHeaders();
    Product updatedProduct = new Product();
    HttpEntity<Product> requestEntity = new HttpEntity<>(updatedProduct, headers);
    ResponseEntity<Object> res = restTemplate.exchange(apiUrl + "/{id}/{qty}", HttpMethod.PUT,
        requestEntity, Object.class, 1, order.getQty());
    
    rbmqTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY,order );


    return new TransactionResponse(order, paymentResponse.getAmount(),
        paymentResponse.getTransactionId(), response);
  }
}
