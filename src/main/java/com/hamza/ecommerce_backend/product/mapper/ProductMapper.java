package com.hamza.ecommerce_backend.product.mapper;

import com.hamza.ecommerce_backend.product.DTO.ProductCreateDTO;
import com.hamza.ecommerce_backend.product.DTO.ProductDTO;
import com.hamza.ecommerce_backend.product.DTO.ProductUpdateDTO;
import com.hamza.ecommerce_backend.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProductMapper {

    public Product toEntity(ProductCreateDTO dto){
        Product product = new Product();
        product.setProductName(dto.getProductName());
        product.setPrice(BigDecimal.valueOf(dto.getPrice()));
        product.setStockQuantity(dto.getStockQuantity());
        product.setDescription(dto.getDescription());
        product.setStatus(dto.getStatus());
        return product;
    }

    public ProductDTO toDTO(Product product){

        ProductDTO dto=new ProductDTO();
        dto.setProductName(product.getProductName());
        dto.setPrice(product.getPrice().doubleValue());
        dto.setStockQuantity(product.getStockQuantity());
        dto.setDescription(product.getDescription());
        dto.setStatus(product.getStatus());
        dto.setCategoryId(product.getCategory().getId());
        dto.setCreatedAt(product.getCreatedAt());
        dto.setUpdatedAt(product.getUpdatedAt());
        dto.setId(product.getId());
        return dto;
    }

    public Page<ProductDTO> allProductsToDTO(Page<Product> products) {
        return products.map(this::toDTO);
    }

    public Product updateProduct(Product existingProduct, ProductUpdateDTO dto){
        if(dto.getProductName() != null){
            existingProduct.setProductName(dto.getProductName());
        }
        if(dto.getDescription() != null){
            existingProduct.setDescription(dto.getDescription());
        }
        if(dto.getPrice() != null){
            existingProduct.setPrice(BigDecimal.valueOf(dto.getPrice()));
        }
        if(dto.getStatus() != null){
            existingProduct.setStatus(dto.getStatus());
        }
        if(dto.getStockQuantity() != null){
            existingProduct.setStockQuantity(dto.getStockQuantity());
        }
        return existingProduct;
    }

}
