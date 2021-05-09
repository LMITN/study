package com.exception;

public class InsufficientBalanceException extends Exception{

    public String message;

    public InsufficientBalanceException(String message){
        this.message= message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }


}
