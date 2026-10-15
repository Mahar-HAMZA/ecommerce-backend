package com.hamza.ecommerce_backend.product.DTO;

import com.hamza.ecommerce_backend.product.entity.ProductStatus;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class ProductCreateDTO {

    @NotBlank(message = "Product name must not be blank")
    @Size(max = 100, message = "Product name must not exceed 100 characters.")
    @Pattern(regexp = ".*[A-Za-z].*", message = "Product name must contain at least one letter.")
    private String productName;
    private String description;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than zero")
    private BigDecimal price;

    @NotNull(message = "Stock quantity is required")
    @PositiveOrZero(message = "Stock quantity must not be negative.")
    private Integer stockQuantity;

    @NotNull(message = "Product status is required.")
    private ProductStatus status;

    @NotNull(message = "Category ID is required.")
    private Long category_Id;

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
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

    public Long getCategory_Id() {
        return category_Id;
    }

    public void setCategory_Id(Long category_Id) {
        this.category_Id = category_Id;
    }
}
