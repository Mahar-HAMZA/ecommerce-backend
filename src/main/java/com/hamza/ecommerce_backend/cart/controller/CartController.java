package com.hamza.ecommerce_backend.cart.controller;

import com.hamza.ecommerce_backend.cart.DTO.AddCartDTO;
import com.hamza.ecommerce_backend.cart.DTO.CartResponseDTO;
import com.hamza.ecommerce_backend.cart.DTO.UpdateCartItemDTO;
import com.hamza.ecommerce_backend.cart.service.CartService;
import com.hamza.ecommerce_backend.order.DTO.OrderDTO;
import com.hamza.ecommerce_backend.order.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;
    private final OrderService orderService;

    public CartController(CartService cartService, OrderService orderService) {
        this.cartService = cartService;
        this.orderService=orderService;
    }

    @PostMapping
    public CartResponseDTO addToCart(@RequestBody @Valid AddCartDTO dto) {

        return cartService.addToCart(dto);
    }

    @PostMapping("/checkout")
    public OrderDTO checkoutCart() {
        return orderService.checkoutCart();
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