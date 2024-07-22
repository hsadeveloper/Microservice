package store.service.entity;




import java.sql.Timestamp;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

//import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
  
  @Column(name = "created_timestamp")
  private Timestamp createdTimestamp;
  
  //Inverse side
  @ManyToMany (mappedBy="manufacturers", cascade =CascadeType.ALL)

  private Set<Product> product;

  
  
public Manufacturer() {
	super();
	// TODO Auto-generated constructor stub
}

public Manufacturer(String manufacturerName, String manufactureOrigin, int yearsWarranty) {
	super();
	this.manufacturerName = manufacturerName;
	this.manufactureOrigin = manufactureOrigin;
	this.yearsWarranty = yearsWarranty;
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

public String getManufactureOrigin() {
	return manufactureOrigin;
}

public void setManufactureOrigin(String manufactureOrigin) {
	this.manufactureOrigin = manufactureOrigin;
}

public int getYearsWarranty() {
	return yearsWarranty;
}

public void setYearsWarranty(int yearsWarranty) {
	this.yearsWarranty = yearsWarranty;
}

public Set<Product> getProduct() {
	return product;
}

public void setProduct(Set<Product> product) {
	this.product = product;
}
  
  
 




//  @OneToMany(mappedBy = "manufacturer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//  private Set<Product> products;
  
  
  



}
