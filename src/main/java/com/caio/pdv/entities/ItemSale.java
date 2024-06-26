package com.caio.pdv.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class ItemSale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Product product;

    @JsonIgnore
    @ManyToOne
    private Sale sale;

    private Integer quantity;

    public BigDecimal getSubTotal(){
        return product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }

}
