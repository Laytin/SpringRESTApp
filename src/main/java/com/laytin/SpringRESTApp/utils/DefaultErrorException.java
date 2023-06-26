package com.laytin.SpringRESTApp.utils;

public class DefaultErrorException extends RuntimeException{
    public DefaultErrorException(String msg) {
        super(msg);
    }
}
