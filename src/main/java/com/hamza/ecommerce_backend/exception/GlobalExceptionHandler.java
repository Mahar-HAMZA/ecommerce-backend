package com.hamza.ecommerce_backend.exception;

import com.hamza.ecommerce_backend.cart.exception.CartItemNotFoundException;
import com.hamza.ecommerce_backend.cart.exception.CartNotFoundException;
import com.hamza.ecommerce_backend.cart.exception.UnauthorizedCartAccessException;
import com.hamza.ecommerce_backend.category.exception.CategoryAlreadyExistsException;
import com.hamza.ecommerce_backend.category.exception.CategoryDeletionNotAllowedException;
import com.hamza.ecommerce_backend.category.exception.CategoryExceptionResponse;
import com.hamza.ecommerce_backend.category.exception.CategoryNotFoundException;
import com.hamza.ecommerce_backend.order.exception.InsufficientStockException;
import com.hamza.ecommerce_backend.order.exception.OrderAccessDeniedException;
import com.hamza.ecommerce_backend.order.exception.OrderNotFoundException;
import com.hamza.ecommerce_backend.product.exception.ProductAlreadyExistsException;
import com.hamza.ecommerce_backend.product.exception.ProductNotFoundException;
import com.hamza.ecommerce_backend.user.exception.EmailAlreadyExistsException;
import com.hamza.ecommerce_backend.user.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.stream.Collectors;

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

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<CategoryExceptionResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String message = "Invalid value for parameter: " + ex.getName();

        CategoryExceptionResponse response=new CategoryExceptionResponse(400, message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CategoryExceptionResponse> generalException(Exception ex){
        CategoryExceptionResponse response=new CategoryExceptionResponse(500, "An unexpected server error occurred.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<CategoryExceptionResponse> HttpMessageHandler(HttpMessageNotReadableException ex){
        CategoryExceptionResponse response=new CategoryExceptionResponse(400, "Invalid request body. Please check your JSON and field values.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CategoryExceptionResponse> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getDefaultMessage()).collect(Collectors.joining(", "));
        if (errorMessage.isBlank()) {
            errorMessage = "Invalid input. Please check your request.";
        }
        CategoryExceptionResponse response=new CategoryExceptionResponse(400, errorMessage);

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

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<CategoryExceptionResponse> handleInsufficientStock(InsufficientStockException ex){
        CategoryExceptionResponse response=new CategoryExceptionResponse(409, ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<CategoryExceptionResponse> handleOrderNotFound(OrderNotFoundException ex){
        CategoryExceptionResponse response =
                new CategoryExceptionResponse(404, ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<CategoryExceptionResponse> dublicateEmailHandler(EmailAlreadyExistsException ex){
        CategoryExceptionResponse response=new  CategoryExceptionResponse(409, ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<CategoryExceptionResponse> handleBadCredentialsException(BadCredentialsException ex) {
        CategoryExceptionResponse response=new  CategoryExceptionResponse(401, ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(OrderAccessDeniedException.class)
    public ResponseEntity<CategoryExceptionResponse> handleOrderAccessDenied(OrderAccessDeniedException ex){
        CategoryExceptionResponse response = new CategoryExceptionResponse(403, ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<CategoryExceptionResponse> handleCartNotFound(CartNotFoundException ex){
        CategoryExceptionResponse response=new CategoryExceptionResponse(404, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(CartItemNotFoundException.class)
    public ResponseEntity<CategoryExceptionResponse> handleCartItemNotFound(CartItemNotFoundException ex){
        CategoryExceptionResponse response=new CategoryExceptionResponse(404, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(UnauthorizedCartAccessException.class)
    public ResponseEntity<CategoryExceptionResponse> handleUnauthorizedCartAccecss(UnauthorizedCartAccessException ex){
        CategoryExceptionResponse response=new CategoryExceptionResponse(403, ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<CategoryExceptionResponse> handleUserNotFound(UserNotFoundException ex){
        CategoryExceptionResponse response=new CategoryExceptionResponse(404, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CategoryExceptionResponse> handleIllegalArgument(IllegalArgumentException ex){
        CategoryExceptionResponse response=new CategoryExceptionResponse(400, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


}
