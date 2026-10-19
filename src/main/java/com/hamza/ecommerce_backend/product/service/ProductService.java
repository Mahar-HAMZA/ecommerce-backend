package com.hamza.ecommerce_backend.product.service;
import com.hamza.ecommerce_backend.category.entity.Category;
import com.hamza.ecommerce_backend.category.exception.CategoryNotFoundException;
import com.hamza.ecommerce_backend.category.repository.CategoryRepository;
import com.hamza.ecommerce_backend.product.DTO.ProductCreateDTO;
import com.hamza.ecommerce_backend.product.DTO.ProductDTO;
import com.hamza.ecommerce_backend.product.DTO.ProductUpdateDTO;
import com.hamza.ecommerce_backend.product.entity.Product;
import com.hamza.ecommerce_backend.product.exception.ProductAlreadyExistsException;
import com.hamza.ecommerce_backend.product.exception.ProductNotFoundException;
import com.hamza.ecommerce_backend.product.mapper.ProductMapper;
import com.hamza.ecommerce_backend.product.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import java.util.Optional;
import java.util.Set;

@Service
public class ProductService {

    private ProductRepository productRepo;
    private CategoryRepository categoryRepo;

    private ProductMapper mapper;

    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of(
            "price",
            "productName",
            "createdAt",
            "id"
    );

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

    public Page<ProductDTO> getAllProducts(int pageNumber, int pageSize, String sortBy, String direction, String search, BigDecimal minPrice, BigDecimal maxPrice, Long categoryId) {
        if (minPrice != null && maxPrice != null && minPrice.compareTo(maxPrice) > 0) {
            throw new IllegalArgumentException("Enter valid price range");
        }
        direction = direction.toUpperCase();
            if (pageNumber < 0) {
                throw new IllegalArgumentException("Page number cannot be negative");
            }
            if (pageSize < 1 || pageSize > 100) {
                throw new IllegalArgumentException("Page size must be between 1 and 100");
            }
            if (!direction.equals("ASC") && !direction.equals("DESC")) {
                throw new IllegalArgumentException("Direction Not Allowed: " + direction);
            }
            if (!ALLOWED_SORT_FIELDS.contains(sortBy)) {
                throw new IllegalArgumentException("Argument Not Allowed: " + sortBy);
            }
            if(categoryId != null) {
                if (!productRepo.existsBycategory_Id(categoryId)) {
                    throw new CategoryNotFoundException("Category does not exist");
                }
            }
            PageRequest pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.valueOf(direction), sortBy));
        return mapper.allProductsToDTO(productRepo.searchByProductName(search, minPrice, maxPrice, categoryId, pageable));
    }

    public ProductDTO getProductById(Long id){
        Optional<Product> product=productRepo.findById(id);
        if(product.isPresent()){
            ProductDTO productDto=mapper.toDTO(product.get());
            return productDto;
       //     return product.get();
        }
        else{
            throw new ProductNotFoundException("Product does not exist.");
        }
    }

    public ProductDTO updateProduct(ProductUpdateDTO updateDTO, Long id) {
        Optional<Product> product=productRepo.findById(id);
        if(!product.isPresent()){
            throw new ProductNotFoundException("Product does not exist");
        }
        if(updateDTO.getProductName() != null){
            if(productRepo.existsByProductNameAndIdNot(updateDTO.getProductName(), id)){
             throw new ProductAlreadyExistsException("Product already exists");
            }
        }
        Product existingProduct=product.get();
        if(updateDTO.getCategoryId() != null){
            Optional<Category> cate=categoryRepo.findById(updateDTO.getCategoryId());
            if(!cate.isPresent()){
                throw new CategoryNotFoundException("Category does not exist");
            }
            existingProduct.setCategory(cate.get());
        }

        Product updateProduct=mapper.updateProduct(existingProduct, updateDTO);
        productRepo.save(updateProduct);
        ProductDTO dto=mapper.toDTO(updateProduct);
        return dto;
    }

    public void deleteProduct(Long id){
        Optional<Product> product=productRepo.findById(id);
        if(product.isPresent()){
            productRepo.deleteById(id);
            return;
        }
        else{
            throw new ProductNotFoundException("Product does not exist.");
        }
    }
}
