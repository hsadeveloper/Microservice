package cart.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;

import cart.model.Cart;
import cart.model.CartItem;
import cart.repository.CartRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;
    

    @Autowired
    private RedisTemplate<String, Cart> redisTemplate;

    public Optional<Cart> getCart(String userId) {
    	System.out.println("get cart ------------>  "+userId);
        return cartRepository.findById(userId);
    }

    public Cart addItem(String userId, CartItem item) {
    	 String redisKey = "cart:" + userId;
    	 System.out.println("addItem ------------>  " + item.toString());
        // Retrieve existing cart or create a new one
        Cart cart = cartRepository.findById(userId).orElse(new Cart());
        System.out.println("cart ------------>  " + cart.toString());

        cart.setUserId(userId);

        // Initialize the items list if null
        List<CartItem> items = cart.getItems();
        if (items == null) {
            items = new ArrayList<>();
        }

        // Check if item already exists in cart
        boolean updated = false;
        for (CartItem existingItem : items) {
            if (existingItem.getProductId().equals(item.getProductId())) {
            	// ✅ Update quantity if product already exists
                existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
                
                updated = true;
                break;
            }
        }
        

        // If item does not exist, add it
        if (!updated) {
            items.add(item);
        }

        cart.setItems(items);
        System.err.println("Updated items size: " + items.size());
        System.out.println("cart ------------>  " + cart.toString());
        // Save to repository (could be Redis or Mongo, depending on your implementation)
     // Save the updated cart to Redis
       cartRepository.save(cart);
        return cart;
    }
    
    public void updateItemQuantity(String userId, String productId, int newQuantity) {
        Optional<Cart> optionalCart = cartRepository.findById(userId);
        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            System.out.println("updateItemQuantity  --> "+ cart.toString());
            for (CartItem item : cart.getItems()) {
                if (item.getProductId().equals(productId)) {
                	System.out.println("updateItemQuantity  --> "+ item.getProductId()+ "----> "+ item.getQuantity()+ "--->  "+newQuantity);
                    item.setQuantity(newQuantity);
                    System.out.println("updateItemQuantity  --> "+ item.getProductId()+ "----> "+ item.getQuantity()+ "--->  "+newQuantity);
                    break;
                }
            }
            cartRepository.save(cart);
        } else {
            throw new RuntimeException("Cart not found for userId: " + userId);
        }
    }


	public void deleteById(String userId) {
		cartRepository.deleteById(userId);
	}

    public String checkout(String userId) {
    	System.err.println("I'm in checkout serrvice");
        Cart cart = cartRepository.findById(userId).orElse(null);
        if (cart == null || cart.getItems() == null || cart.getItems().isEmpty()) {
            return "Cart is empty.";
        }
        Cart Cart = new Cart();
        Cart.setUserId(userId);
        Cart.setItems(cart.getItems());
        //redisTemplate.opsForValue().set("checkout:" + userId, checkoutCart);
        cartRepository.deleteById(userId);
        System.out.println("checkout ......................> "+ Cart);
        
        return "Checkout successful.";
    }

    public Cart getCheckedOutCart(String userId) {
        return redisTemplate.opsForValue().get("checkout:" + userId);
    }
//    
//    public void saveCart(CheckoutCart cart) {
//        String key = CART_KEY_PREFIX + cart.getUserId();
//        redisTemplate.opsForValue().set(key, cart);
//    }
}
