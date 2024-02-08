package PaymentService.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import PaymentService.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

  Payment findByOrderId(int orderId);

}
