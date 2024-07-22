package store.service.entity;

import lombok.Data;

@Data
public class ProductDTO {
	
	private String productName;
	private String description;
	private String serialNumber;
	private Double productPrice;
	private Long  departmentId;
	private Long inventoryId;
	
}
