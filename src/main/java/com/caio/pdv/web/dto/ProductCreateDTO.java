package com.caio.pdv.web.dto;

import com.caio.pdv.entities.Product;

import java.math.BigDecimal;

public record ProductCreateDTO(String name, String description, BigDecimal price, Integer quantity) {

    public Product toProduct(){
        return new Product(null, name, description, price, quantity);
    }
}
