package store.service.entity;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@Entity
@XmlRootElement
@Table(name = "product")
public class Product {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "product_id")
  private Long productId;

  @NotNull(message = "Product Name is mandatory")
  @NotBlank(message = "Product Name is mandatory")
  @Column(name = "product_name")
  private String productName;

  @NotNull
  @Column(name = "product_price")
  private double productPrice;
  

  @Column(name = "description")
  private String description;
  

  /*Many product belong to one department */
  @ManyToOne
  @JoinColumn(name = "dept_id")
  private Department department;
  
  @NotNull
  @Size(min = 1)
  private int qty;

  /* Many product belong to one manufacturer */
  @ManyToOne 
  @JoinColumn(name = "manuf_id")
  private Manufacturer manufacturer;

  

  
  


}
