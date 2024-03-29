package store.service.entity;




import java.sql.Timestamp;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

//@XmlRootElement

@Entity
@Table(name = "manufacturer")
public class Manufacturer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "manufacturer_id")
  private Long manufacturerId;

  @Column(name = "manufacturer_name")
  private String manufacturerName;
  
  @Column(name = "manufacturer_origin")
  private String manufactureOrigin;

  @Column(name = "years_warranty")
  private int  yearsWarranty;
  
  @CreationTimestamp
  @Column(name = "created_timestamp")
  private Timestamp createdTimestamp;
  
  //The owner is always the “Many” side
  @OneToMany (mappedBy="manufacturer")
  private Set<Product> product;
  
  
  
  

public Manufacturer() {
	super();
	// TODO Auto-generated constructor stub
}

public Manufacturer(Long manufacturerId, String manufacturerName, Timestamp createdTimestamp) {
	super();
	this.manufacturerId = manufacturerId;
	this.manufacturerName = manufacturerName;
	this.createdTimestamp = createdTimestamp;
}

public Long getManufacturerId() {
	return manufacturerId;
}

public void setManufacturerId(Long manufacturerId) {
	this.manufacturerId = manufacturerId;
}

public String getManufacturerName() {
	return manufacturerName;
}

public void setManufacturerName(String manufacturerName) {
	this.manufacturerName = manufacturerName;
}

public Timestamp getCreatedTimestamp() {
	return createdTimestamp;
}

public void setCreatedTimestamp(Timestamp createdTimestamp) {
	this.createdTimestamp = createdTimestamp;
}

@Override
public String toString() {
	return "Manufacturer [manufacturerId=" + manufacturerId + ", manufacturerName=" + manufacturerName
			+ ", createdTimestamp=" + createdTimestamp + "]";
}

//  @OneToMany(mappedBy = "manufacturer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//  private Set<Product> products;
  
  
  



}
