package com.hamza.ecommerce_backend.product.service;
import com.hamza.ecommerce_backend.category.entity.Category;
import com.hamza.ecommerce_backend.category.exception.CategoryNotFoundException;
import com.hamza.ecommerce_backend.category.repository.CategoryRepository;
import com.hamza.ecommerce_backend.product.DTO.ProductCreateDTO;
import com.hamza.ecommerce_backend.product.DTO.ProductDTO;
import com.hamza.ecommerce_backend.product.DTO.ProductUpdateDTO;
import com.hamza.ecommerce_backend.product.entity.Product;
import com.hamza.ecommerce_backend.product.exception.ProductAlreadyExistsException;
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
        if(dto.getCategory_Id() == null){
            throw new CategoryNotFoundException("Category is necessary to create Product");
        }
        Optional<Category> cate=categoryRepo.findById(dto.getCategory_Id());
        boolean productExists=productRepo.existsByProductName(dto.getProductName());
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
                throw new CategoryNotFoundException("Category does not exist");
            }
        }
        else{
            throw new ProductAlreadyExistsException("Product already exists");
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

    public ProductDTO updateProduct(ProductUpdateDTO updateDTO, Long id) {
        Optional<Product> product = productRepo.findById(id);
        if (!product.isPresent()) {
            throw new RuntimeException("Product does not exist");
        }

        Product existingProduct = product.get();
        if (updateDTO.getProductName() != null && productRepo.existsByProductNameAndIdNot(updateDTO.getProductName(), id)) {
            throw new RuntimeException("Product already exists");
        }
        Product updatedProduct = mapper.updateProduct(existingProduct, updateDTO);
        if (updateDTO.getCategoryId() != null){
            Optional<Category> category=categoryRepo.findById(updateDTO.getCategoryId());
            if (!category.isPresent()){
                throw new RuntimeException("Category does not exist");
            }
            updatedProduct.setCategory(category.get());
        }
        Product savedProduct=productRepo.save(updatedProduct);

        ProductDTO dto=mapper.toDTO(savedProduct);
        return dto;
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
