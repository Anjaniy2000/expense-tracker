package com.anjaniy.expensetracker.exceptions;

public class UserAlreadyPresentException extends RuntimeException{
    public UserAlreadyPresentException(String exceptionMessage){
        super(exceptionMessage);
    }
}