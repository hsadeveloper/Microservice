package cart.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;

@Document("carts")
public class Cart {
    @Id
    public String userId;
    private List<CartItem> items;
    
	public Cart() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Cart(String userId, List<CartItem> items) {
		super();
		this.userId = userId;
		this.items = items;
	}

	public String getUserId() {
		return userId;
	}

	public List<CartItem> getItems() {
		return items;
	}

	public void setUserId(String userId2) {
		this.userId = userId2;
	}

	public void setItems(List<CartItem> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "Cart [userId=" + userId + ", items=" + items + "]";
	}
	
	
	
}