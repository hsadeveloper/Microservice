package onlinestore.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlRootElement;


@Entity
@XmlRootElement
@Table(name = "product")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "product_id")
  private Long productId;

  @Column(name = "product_name")
  private String productName;

  @Column(name = "description")
  private String description;


  @ManyToOne // (fetch = FetchType.LAZY)
  @JoinColumn(name = "dept_id")
  private Department department;

  // QUANTRITY
  private int qty;

  @ManyToOne // (fetch = FetchType.LAZY)
  @JoinColumn(name = "manuf_id")
  private Manufacturer manufacturer;

  public Product(Long productId, String productName, String description, Department department,
      Manufacturer manufacturer) {
    super();
    this.productId = productId;
    this.productName = productName;
    this.description = description;
    this.department = department;
    this.manufacturer = manufacturer;
  }

  public Product() {
    super();
    // TODO Auto-generated constructor stub
  }

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Department getDepartment() {
    return department;
  }

  public void setDepartment(Department department) {
    this.department = department;
  }

  public Manufacturer getManufacturer() {
    return manufacturer;
  }

  public void setManufacturer(Manufacturer manufacturer) {
    this.manufacturer = manufacturer;
  }



  public int getQty() {
    return qty;
  }

  public void setQty(int qty) {
    this.qty = qty;
  }

  @Override
  public String toString() {
    return "Product [productId=" + productId + ", productName=" + productName + ", description="
        + description + ", department=" + department + ", manufacturer=" + manufacturer + "]";
  }



}
