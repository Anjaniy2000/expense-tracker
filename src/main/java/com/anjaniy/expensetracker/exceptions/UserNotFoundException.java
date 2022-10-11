package com.anjaniy.expensetracker.exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String exceptionMessage){
        super(exceptionMessage);
    }
}
