package com.caio.pdv.entities;

import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemSalePK {

    @Getter
    @ManyToOne
    private Product product;

    @ManyToOne
    private Sale sale;

}
