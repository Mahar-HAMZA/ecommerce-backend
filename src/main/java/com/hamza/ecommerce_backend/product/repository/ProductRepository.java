package com.hamza.ecommerce_backend.product.repository;


import com.hamza.ecommerce_backend.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    public boolean existsByProductName(String productName);
}
