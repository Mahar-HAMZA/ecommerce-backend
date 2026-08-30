package com.hamza.ecommerce_backend.product.mapper;

import com.hamza.ecommerce_backend.product.DTO.ProductCreateDTO;
import com.hamza.ecommerce_backend.product.DTO.ProductDTO;
import com.hamza.ecommerce_backend.product.entity.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductMapper {

    public Product toEntity(ProductCreateDTO dto){
        Product product = new Product();
        product.setProductName(dto.getProductName());
        product.setPrice(dto.getPrice());
        product.setStockQuantity(dto.getStockQuantity());
        product.setDescription(dto.getDescription());
        product.setStatus(dto.getStatus());
        return product;
    }

    public ProductDTO toDTO(Product product){

        ProductDTO dto=new ProductDTO();
        dto.setProductName(product.getProductName());
        dto.setPrice(product.getPrice());
        dto.setStockQuantity(product.getStockQuantity());
        dto.setDescription(product.getDescription());
        dto.setStatus(product.getStatus());
        dto.setCategoryId(product.getCategory().getId());
        dto.setCreatedAt(product.getCreatedAt());
        dto.setUpdatedAt(product.getUpdatedAt());
        dto.setId(product.getId());
        return dto;
    }

    public List<ProductDTO> allProductsToDTO(List<Product> products){
        List<ProductDTO> dtos=new ArrayList<>();
        for(Product product:products){
            dtos.add(toDTO(product));
        }
        return dtos;
    }
}
