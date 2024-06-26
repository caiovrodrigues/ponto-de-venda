package com.caio.pdv.services.exceptions;

public class ProductEstoqueInsuficiente extends RuntimeException {

    public ProductEstoqueInsuficiente(String msg){
        super(msg);
    }
}
