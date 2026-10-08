package com.hamza.ecommerce_backend.cart.mapper;

import com.hamza.ecommerce_backend.cart.DTO.CartItemResponseDTO;
import com.hamza.ecommerce_backend.cart.DTO.CartResponseDTO;
import com.hamza.ecommerce_backend.cart.entity.Cart;
import com.hamza.ecommerce_backend.cart.entity.CartItem;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CartMapper {

    public CartResponseDTO toDTO(Cart cart) {

        CartResponseDTO responseDTO = new CartResponseDTO();

        responseDTO.setCartId(cart.getId());
        responseDTO.setUserId(cart.getUser().getId());

        List<CartItemResponseDTO> items = new ArrayList<>();

        for (CartItem cartItem : cart.getCartItems()) {

            CartItemResponseDTO itemDTO = new CartItemResponseDTO();

            itemDTO.setCartItemId(cartItem.getId());
            itemDTO.setProductId(cartItem.getProduct().getId());
            itemDTO.setProductName(cartItem.getProduct().getProductName());
            itemDTO.setQuantity(cartItem.getQuantity());

            items.add(itemDTO);
        }

        responseDTO.setItems(items);

        return responseDTO;
    }
}