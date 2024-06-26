package com.caio.pdv.services.exceptions;

public class UserAlreadyExist extends RuntimeException{
    public UserAlreadyExist(String msg){
        super(msg);
    }
}
