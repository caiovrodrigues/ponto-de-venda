package com.caio.pdv.entities.repositories;

import com.caio.pdv.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u left join fetch u.sales where u.email = :email")
    User findUserByEmail(@Param("email") String email);

}
