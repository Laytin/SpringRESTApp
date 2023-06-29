package com.laytin.SpringRESTApp.utils.errors;

public class DefaultErrorException extends RuntimeException{
    public DefaultErrorException(String msg) {
        super(msg);
    }
}
