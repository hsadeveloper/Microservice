package cart.model;

public class CartItem {

	private String productId;
    private int quantity;
    
	public CartItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CartItem(String productId, int quantity) {
		super();
		this.productId = productId;
		this.quantity = quantity;
	}

	public String getProductId() {
		return productId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "CartItem [productId=" + productId + ", quantity=" + quantity + "]";
	}
	
	
    
    

}
