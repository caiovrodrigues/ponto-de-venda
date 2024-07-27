package com.caio.pdv.infra;

import com.caio.pdv.entities.*;
import com.caio.pdv.entities.repositories.*;
import com.caio.pdv.infra.security.SecurityConfig;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@Profile(value = {"local"})
public class DatabaseLoaderFake {

    protected final UserRepository userRepository;
    private final SaleRepository saleRepository;
    private final ItemSaleRepository itemSaleRepository;
    private final ProductRepository productRepository;
    protected final RoleRepository roleRepository;
    protected final RolesRepository rolesRepository;
    protected final PasswordEncoder passwordEncoder;

    public DatabaseLoaderFake(UserRepository userRepository, SaleRepository saleRepository, ItemSaleRepository itemSaleRepository, ProductRepository productRepository, RoleRepository roleRepository, RolesRepository rolesRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.saleRepository = saleRepository;
        this.itemSaleRepository = itemSaleRepository;
        this.productRepository = productRepository;
        this.roleRepository = roleRepository;
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void loadData(){

        User user1 = User.builder().name("Caio").email("caio@gmail.com").password(passwordEncoder.encode("1234")).isEnabled(true).build();
        User user2 = User.builder().name("Ana").email("ana@gmail.com").password(passwordEncoder.encode("1234")).isEnabled(false).build();

        userRepository.save(user1);
        userRepository.save(user2);

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

        Role roleAdmin = new Role(null, Role.ADMIN);
        Role roleBasic = new Role(null, Role.BASIC);

        roleRepository.save(roleAdmin);
        roleRepository.save(roleBasic);

        rolesRepository.save(new UserRoles(null, user1, roleAdmin));
        rolesRepository.save(new UserRoles(null, user1, roleBasic));
        rolesRepository.save(new UserRoles(null, user2, roleBasic));
//        rolesRepository.save(new Roles(null, user2, roleBasic));

    }

}
