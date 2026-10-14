package com.hamza.ecommerce_backend.product.repository;


import com.hamza.ecommerce_backend.product.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import java.math.BigDecimal;

public interface ProductRepository extends JpaRepository<Product, Long> {

    public boolean existsByProductName(String productName);
    public boolean existsByProductNameAndIdNot(String productName, Long id);
    public boolean existsBycategory_Id(Long categoryId);
    public Page<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);
    public Page<Product> findByPriceGreaterThanEqual(BigDecimal minPrice, Pageable pageable);
    public Page<Product> findByPriceLessThanEqual(BigDecimal maxPrice, Pageable pageable);
}
