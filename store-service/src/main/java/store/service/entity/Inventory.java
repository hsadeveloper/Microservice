package store.service.entity;



import java.sql.Date;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.CascadeType;
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
import lombok.Data;



@Entity
@Data
@Table(name = "inventory")
public class Inventory {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "inventory_Id")
  private Long inventoryId;
  

  @ManyToOne(cascade = CascadeType.ALL) // Enable cascading persistence
  @JoinColumn(name = "manufacturer_id", nullable = false)
  private Manufacturer manufacturer;
  
  @NotBlank(message = "The  is required.")
  @Column(name = "unit_cost")
  private double unitCost; 
  
  @NotNull (message="qty can'nt be null")
  @Size(min = 1)
  private int qty;
  
  @Column(name = "qty_sold")
  @ColumnDefault("0")
  private int qtySold = 0;
  
  private String location;
  
  @Column(name = "last_sold", updatable = false)
  private Date lastSold;
  
  /* No inverse side for OneToOne*/


}
