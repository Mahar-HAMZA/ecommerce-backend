package com.hamza.ecommerce_backend.product.controller;

import com.hamza.ecommerce_backend.product.entity.Product;
import com.hamza.ecommerce_backend.product.service.ProductService;
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
    public Product createProduct(@RequestBody Product product){
        return productService.createProduct(product);
    }


}
