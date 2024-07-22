package orderservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")  // Change the table name
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) // Add auto-increment for ID
  private int id;
  private String name;
  private int qty;
  private double price;
  private int productId;
}

