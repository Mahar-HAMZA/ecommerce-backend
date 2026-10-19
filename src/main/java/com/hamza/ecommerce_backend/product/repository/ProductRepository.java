package com.hamza.ecommerce_backend.product.repository;


import com.hamza.ecommerce_backend.product.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface ProductRepository extends JpaRepository<Product, Long> {

    public boolean existsByProductName(String productName);
    public boolean existsByProductNameAndIdNot(String productName, Long id);
    public boolean existsBycategory_Id(Long categoryId);
    public Page<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);
    public Page<Product> findByPriceGreaterThanEqual(BigDecimal minPrice, Pageable pageable);
    public Page<Product> findByPriceLessThanEqual(BigDecimal maxPrice, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE (:search IS NULL OR LOWER(p.productName) LIKE LOWER(CONCAT('%', :search, '%')))" +
            " AND (:minPrice is NULL OR p.price >= :minPrice) " +
            "AND (:maxPrice is NULL OR p.price <= :maxPrice)")
    public Page<Product> searchByProductName(@Param("search") String search,
                                             @Param("minPrice") BigDecimal minPrice,
                                             @Param("maxPrice") BigDecimal maxPrice ,Pageable pageable);


}
