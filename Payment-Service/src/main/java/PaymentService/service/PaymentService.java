package PaymentService.service;



import java.util.Random;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import PaymentService.entity.Payment;
import PaymentService.repository.PaymentRepository;


@Service
public class PaymentService {
  @Autowired
  private PaymentRepository repository;

  public Payment doPayment(Payment payment) {
    payment.setPaymentStatus(paymentProcessing());
    payment.setTransactionId(UUID.randomUUID().toString());
    return repository.save(payment);
  }

  public String paymentProcessing() {
    // for others payment
    return new Random().nextBoolean() ? "success" : "false";
  }

  public Payment findPaymentHistoryByOrderId(int orderId) {

    // TODO Auto-generated method stub
    return repository.findByOrderId(orderId);
  }
}
