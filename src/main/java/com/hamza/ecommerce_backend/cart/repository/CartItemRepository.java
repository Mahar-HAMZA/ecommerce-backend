package com.hamza.ecommerce_backend.cart.repository;

import com.hamza.ecommerce_backend.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
