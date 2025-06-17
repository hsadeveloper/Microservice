package shipmentservice.entity;

import org.springframework.lang.NonNull;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
@Entity
//@SQLDelete
@Table(name = "invoices")
public class Invoice {

	@Id
	private long id;
	
    @Nonnull
    @Column(name = "status")
    private String status ="pending";
    
    private String fullAdrress;
    
    private String orderName;
    
    private int orderId;
    
    private int qty;
    
    private double price;
    
    @NonNull
    @Column(name = "total")
    private Double total;
    
    @NonNull
    @Column(name = "tax")
    private Double tax;
    
    private String transactionId;

	public Invoice() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Invoice(String status, String fullAdrress, String orderName, int orderId, int qty, double price,
			Double total, Double tax, String transactionId) {
		super();
		this.status = status;
		this.fullAdrress = fullAdrress;
		this.orderName = orderName;
		this.orderId = orderId;
		this.qty = qty;
		this.price = price;
		this.total = total;
		this.tax = tax;
		this.transactionId = transactionId;
	}
    
}
	
    

