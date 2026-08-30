package com.hamza.ecommerce_backend.product.DTO;

import com.hamza.ecommerce_backend.product.entity.ProductStatus;

public class ProductCreateDTO {

    private String productName;
    private String description;
    private double price;
    private int stockQuantity;
    private ProductStatus status;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
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
