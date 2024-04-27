package store.service.entity;


public class ProductDTO {
	
	private String productName;
	private String description;
	private Double productPrice;
	private Long  departmentId;
	private Long inventoryId;
	
	public ProductDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProductDTO(Long producId, String description, Double productPrice, Long departmentId, Long inventoryId) {
		super();
		this.description = description;
		this.description = description;
		this.productPrice = productPrice;
		this.departmentId = departmentId;
		this.inventoryId = inventoryId;
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

	public Double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Double productPrice) {
		this.productPrice = productPrice;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public Long getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(Long inventoryId) {
		this.inventoryId = inventoryId;
	}
	
	
	
	
}
