package com.caio.pdv.services;

import com.caio.pdv.entities.User;
import com.caio.pdv.infra.security.SecurityConfig;
import com.caio.pdv.infra.utils.ModelMapperSingleton;
import com.caio.pdv.services.exceptions.UserAlreadyExist;
import com.caio.pdv.entities.repositories.UserRepository;
import com.caio.pdv.web.dto.LoginRequestDTO;
import com.caio.pdv.web.dto.UserCadastroRequest;
import com.caio.pdv.web.dto.UserResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
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

        userDTO.setPassword(SecurityConfig.passwordEncoder().encode(userDTO.getPassword()));

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

    public User logar(LoginRequestDTO login) {
        User userOpt = userRepository.findUserByEmail(login.email());

//        if(userOpt.isEmpty()){
//            throw new EntityNotFoundException("Email não existe");
//        }

        if(!SecurityConfig.passwordEncoder().matches(login.password(), userOpt.getPassword())){
            throw new EntityNotFoundException("Credenciais erradas");
        }else{
            return userOpt;
        }
    }
}
