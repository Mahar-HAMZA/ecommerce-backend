package com.hamza.ecommerce_backend.exception;

import com.hamza.ecommerce_backend.category.exception.CategoryAlreadyExistsException;
import com.hamza.ecommerce_backend.category.exception.CategoryDeletionNotAllowedException;
import com.hamza.ecommerce_backend.category.exception.CategoryExceptionResponse;
import com.hamza.ecommerce_backend.category.exception.CategoryNotFoundException;
import com.hamza.ecommerce_backend.product.exception.ProductAlreadyExistsException;
import com.hamza.ecommerce_backend.product.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(CategoryAlreadyExistsException.class)
    public ResponseEntity<CategoryExceptionResponse> handle(CategoryAlreadyExistsException ex) {
        //return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        CategoryExceptionResponse response=new CategoryExceptionResponse(409, ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<CategoryExceptionResponse> notFound(CategoryNotFoundException ex){
        CategoryExceptionResponse response=new CategoryExceptionResponse(404, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CategoryExceptionResponse> generalException(Exception ex){
        CategoryExceptionResponse response=new CategoryExceptionResponse(500, ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CategoryExceptionResponse> handleValidationException(MethodArgumentNotValidException ex){
        CategoryExceptionResponse response=new CategoryExceptionResponse(400, ex.getBindingResult().getFieldError().getDefaultMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(CategoryDeletionNotAllowedException.class)
    public ResponseEntity<CategoryExceptionResponse> handleDeletionException(CategoryDeletionNotAllowedException ex){
        CategoryExceptionResponse response=new CategoryExceptionResponse(409, ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<CategoryExceptionResponse> handleProductNotFound(ProductNotFoundException ex){
        CategoryExceptionResponse response =
                new CategoryExceptionResponse(404, ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(ProductAlreadyExistsException.class)
    public ResponseEntity<CategoryExceptionResponse> handleProductAlreadyExists(ProductAlreadyExistsException ex){
        CategoryExceptionResponse response =
                new CategoryExceptionResponse(409, ex.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }


}
