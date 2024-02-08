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
@Table(name = "product_detail")
public class ProductDetail {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "product_detail_id")
  private Long productDetailId;

  @Column(name = "manufacturer_id")
  private int manufacturerId;


  @OneToOne
  @JoinColumn(name = "product_id")
  private Product productId;



  @Column(name = "serial_number")
  private String serialNumber;

  @Column(name = "tag_number")
  private String tagNumber;



  @Column(name = "created_timestamp")
  private Timestamp createdTimestamp;

  @Column(name = "last_updated_timestamp")
  private Timestamp lastUpdatedTimestamp;

}
