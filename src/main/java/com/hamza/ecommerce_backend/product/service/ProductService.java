package com.hamza.ecommerce_backend.product.service;
import com.hamza.ecommerce_backend.category.entity.Category;
import com.hamza.ecommerce_backend.category.repository.CategoryRepository;
import com.hamza.ecommerce_backend.product.entity.Product;
import com.hamza.ecommerce_backend.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;

import java.util.Optional;

@Service
public class ProductService {

    private ProductRepository productRepo;
    private CategoryRepository categoryRepo;

    public ProductService(ProductRepository repo1, CategoryRepository repo2){
        productRepo = repo1;
        categoryRepo = repo2;
    }

    public Product createProduct(Product product){
        boolean isTrue=productRepo.existsByProductName(product.getProductName());
        Optional<Category> cate=categoryRepo.findById(product.getCategory().getId());
        if(!isTrue){
            if(cate.isPresent()){
                product.setCategory(cate.get());
                return productRepo.save(product);
            }
            else{
                throw new RuntimeException("Category does not exist");
            }
        }
        else{
            throw new RuntimeException("Product already exists");
        }
    }



}
