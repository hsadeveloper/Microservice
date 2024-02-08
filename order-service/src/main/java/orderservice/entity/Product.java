package orderservice.entity;

import lombok.Data;

@Data

public class Product {

  private Long productId;

  private String productName;

  private String description;

  private int qty;


}
