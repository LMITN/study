package com.exception;

public class InvalidProductException extends Exception{
    public String message;

    public InvalidProductException(String message){
        this.message= message;

    }


    @Override
    public String getMessage() {
        return this.message;
    }

}
