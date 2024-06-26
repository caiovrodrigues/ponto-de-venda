package com.caio.pdv.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL)
    private List<ItemSale> items;

    public String getTotal(){
        BigDecimal total = items.stream().map(ItemSale::getSubTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
        return NumberFormat.getCurrencyInstance(Locale.CHINA).format(total);
    }

}
