package com.hamza.ecommerce_backend.product.controller;

import com.hamza.ecommerce_backend.product.DTO.ProductCreateDTO;
import com.hamza.ecommerce_backend.product.DTO.ProductDTO;
import com.hamza.ecommerce_backend.product.DTO.ProductUpdateDTO;
import com.hamza.ecommerce_backend.product.entity.Product;
import com.hamza.ecommerce_backend.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    ProductService productService;

    public ProductController(ProductService productService){
        this.productService=productService;
    }

    @PostMapping
    public ProductDTO createProduct(@RequestBody @Valid ProductCreateDTO dto){
        return productService.createProduct(dto);
    }

    @GetMapping
    public List<ProductDTO> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable Long id){
        return productService.getProductById(id);
    }

    @PutMapping("/{id}")
    public ProductDTO updateProduct(@RequestBody ProductUpdateDTO product, @PathVariable Long id){
        return productService.updateProduct(product, id);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
    }
}
