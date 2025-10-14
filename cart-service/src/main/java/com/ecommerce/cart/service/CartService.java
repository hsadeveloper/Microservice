package com.ecommerce.cart.service;

import com.commonlib.security.JwtTokenValidator;
import com.ecommerce.cart.model.Cart;
import com.ecommerce.cart.model.CartItem;
import com.ecommerce.cart.repository.CartRepository;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final JwtTokenValidator jwtTokenValidator;

    public CartService(CartRepository cartRepository, JwtTokenValidator jwtTokenValidator) {
        this.cartRepository = cartRepository;
        this.jwtTokenValidator = jwtTokenValidator;
    }

//    public String extractUserId(String token) {
//        return jwtTokenValidator.extractUsername(token.replace("Bearer ", ""));
//    }

    public Cart getCartByUser(String userId) {
        return cartRepository.findByUserId(userId).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUserId(userId);
            newCart.setItems(new ArrayList<>());
            return cartRepository.save(newCart);
        });
    }

    public Cart addItemToCart(String userId, String productId, int quantity) {
        Cart cart = getCartByUser(userId);
        CartItem item = new CartItem();
        item.setProductId(productId);
        item.setQuantity(quantity);
        item.setPrice(100.0);
        cart.getItems().add(item);
        return cartRepository.save(cart);
    }
}
