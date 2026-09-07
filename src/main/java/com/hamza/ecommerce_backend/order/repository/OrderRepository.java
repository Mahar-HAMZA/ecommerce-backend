package com.hamza.ecommerce_backend.order.repository;

import com.hamza.ecommerce_backend.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
