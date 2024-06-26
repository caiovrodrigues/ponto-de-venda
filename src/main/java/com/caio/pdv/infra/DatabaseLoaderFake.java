package com.caio.pdv.infra;

import com.caio.pdv.entities.*;
import com.caio.pdv.entities.repositories.ItemSaleRepository;
import com.caio.pdv.entities.repositories.ProductRepository;
import com.caio.pdv.entities.repositories.SaleRepository;
import com.caio.pdv.entities.repositories.UserRepository;
import com.caio.pdv.infra.security.SecurityConfig;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;

@Configuration
public class DatabaseLoaderFake {

    private final UserRepository userRepository;
    private final SaleRepository saleRepository;
    private final ItemSaleRepository itemSaleRepository;
    private final ProductRepository productRepository;

    public DatabaseLoaderFake(UserRepository userRepository, SaleRepository saleRepository, ItemSaleRepository itemSaleRepository, ProductRepository productRepository){
        this.userRepository = userRepository;
        this.saleRepository = saleRepository;
        this.itemSaleRepository = itemSaleRepository;
        this.productRepository = productRepository;
    }

    @PostConstruct
    public void loadData(){

        User user1 = User.builder().name("Caio").email("caio@gmail.com").password(SecurityConfig.passwordEncoder().encode("1234")).isEnabled(true).build();
        User user2 = User.builder().name("Ana").email("ana@gmail.com").password(SecurityConfig.passwordEncoder().encode("1234")).isEnabled(false).build();
//        User user3 = User.builder().name("Lara").email("lara@gmail.com").isEnabled(true).build();
//        User user4 = User.builder().name("Teste").email("Teste@gmail.com").isEnabled(true).build();
//        User user5 = User.builder().name("Mky").email("Mky@gmail.com").isEnabled(true).build();
//        User user6 = User.builder().name("Zue").email("Zue@gmail.com").isEnabled(true).build();
//        User user7 = User.builder().name("Kye").email("Kye@gmail.com").isEnabled(true).build();
//        User user8 = User.builder().name("Lew").email("Lew@gmail.com").isEnabled(true).build();
//        User user9 = User.builder().name("Moz").email("Moz@gmail.com").isEnabled(true).build();

        userRepository.save(user1);
        userRepository.save(user2);
//        userRepository.save(user3);
//        userRepository.save(user4);
//        userRepository.save(user5);
//        userRepository.save(user6);
//        userRepository.save(user7);
//        userRepository.save(user8);
//        userRepository.save(user9);

        Sale sale1 = Sale.builder().user(user1).build();
        Sale sale2 = Sale.builder().user(user2).build();

        saleRepository.saveAll(List.of(sale1, sale2));

        Product p1 = new Product(null, "Prato", "Descricao", BigDecimal.valueOf(50.0), 20);
        Product p2 = new Product(null, "Headset", "Descricao", BigDecimal.valueOf(100.0), 20);
        Product p3 = new Product(null, "Monitor LG 144hz", "Descricao", BigDecimal.valueOf(200.0), 20);

        ItemSale item1 = ItemSale.builder().product(p1).sale(sale1).quantity(1).build();
        ItemSale item2 = ItemSale.builder().product(p2).sale(sale1).quantity(1).build();
        ItemSale item3 = ItemSale.builder().product(p1).sale(sale2).quantity(2).build();

        productRepository.saveAll(List.of(p1, p2, p3));
        itemSaleRepository.saveAll(List.of(item1, item2, item3));

    }

}
