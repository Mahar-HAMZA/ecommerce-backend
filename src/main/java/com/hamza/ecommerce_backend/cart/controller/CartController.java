package com.hamza.ecommerce_backend.cart.controller;

import com.hamza.ecommerce_backend.cart.DTO.AddCartDTO;
import com.hamza.ecommerce_backend.cart.DTO.CartResponseDTO;
import com.hamza.ecommerce_backend.cart.DTO.UpdateCartItemDTO;
import com.hamza.ecommerce_backend.cart.service.CartService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public CartResponseDTO addToCart(@RequestBody @Valid AddCartDTO dto) {

        return cartService.addToCart(dto);
    }

    @GetMapping
    public CartResponseDTO getMyCart() {
        return cartService.getMyCart();
    }

    @PutMapping("/items/{cartItemId}")
    public CartResponseDTO updateCartItem(@PathVariable Long cartItemId, @RequestBody @Valid UpdateCartItemDTO dto) {

        return cartService.updateCartItem(cartItemId, dto);
    }

    @DeleteMapping("/items/{cartItemId}")
    public CartResponseDTO removeCartItem(
            @PathVariable Long cartItemId) {

        return cartService.removeCartItem(cartItemId);
    }

    @DeleteMapping
    public CartResponseDTO clearCart() {
        return cartService.clearCart();
    }

}