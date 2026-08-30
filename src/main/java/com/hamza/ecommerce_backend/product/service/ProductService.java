package com.hamza.ecommerce_backend.product.service;
import com.hamza.ecommerce_backend.category.entity.Category;
import com.hamza.ecommerce_backend.category.repository.CategoryRepository;
import com.hamza.ecommerce_backend.product.DTO.ProductCreateDTO;
import com.hamza.ecommerce_backend.product.DTO.ProductDTO;
import com.hamza.ecommerce_backend.product.entity.Product;
import com.hamza.ecommerce_backend.product.mapper.ProductMapper;
import com.hamza.ecommerce_backend.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

@Service
public class ProductService {

    private ProductRepository productRepo;
    private CategoryRepository categoryRepo;

    private ProductMapper mapper;

    public ProductService(ProductRepository productRepo, CategoryRepository categoryRepo, ProductMapper mapper){
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
        this.mapper=mapper;
    }

    public ProductDTO createProduct(ProductCreateDTO dto){
        boolean productExists=productRepo.existsByProductName(dto.getProductName());
        Optional<Category> cate=categoryRepo.findById(dto.getCategory_Id());
        if(!productExists){
            if(cate.isPresent()){
                Product product=mapper.toEntity(dto);
                product.setCategory(cate.get());
                Product savedProduct=productRepo.save(product);
                ProductDTO productDTO=mapper.toDTO(savedProduct);
                return productDTO;
//                return productRepo.save(product);
            }
            else{
                throw new RuntimeException("Category does not exist");
            }
        }
        else{
            throw new RuntimeException("Product already exists");
        }
    }

    public List<ProductDTO> getAllProducts(){
        List<ProductDTO> dtos=mapper.allProductsToDTO(productRepo.findAll());
        return dtos;
    }

    public ProductDTO getProductById(Long id){
        Optional<Product> product=productRepo.findById(id);
        if(product.isPresent()){
            ProductDTO productDto=mapper.toDTO(product.get());
            return productDto;
       //     return product.get();
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

    public void deleteProduct(Long id){
        Optional<Product> product=productRepo.findById(id);
        if(product.isPresent()){
            productRepo.deleteById(id);
            return;
        }
        else{
            throw new RuntimeException("Product does not exist.");
        }
    }
}
