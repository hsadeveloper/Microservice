package store.service.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    
    @Column(name = "serial_number", unique = true)
    private String serialNumber;

    @PrePersist
    public void generateSerialNumber() {
        this.serialNumber = UUID.randomUUID().toString();
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_id")
    @JsonIgnore // Avoid serialization issues with lazy-loaded properties
    private Department department;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "product_manufacturer",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "manufacturer_id")
    )
    @JsonIgnore // Avoid serialization issues
    private Set<Manufacturer> manufacturers = new HashSet<>();

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "inventory_id")
    @JsonManagedReference // Manage bidirectional serialization
    private Inventory inventory;
}