package cart.controller;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;



import cart.model.Cart;
import cart.model.CartItem;
import cart.service.CartService;
import ch.qos.logback.core.model.Model;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;
    

    
    //http://localhost:2088/cart?userId=999
    @GetMapping
    public ResponseEntity<Optional<Cart>> getCartByUserId(@RequestParam("userId") String userId) {
    	System.out.println("In sside get CART ----->>>>>>>");
        Optional<Cart> cart = cartService.getCart(userId);
        System.out.println("CART ----->>>>>>> "+cart.toString());
       
        if (cart != null) {
            return ResponseEntity.ok(cart);
        } else {
        	 
            return ResponseEntity.notFound().build();
        }
    }


//    @GetMapping("/{userId}")
//    public Cart getCart(@PathVariable String userId) {
//    	System.err.println("In sside get CART ----->>>>>>>");
//        return cartService.getCart(userId);
//    }
    
    @PostMapping("/add")
    public ResponseEntity<Optional<Cart>> addItem(
        @RequestParam("userId") String userId,
        @RequestParam("productId") String productId,
        @RequestParam("quantity") int quantity
    ) {
        System.err.println("In side add item to CART ----->>>>>>>");

        // Retrieve the cart for the user
        Optional<Cart> cart = cartService.getCart(userId); // Assuming userId is a string in DB
        System.err.println("CART ----->>>>>>> " + cart);

        if (cart.isEmpty()) {
            // Create a new CartItem
            CartItem item = new CartItem();
            System.out.println("productId ---> "+productId);
            item.setProductId(productId);
            item.setQuantity(quantity);
            System.out.println("item ---> "+item);

            // Add the item to the cart
            cartService.addItem(userId, item);

            // Return the updated cart
            return ResponseEntity.ok(cart);
        } else {
            // If the cart does not exist, return 404 Not Found
            return ResponseEntity.notFound().build();
        }
    }

    
    @DeleteMapping()
    public ResponseEntity<Void> deleteCart(@RequestParam("userId")  String userId) {
        Optional<Cart> cart = cartService.getCart(userId);

        if (cart.isPresent()) {
            cartService.deleteById(cart.get().getUserId());
            return ResponseEntity.noContent().build(); // 204
        } else {
            return ResponseEntity.notFound().build(); // 404
        }       
    }
    
    
    @PutMapping("/update")
    public ResponseEntity<String> updateQuantity(
            @RequestParam("userId") String userId,
            @RequestParam("productId") String productId,
            @RequestParam("quantity") int quantity) {
        
        cartService.updateItemQuantity(userId, productId, quantity);
        return ResponseEntity.ok("Quantity updated.");
    }

    @PostMapping("/checkout")
    public String checkout(@RequestParam("userId") String userId) {
        
        System.err.println("I'm in checkout controller");
        String url = "http://localhost:8087/payment/doPayment";
        // Create HttpHeaders
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        // Prepare request body
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setAmount(1000L); // amount in cents
        paymentRequest.setCurrency("usd");

        // Build the HttpEntity with headers and body
        HttpEntity<PaymentRequest> requestEntity = new HttpEntity<>(paymentRequest, headers);

        // Use RestTemplate to send POST request
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
        
        return cartService.checkout(userId);
    }
 

 
   
}