package com.hamza.ecommerce_backend.order.repository;

import com.hamza.ecommerce_backend.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    public List<Order> findByUser_EmailOrderByCreatedAtDesc(String email);
    public List<Order> findAllByOrderByCreatedAtDesc();

}
