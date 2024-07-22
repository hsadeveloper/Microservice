package orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import orderservice.entity.Invoice;
import orderservice.entity.Order;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

}
