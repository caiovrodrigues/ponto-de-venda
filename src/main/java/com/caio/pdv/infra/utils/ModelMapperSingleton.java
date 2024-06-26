package com.caio.pdv.infra.utils;

import com.caio.pdv.entities.User;
import com.caio.pdv.infra.security.CustomUserDetails;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

public class ModelMapperSingleton {

    private static ModelMapper modelMapper = null;

    public static ModelMapper getInstance(){
        if(Objects.isNull(modelMapper)){
            modelMapper = new ModelMapper();
        }
        return modelMapper;
    }

    public static User getUserFromContext(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof CustomUserDetails user)
            return user.getUser();
        throw new RuntimeException("get authentication not found a user.");
    }

}
