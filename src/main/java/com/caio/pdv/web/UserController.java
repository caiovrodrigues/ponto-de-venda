package com.caio.pdv.web;

import com.caio.pdv.entities.User;
import com.caio.pdv.services.UserService;
import com.caio.pdv.web.dto.LoginRequestDTO;
import com.caio.pdv.web.dto.UserCadastroRequest;
import com.caio.pdv.web.dto.UserResponseDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Page<User>> findAll(@PageableDefault() Pageable pageable){
        Page<User> users = userService.findAll(pageable);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(userService.findByIdDTO(id));
    }

    @PostMapping
    public ResponseEntity<User> save(@Valid @RequestBody UserCadastroRequest user){
        User userSaved = userService.save(user);
        return ResponseEntity.ok(userSaved);
    }

    @PostMapping("/auth")
    public ResponseEntity<User> logar(@Valid @RequestBody LoginRequestDTO login){
        User userSaved = userService.logar(login);
        return ResponseEntity.ok(userSaved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user){
        User userUpdated = userService.update(id, user);
        return ResponseEntity.ok(userUpdated);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody User user){
        userService.delete(user);
        return ResponseEntity.noContent().build();
    }

}
