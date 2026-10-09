package com.hamza.ecommerce_backend.cart.service;

import com.hamza.ecommerce_backend.cart.DTO.AddCartDTO;
import com.hamza.ecommerce_backend.cart.DTO.AddCartItemDTO;
import com.hamza.ecommerce_backend.cart.DTO.CartResponseDTO;
import com.hamza.ecommerce_backend.cart.DTO.UpdateCartItemDTO;
import com.hamza.ecommerce_backend.cart.entity.Cart;
import com.hamza.ecommerce_backend.cart.entity.CartItem;
import com.hamza.ecommerce_backend.cart.exception.CartItemNotFoundException;
import com.hamza.ecommerce_backend.cart.exception.CartNotFoundException;
import com.hamza.ecommerce_backend.cart.exception.UnauthorizedCartAccessException;
import com.hamza.ecommerce_backend.cart.mapper.CartMapper;
import com.hamza.ecommerce_backend.cart.repository.CartItemRepository;
import com.hamza.ecommerce_backend.cart.repository.CartRepository;
import com.hamza.ecommerce_backend.order.exception.InsufficientStockException;
import com.hamza.ecommerce_backend.product.entity.Product;
import com.hamza.ecommerce_backend.product.exception.ProductNotFoundException;
import com.hamza.ecommerce_backend.product.repository.ProductRepository;
import com.hamza.ecommerce_backend.user.entity.User;
import com.hamza.ecommerce_backend.user.exception.UserNotFoundException;
import com.hamza.ecommerce_backend.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CartService {

    private final CartRepository cartRepo;
    private final CartItemRepository cartItemRepo;
    private final ProductRepository productRepo;
    private final UserRepository userRepo;
    private final CartMapper cartMapper;

    public CartService(CartRepository cartRepo,CartItemRepository cartItemRepo, ProductRepository productRepo, UserRepository userRepo, CartMapper cartMapper){
        this.cartRepo = cartRepo;
        this.cartItemRepo = cartItemRepo;
        this.productRepo = productRepo;
        this.userRepo = userRepo;
        this.cartMapper = cartMapper;
    }

    @Transactional
    public CartResponseDTO addToCart(AddCartDTO dto) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Cart> cart = cartRepo.findByUser_Email(email);
        Cart existCart;

        if (cart.isPresent()) {
            existCart = cart.get();
        }
        else {
            Optional<User> user = userRepo.findByEmail(email);

            if (!user.isPresent()) {
                throw new UserNotFoundException("User does not exist");
            }
            existCart = new Cart();
            existCart.setUser(user.get());
            existCart.setCartItems(new ArrayList<>());

            existCart = cartRepo.save(existCart);
        }

        for (AddCartItemDTO dtoItem : dto.getItems()) {
            Optional<Product> product=productRepo.findById(dtoItem.getProductId());

            if (!product.isPresent()) {
                throw new ProductNotFoundException("Product does not exist");
            }
            Product existProduct = product.get();

            if (dtoItem.getQuantity() > existProduct.getStockQuantity()) {
                throw new InsufficientStockException("Insufficient stock available for the requested quantity");
            }

            Optional<CartItem> cartItem=cartItemRepo.findByCart_IdAndProduct_Id(existCart.getId(), existProduct.getId());

            if (cartItem.isPresent()) {
                CartItem existCartItem = cartItem.get();
                int newQuantity=existCartItem.getQuantity() + dtoItem.getQuantity();

                if (newQuantity > existProduct.getStockQuantity()) {
                    throw new InsufficientStockException("Insufficient stock available for the requested quantity");
                }
                existCartItem.setQuantity(newQuantity);
            }
            else {
                CartItem newCartItem = new CartItem();

                newCartItem.setCart(existCart);
                newCartItem.setProduct(existProduct);
                newCartItem.setQuantity(dtoItem.getQuantity());

                existCart.getCartItems().add(newCartItem);
            }
        }
        Cart savedCart = cartRepo.save(existCart);
        return cartMapper.toDTO(savedCart);
    }

    public CartResponseDTO getMyCart() {

        String email=SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Cart> cart = cartRepo.findByUser_Email(email);

        if (!cart.isPresent()) {
            throw new CartNotFoundException("Cart does not exist");
        }
        return cartMapper.toDTO(cart.get());
    }

    @Transactional
    public CartResponseDTO updateCartItem(Long cartItemId, UpdateCartItemDTO dto) {
        String email=SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<CartItem> cartItem=cartItemRepo.findById(cartItemId);
        Optional<Cart> cart = cartRepo.findByUser_Email(email);

        if (!cartItem.isPresent()) {
            throw new CartItemNotFoundException("Cart item does not exist");
        }
        if(!cart.isPresent()){
            throw new CartNotFoundException("Cart does not exist");
        }

        CartItem existCartItem = cartItem.get();
        if(!existCartItem.getCart().getId().equals(cart.get().getId())){
            throw new UnauthorizedCartAccessException("You are not authorized to access this cart item");
        }

        Product product = existCartItem.getProduct();

        if (dto.getQuantity() > product.getStockQuantity()) {
            throw new InsufficientStockException("Insufficient stock available for the requested quantity");
        }

        existCartItem.setQuantity(dto.getQuantity());
        CartItem updatedCartItem = cartItemRepo.save(existCartItem);
        return cartMapper.toDTO(updatedCartItem.getCart());
    }

    @Transactional
    public CartResponseDTO removeCartItem(Long cartItemId) {
        String email=SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<CartItem> cartItem=cartItemRepo.findById(cartItemId);
        Optional<Cart> cart1=cartRepo.findByUser_Email(email);
        if (!cart1.isPresent()) {
            throw new CartNotFoundException("Cart does not exist");
        }
        if (!cartItem.isPresent()) {
            throw new CartItemNotFoundException("Cart item does not exist");
        }

        CartItem existCartItem = cartItem.get();
        Cart cart = existCartItem.getCart();
        if(!cart.getId().equals(cart1.get().getId())){
            throw new UnauthorizedCartAccessException("You are not authorized to access this cart item");
        }
        cart.getCartItems().remove(existCartItem);
        cartItemRepo.delete(existCartItem);

        return cartMapper.toDTO(cart);
    }

    @Transactional
    public CartResponseDTO clearCart() {

        String email=SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Cart> cart = cartRepo.findByUser_Email(email);

        if (!cart.isPresent()) {
            throw new CartNotFoundException("Cart does not exist");
        }

        Cart existCart = cart.get();
        existCart.getCartItems().clear();
        Cart clearedCart = cartRepo.save(existCart);

        return cartMapper.toDTO(clearedCart);
    }

}
