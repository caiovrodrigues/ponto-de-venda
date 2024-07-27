package com.caio.pdv.infra;

import com.caio.pdv.entities.Role;
import com.caio.pdv.entities.User;
import com.caio.pdv.entities.UserRoles;
import com.caio.pdv.entities.repositories.*;
import com.caio.pdv.infra.security.SecurityConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;

@Slf4j
@Configuration
@Profile(value = {"prod"})
public class DataBaseLoader extends DatabaseLoaderFake{

    public DataBaseLoader(UserRepository userRepository, SaleRepository saleRepository, ItemSaleRepository itemSaleRepository, ProductRepository productRepository, RoleRepository roleRepository, RolesRepository rolesRepository, PasswordEncoder passwordEncoder) {
        super(userRepository, saleRepository, itemSaleRepository, productRepository, roleRepository, rolesRepository, passwordEncoder);
    }

    @Override
    public void loadData() {

        User user = User.builder().name("Caio").email("caio@gmail.com").password(passwordEncoder.encode("1234")).isEnabled(true).build();

        if(Objects.nonNull(userRepository.findUserByEmail(user.getEmail()))){
            log.info(user.getEmail() + " já existe no banco.");
            return;
        }

        userRepository.save(user);

        Role roleAdmin = new Role(null, Role.ADMIN);
        Role roleBasic = new Role(null, Role.BASIC);

        roleRepository.save(roleAdmin);
        roleRepository.save(roleBasic);

        rolesRepository.save(new UserRoles(null, user, roleAdmin));
        rolesRepository.save(new UserRoles(null, user, roleBasic));
    }
}
