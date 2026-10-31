package com.hamza.ecommerce_backend.cart.entity;

import com.hamza.ecommerce_backend.product.entity.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "cart_items", uniqueConstraints = {@UniqueConstraint(
        columnNames = {"cart_id", "product_id"})
        }
)
public class CartItem {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) @Min(1)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "cart_id",  nullable = false)
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id",  nullable = false)
    private Product product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
