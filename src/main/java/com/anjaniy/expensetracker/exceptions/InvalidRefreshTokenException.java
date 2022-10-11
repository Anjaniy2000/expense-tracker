package com.anjaniy.expensetracker.exceptions;

public class InvalidRefreshTokenException extends RuntimeException{
    public InvalidRefreshTokenException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
