package com.ecommerce.cart.controller;

import com.ecommerce.cart.model.Cart;
import com.ecommerce.cart.service.CartService;


import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<Cart> getCart() {
    	User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	System.out.println("Current logged-in user: " + user.getUsername());
        return ResponseEntity.ok(cartService.getCartByUser(user.getUsername()));
    }

    @PostMapping
    public ResponseEntity<Cart> addItem(@RequestParam ("productId") String productId,
                                        @RequestParam ("quantity") int quantity) {
    	User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	System.out.println("Current logged-in user: " + user.getUsername());
        return ResponseEntity.ok(cartService.addItemToCart(user.getUsername(), productId, quantity));

    }
}
