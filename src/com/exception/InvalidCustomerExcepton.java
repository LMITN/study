package com.exception;

public class InvalidCustomerExcepton extends Exception{
    public String message;

    public InvalidCustomerExcepton(String message){
        this.message= message;

    }


    @Override
    public String getMessage() {
        return this.message;
    }
}
