package orderservice.entity;

import org.hibernate.annotations.SQLDelete;
import org.springframework.lang.NonNull;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "invoices")
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Nonnull
    @Column(name = "status")
    private String status = "pending";

    private String fullAdrress;

    private String orderName;

    private int orderId;

    private int qty;

    private double price;

    @Nonnull
    @Column(name = "total")
    private Double total;

    @Nonnull
    @Column(name = "tax")
    private Double tax;

    private String transactionId;
}
