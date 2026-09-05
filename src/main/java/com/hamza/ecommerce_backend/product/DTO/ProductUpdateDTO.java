package com.hamza.ecommerce_backend.product.DTO;

import com.hamza.ecommerce_backend.product.entity.ProductStatus;
import jakarta.validation.constraints.*;

public class ProductUpdateDTO {

    @Size(max = 100, message = "Product name must not exceed 100 characters.")
    @Pattern(regexp = ".*[A-Za-z].*", message = "Product name must contain at least one letter.")
    private String productName;
    private String description;

    @Positive(message = "Price must be greater than zero")
    private Double price;

    @PositiveOrZero(message = "Stock quantity must not be negative.")
    private Integer stockQuantity;

    private ProductStatus status;

    private Long categoryId;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
