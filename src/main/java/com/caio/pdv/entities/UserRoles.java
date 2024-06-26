package com.caio.pdv.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user_roles", uniqueConstraints = { // Unique contraints diz que o valor sao validos somente se eles sao unicos
        @UniqueConstraint(columnNames = {"user_id", "role_id"}) // Nesse caso dizemos que a coluna user_id e role_id sao unicas e nao podem se repetir na tabela
})
public class UserRoles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

}
