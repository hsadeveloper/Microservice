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
import orderservice.entity.Invoice;
import orderservice.entity.Order;
import orderservice.entity.Product;
import orderservice.exception.CustomExceptionMessage;
import orderservice.repository.InvoiceRepository;
import orderservice.repository.OrderRepository;

@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class OrderService {

    private final InvoiceRepository repository;
    private final RabbitTemplate rbmqTemplate;

    public OrderService(InvoiceRepository repository, RabbitTemplate rbmqTemplate) {
        this.repository = repository;
        this.rbmqTemplate = rbmqTemplate;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public List<Product> getProducts() throws CustomExceptionMessage {
        RestTemplate restTemplate = new RestTemplate();

        try {
            String url = "http://localhost:1727/product/all";
            ResponseEntity<Product[]> productsResponse = restTemplate.getForEntity(url, Product[].class);
            Product[] products = productsResponse.getBody();
            return Arrays.asList(products);
        } catch (Exception e) {
            throw new CustomExceptionMessage("Connection to this 'localhost:1987/product/all' endpoint on store service failed", e);
        }
    }

    //@Transactional(propagation = Propagation.MANDATORY)
    public TransactionResponse saveOrder(TransactionRequest request) throws CustomExceptionMessage {
        String response = "";
        RestTemplate restTemplate = new RestTemplate();
        Payment paymentResponse = null;
        Order order = request.getOrder();
        Invoice invoice = new Invoice();
        Payment payment = request.getPayment();

        try {
            invoice.setOrderName(order.getName());
            invoice.setQty(order.getQty());
            invoice.setPrice(order.getPrice());
            invoice.setTax(payment.getTax());
            invoice.setStatus(payment.getPaymentStatus());
            invoice.setOrderName(order.getName());
            invoice.setTotal(payment.getTotal());
            invoice.setFullAdrress(payment.getFullAddress());

            System.out.println("payment orderservice ====> " + payment);
            
            paymentResponse = restTemplate.postForObject("http://localhost:8088/payment/doPayment", payment, Payment.class);

            response = paymentResponse.getPaymentStatus().equals("success") ? "payment processing successful" : "there is a failure";
            invoice.setTransactionId(paymentResponse.getTransactionId());

            repository.save(invoice);
        } catch (Exception e) {
            throw new CustomExceptionMessage("Failed to Access Payment endpoint on `http://localhost:8088/payment/doPayment`");
        }

        String apiUrl = "http://localhost:1727/product/sell";
        HttpHeaders headers = new HttpHeaders();
        Product updatedProduct = new Product();
        HttpEntity<Product> requestEntity = new HttpEntity<>(updatedProduct, headers);
        
        ResponseEntity<Object> res = restTemplate.exchange(apiUrl + "/{id}/{qty}", HttpMethod.PUT, requestEntity, Object.class, order.getProductId(), order.getQty());

        System.out.println("invoice -->" + invoice);
        
        rbmqTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY, invoice);

        return new TransactionResponse(order, paymentResponse.getAmount(), paymentResponse.getTransactionId(), response);
    }
}
