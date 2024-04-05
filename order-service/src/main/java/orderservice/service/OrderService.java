package orderservice.service;
import java.util.Arrays;
import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import orderservice.common.Payment;
import orderservice.common.TransactionRequest;
import orderservice.common.TransactionResponse;
import orderservice.configuration.RabbitMQConfig;
import orderservice.entity.Order;
import orderservice.entity.Product;
import orderservice.exception.CustomExceptionMessage;
import orderservice.repository.OrderRepository;


@Service
@Transactional(propagation =Propagation.SUPPORTS)
public class OrderService {

 
 private final OrderRepository repository;
 private final RabbitTemplate rbmqTemplate;

 public OrderService(OrderRepository repository, RabbitTemplate rbmqTemplate) {
     this.repository = repository;
     this.rbmqTemplate = rbmqTemplate;
 }

 @Transactional(propagation = Propagation.MANDATORY)
 public List<Product> getProducts() throws CustomExceptionMessage {
     
     // Create a RestTemplate instance
     RestTemplate restTemplate = new RestTemplate();
     
     try {
         // Define the URL you want to send the GET request to
         String url = "http://localhost:1987/product/all";
         
         // Send the GET request and receive the response
         ResponseEntity<Product[]> productsResponse = restTemplate.getForEntity(url, Product[].class);
         
         // Extract products from the response and return as a list
         Product[] products = productsResponse.getBody();
         return Arrays.asList(products);
     } catch (Exception e) {
         // Catch any exception that occurs during the request
         // and rethrow it as PullProjectException
         throw new CustomExceptionMessage("Connection to this'localhost:1987/product/all' enpoint on store service  failed", e);
     }
 }



@Transactional(propagation =Propagation.MANDATORY)
  public TransactionResponse saveOrder(TransactionRequest request)throws CustomExceptionMessage {

	String response = "";
	RestTemplate restTemplate = new RestTemplate();
	Payment paymentResponse = null;
    Order order = request.getOrder();
    try {
    Payment payment = request.getPayment();
    payment.setOrderId(order.getId());
    payment.setAmount(order.getPrice());
   
 
     paymentResponse = restTemplate.postForObject("http://localhost:8088/payment/doPayment",
        payment, Payment.class);

    response =
        paymentResponse.getPaymentStatus().equals("success") ? "payment processing successful"
            : "there is a failure";
    repository.save(order);
    }catch(Exception e) {
     throw new CustomExceptionMessage("Failed to Access Payment endpoint on `http://localhost:8088/payment/doPayment`");
    }
//    System.out.println("order -->" + order);
//    System.out.println("Amount -->" + paymentResponse.getAmount());
//    System.out.println("TransactionId -->" + paymentResponse.getTransactionId());
    
    // DECREMENTING QTY
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
