package PaymentService.service;



import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import PaymentService.entity.Payment;



@Service
public class PaymentService {
  //@Autowired
 // private PaymentRepository repository;

  public String doPayment(Payment payment) {  
//    payment.setPaymentStatus("success");
//    
//    payment.setTransactionId(UUID.randomUUID().toString());
    System.out.println("PaymentService.doPayment()");
    return new Payment().toString();
    		//repository.save(payment);
  }

  public Payment findPaymentHistoryByOrderId(int orderId) {
   
    //return repository.findByOrderId(orderId);
	  return  new Payment();
  }
}
