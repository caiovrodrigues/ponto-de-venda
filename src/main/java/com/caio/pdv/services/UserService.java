package com.caio.pdv.services;

import com.caio.pdv.entities.User;
import com.caio.pdv.infra.security.SecurityConfig;
import com.caio.pdv.infra.utils.ModelMapperSingleton;
import com.caio.pdv.entities.repositories.UserRepository;
import com.caio.pdv.web.dto.LoginRequestDTO;
import com.caio.pdv.web.dto.UserCadastroRequest;
import com.caio.pdv.web.dto.UserResponseDTO;
import com.nimbusds.jwt.JWTClaimsSet;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.grammars.hql.HqlParser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtEncoder jwtEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
    }

    public Page<User> findAll(Pageable pageable){
        return userRepository.findAll(pageable);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("User com o id %d não encontrado.", id)));
    }

    public UserResponseDTO findByIdDTO(Long id) {
        User user = findById(id);

        var modelMapper = ModelMapperSingleton.getInstance();
        return modelMapper.map(user, UserResponseDTO.class);
    }

    @Transactional
    public User save(UserCadastroRequest userDTO){
        User userOpt = userRepository.findUserByEmail(userDTO.getEmail());

//        if(userOpt.isPresent()){
//            throw new UserAlreadyExist(String.format("Email %s já cadastrado.", userDTO.getEmail()));
//        }

        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        return userRepository.save(ModelMapperSingleton.getInstance().map(userDTO, User.class));
    }

    @Transactional
    public User update(Long id, User user) {
        User userById = findById(id);
        userById.setName(user.getName());
        userById.setIsEnabled(user.getIsEnabled());
        return userRepository.save(userById);
    }

    public void delete(User user) {
        User byId = findById(user.getId());
        userRepository.delete(byId);
    }

    public String logar(LoginRequestDTO login) {
        User userOpt = userRepository.findUserByEmail(login.email());

        if(Objects.isNull(userOpt)){
            throw new EntityNotFoundException("Entity not found");
        }

        boolean passwordMatches = passwordEncoder.matches(login.password(), userOpt.getPassword());

        if(!passwordMatches){
            throw new BadCredentialsException("email or username is invalid.");
        }

        var scopes = userOpt.getRoles().stream()
                .map(roles -> roles.getRole().getName())
                .collect(Collectors.joining(" "));

        Instant now = Instant.now();
        var expireAt = 300;

        var claims = JwtClaimsSet.builder()
                .issuer("pdv-backend")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expireAt))
                .subject(userOpt.getEmail())
                .claim("id", userOpt.getId())
                .claim("scope", scopes)
                .build();


        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
