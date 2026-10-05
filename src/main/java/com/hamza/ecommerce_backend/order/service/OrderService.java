package com.hamza.ecommerce_backend.order.service;

import com.hamza.ecommerce_backend.order.DTO.*;
import com.hamza.ecommerce_backend.order.entity.*;
import com.hamza.ecommerce_backend.order.exception.InsufficientStockException;
import com.hamza.ecommerce_backend.order.exception.OrderAccessDeniedException;
import com.hamza.ecommerce_backend.order.exception.OrderNotFoundException;
import com.hamza.ecommerce_backend.order.mapper.OrderMapper;
import com.hamza.ecommerce_backend.order.repository.*;
import com.hamza.ecommerce_backend.product.entity.Product;
import com.hamza.ecommerce_backend.product.exception.ProductNotFoundException;
import com.hamza.ecommerce_backend.product.repository.ProductRepository;
import com.hamza.ecommerce_backend.user.entity.Role;
import com.hamza.ecommerce_backend.user.entity.User;
import com.hamza.ecommerce_backend.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderMapper mapper;
    private final OrderItemRepository OrderItemRepo;
    private final OrderRepository OrderRepo;
    private final ProductRepository productRepo;
    private final UserRepository userRepo;

    public OrderService(OrderMapper mapper, OrderItemRepository OrderItemRepo, OrderRepository OrderRepo,
                        ProductRepository productRepo, UserRepository userRepo) {
        this.mapper=mapper;
        this.OrderItemRepo=OrderItemRepo;
        this.OrderRepo=OrderRepo;
        this.productRepo=productRepo;
        this.userRepo=userRepo;
    }

    @Transactional
    public OrderDTO makeOrder(OrderCreateDTO dto){
        List<OrderItem> orderItems=new ArrayList<>();
        Order order=mapper.toEntity(dto);
        BigDecimal totalAmount=BigDecimal.ZERO;
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        for(OrderItemCreateDTO items : dto.getItems()){
            Optional<Product> product=productRepo.findById(items.getProductId());
            if(product.isPresent()){
                Product product1=product.get();
                if(product1.getStockQuantity() >= items.getQuantity()){
                    OrderItem item=new OrderItem();
                    item.setProduct(product1);
                    item.setQuantity(items.getQuantity());
                    item.setUnitPrice(product1.getPrice());
                    item.setOrder(order);
                    item.setSubtotal(product1.getPrice().multiply(BigDecimal.valueOf(items.getQuantity())));
                    orderItems.add(item);
                    totalAmount = totalAmount.add(item.getSubtotal());
                    product1.setStockQuantity(product1.getStockQuantity() - items.getQuantity());
                }
                else{
                    throw new InsufficientStockException("Insufficient stock available for the requested quantity");
                }
            }
            else{
                throw new ProductNotFoundException("Product does not exist");
            }
        }
        Optional<User> loginUser=userRepo.findByEmail(email);
        User user=loginUser.get();
        order.setUser(user);
        order.setOrderItems(orderItems);
        order.setTotalAmount(totalAmount);
        Order savedOrder = OrderRepo.save(order);
        return mapper.toDTO(savedOrder);
    }

    public OrderDTO getOrderById(Long id){
        Optional<Order> order=OrderRepo.findById(id);
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> loginUser=userRepo.findByEmail(email);
        User user=loginUser.get();
        if(!order.isPresent()){
            throw new ProductNotFoundException("Order does not exist");
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
        Optional<User> user=userRepo.findByEmail(email);
        User loginUser=user.get();
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
