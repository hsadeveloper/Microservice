package store.service.entity;



import java.sql.Timestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;



@Entity
@Data
@Table(name = "inventory")
public class Inventory {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "inventory_Id")
  private Long inventoryId;


  @Column(name = "created_timestamp")
  private Timestamp createdTimestamp;

  @OneToOne
  @JoinColumn(name = "product_id", nullable = false)
  private Product product;

  @OneToOne
  @JoinColumn(name = "manufacturer_id", nullable = false)
  private Manufacturer manufacturer;


}
