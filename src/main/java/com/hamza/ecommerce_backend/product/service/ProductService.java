package com.hamza.ecommerce_backend.product.service;
import com.hamza.ecommerce_backend.category.entity.Category;
import com.hamza.ecommerce_backend.category.repository.CategoryRepository;
import com.hamza.ecommerce_backend.product.entity.Product;
import com.hamza.ecommerce_backend.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    public List<Product> getAllProducts(){
        return productRepo.findAll();
    }

    public Product getProductById(Long id){
        Optional<Product> product=productRepo.findById(id);
        if(product.isPresent()){
            return product.get();
        }
        else{
            throw new RuntimeException("Product does not exist.");
        }
    }

    public Product updateProduct(Product product, Long id){
        Optional<Product> product1=productRepo.findById(id);
        Optional<Category> category1=categoryRepo.findById(product.getCategory().getId());
        if(product1.isPresent()){
            if(category1.isPresent()){
                Product existProduct=product1.get();
                existProduct.setCategory(category1.get());
                existProduct.setProductName(product.getProductName());
                existProduct.setDescription(product.getDescription());
                existProduct.setStatus(product.getStatus());
                existProduct.setPrice(product.getPrice());
                existProduct.setStockQuantity(product.getStockQuantity());
                return productRepo.save(existProduct);
            }
            else{
                throw new RuntimeException("category does not exist.");
            }
        }
        else{
            throw new RuntimeException("Product does not exist.");
        }
    }

}
