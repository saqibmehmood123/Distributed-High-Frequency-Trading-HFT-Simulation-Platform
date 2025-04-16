package com.hft.exceptionhandling;

public class OrderNotFoundException  extends Exception {

    Long id ;


    public OrderNotFoundException(Long id)
    {
        this.id = id;
    }
}
