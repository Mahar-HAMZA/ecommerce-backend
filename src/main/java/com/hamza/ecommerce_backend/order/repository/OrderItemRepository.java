package com.hamza.ecommerce_backend.order.repository;

import com.hamza.ecommerce_backend.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
