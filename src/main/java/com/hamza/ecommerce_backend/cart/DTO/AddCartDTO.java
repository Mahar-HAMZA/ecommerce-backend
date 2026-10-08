package com.hamza.ecommerce_backend.cart.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class AddCartDTO {

    @NotEmpty
    @Valid
    private List<AddCartItemDTO> items;

    public List<AddCartItemDTO> getItems() {
        return items;
    }

    public void setItems(List<AddCartItemDTO> items) {
        this.items = items;
    }
}
