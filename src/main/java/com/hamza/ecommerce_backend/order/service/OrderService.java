package com.hamza.ecommerce_backend.order.service;

import com.hamza.ecommerce_backend.cart.entity.Cart;
import com.hamza.ecommerce_backend.cart.entity.CartItem;
import com.hamza.ecommerce_backend.cart.exception.CartNotFoundException;
import com.hamza.ecommerce_backend.cart.exception.EmptyCartException;
import com.hamza.ecommerce_backend.cart.repository.CartRepository;
import com.hamza.ecommerce_backend.order.DTO.*;
import com.hamza.ecommerce_backend.order.entity.*;
import com.hamza.ecommerce_backend.order.exception.DuplicateProductException;
import com.hamza.ecommerce_backend.order.exception.InsufficientStockException;
import com.hamza.ecommerce_backend.order.exception.OrderAccessDeniedException;
import com.hamza.ecommerce_backend.order.exception.OrderNotFoundException;
import com.hamza.ecommerce_backend.order.mapper.OrderMapper;
import com.hamza.ecommerce_backend.order.repository.*;
import com.hamza.ecommerce_backend.product.entity.Product;
import com.hamza.ecommerce_backend.product.entity.ProductStatus;
import com.hamza.ecommerce_backend.product.exception.InactiveProductException;
import com.hamza.ecommerce_backend.product.exception.ProductNotFoundException;
import com.hamza.ecommerce_backend.product.repository.ProductRepository;
import com.hamza.ecommerce_backend.user.entity.Role;
import com.hamza.ecommerce_backend.user.entity.User;
import com.hamza.ecommerce_backend.user.exception.UserNotFoundException;
import com.hamza.ecommerce_backend.user.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class OrderService {

    private final OrderMapper mapper;
    private final OrderItemRepository OrderItemRepo;
    private final OrderRepository OrderRepo;
    private final ProductRepository productRepo;
    private final UserRepository userRepo;
    private final CartRepository cartRepo;

    public OrderService(OrderMapper mapper, OrderItemRepository OrderItemRepo, OrderRepository OrderRepo,
                        ProductRepository productRepo, UserRepository userRepo, CartRepository cartRepo) {
        this.mapper=mapper;
        this.OrderItemRepo=OrderItemRepo;
        this.OrderRepo=OrderRepo;
        this.productRepo=productRepo;
        this.userRepo=userRepo;
        this.cartRepo=cartRepo;
    }

    @Transactional
    public OrderDTO makeOrder(OrderCreateDTO dto){
        List<OrderItem> orderItems=new ArrayList<>();
        Set<Long> productIds=new HashSet<>();
        Order order=mapper.toEntity(dto);
        BigDecimal totalAmount=BigDecimal.ZERO;
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        for(OrderItemCreateDTO items : dto.getItems()){
            Optional<Product> product=productRepo.findByIdWithLock(items.getProductId());
            if(product.isPresent()) {
                Product product1 = product.get();
                if(!productIds.add(product1.getId())) {
                    throw new DuplicateProductException("Duplicate product IDs are not allowed in an order.");
                }
                if(product1.getStatus() == ProductStatus.INACTIVE){
                    throw new InactiveProductException("Inactive products cannot be ordered.");
                }
                if (product1.getStockQuantity() >= items.getQuantity()) {
                    OrderItem item = new OrderItem();
                    item.setProduct(product1);
                    item.setQuantity(items.getQuantity());
                    item.setUnitPrice(product1.getPrice());
                    item.setOrder(order);
                    item.setSubtotal(product1.getPrice().multiply(BigDecimal.valueOf(items.getQuantity())));
                    orderItems.add(item);
                    totalAmount = totalAmount.add(item.getSubtotal());
                    product1.setStockQuantity(product1.getStockQuantity() - items.getQuantity());
                } else {
                    throw new InsufficientStockException("Insufficient stock available for the requested quantity");
                }

            }
            else{
                throw new ProductNotFoundException("Product does not exist");
            }
        }
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User does not exist"));
        order.setUser(user);
        order.setOrderItems(orderItems);
        order.setTotalAmount(totalAmount);
        Order savedOrder = OrderRepo.save(order);
        return mapper.toDTO(savedOrder);
    }

    @Transactional
    public OrderDTO checkoutCart() {

        // 1. Get authenticated user's email
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        // 2. Fetch user's cart
        Cart cart = cartRepo.findByUser_Email(email)
                .orElseThrow(() ->
                        new CartNotFoundException("Cart does not exist"));

        // 3. Validate cart is not empty
        if (cart.getCartItems() == null || cart.getCartItems().isEmpty()) {
            throw new EmptyCartException("Cannot checkout an empty cart");
        }

        // 4. Convert CartItems into OrderItemCreateDTO
        List<OrderItemCreateDTO> orderItems = new ArrayList<>();

        for (CartItem cartItem : cart.getCartItems()) {

            OrderItemCreateDTO itemDTO = new OrderItemCreateDTO();

            itemDTO.setProductId(cartItem.getProduct().getId());
            itemDTO.setQuantity(cartItem.getQuantity());

            orderItems.add(itemDTO);
        }

        // 5. Create OrderCreateDTO
        OrderCreateDTO orderDTO = new OrderCreateDTO();
        orderDTO.setItems(orderItems);

        // 6. Reuse existing order creation logic
        OrderDTO createdOrder = makeOrder(orderDTO);

        // 7. Clear cart after successful order creation
        cart.getCartItems().clear();

        return createdOrder;
    }

    public OrderDTO getOrderById(Long id){
        Optional<Order> order=OrderRepo.findById(id);
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User does not exist"));
        if(!order.isPresent()){
            throw new OrderNotFoundException("Order does not exist");
        }
        Order getOrder=order.get();
        if(user.getRole() == Role.ADMIN){
            OrderDTO dto=mapper.toDTO(getOrder);
            return dto;
        }
        if(getOrder.getUser().getEmail().equals(user.getEmail())) {
            OrderDTO dto = mapper.toDTO(getOrder);
            return dto;
        }
        throw new OrderAccessDeniedException("You are not allowed to access this order");
    }

    public List<OrderDTO> getAllOrders(){
        List<OrderDTO> dto=new ArrayList<>();
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User loginUser = userRepo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User does not exist"));
        if(loginUser.getRole() == Role.CUSTOMER){
            List<Order> customerOrder=OrderRepo.findByUser_EmailOrderByCreatedAtDesc(email);
            for(Order order : customerOrder){
                dto.add(mapper.toDTO(order));
            }
            return dto;
        }
        List<Order> OrderList = OrderRepo.findAllByOrderByCreatedAtDesc();
        for(Order order: OrderList){
            dto.add(mapper.toDTO(order));
        }
        return dto;
    }

    public OrderDTO updateOrderStatus(Long id, OrderStatusUpdateDTO dto){
        Optional<Order> order=OrderRepo.findById(id);
        if(!order.isPresent()){
            throw new OrderNotFoundException("Order does not exist");
        }
        Order existOrder=order.get();
        existOrder.setStatus(dto.getStatus());
        Order updateOrder=OrderRepo.save(existOrder);
        return mapper.toDTO(updateOrder);
    }

}
