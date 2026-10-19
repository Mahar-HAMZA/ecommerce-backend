package com.hamza.ecommerce_backend.product.controller;

import com.hamza.ecommerce_backend.product.DTO.ProductCreateDTO;
import com.hamza.ecommerce_backend.product.DTO.ProductDTO;
import com.hamza.ecommerce_backend.product.DTO.ProductUpdateDTO;
import com.hamza.ecommerce_backend.product.entity.Product;
import com.hamza.ecommerce_backend.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
    public Page<ProductDTO> getAllProducts(@RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "10") int pageSize,
                                           @RequestParam(defaultValue = "price") String sortBy, @RequestParam(defaultValue = "DESC") String direction,
                                           @RequestParam(required = false) String search,
                                           @RequestParam(required = false) BigDecimal minPrice,
                                           @RequestParam(required = false) BigDecimal maxPrice, @RequestParam(required = false) Long categoryId){
        return productService.getAllProducts(pageNumber, pageSize, sortBy, direction, search, minPrice, maxPrice, categoryId);
    }

    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable Long id){
        return productService.getProductById(id);
    }

    @PutMapping("/{id}")
    public ProductDTO updateProduct(@RequestBody @Valid ProductUpdateDTO product, @PathVariable Long id){
        return productService.updateProduct(product, id);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
    }
}
