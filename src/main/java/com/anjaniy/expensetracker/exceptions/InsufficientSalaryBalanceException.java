package com.anjaniy.expensetracker.exceptions;

public class InsufficientSalaryBalanceException extends RuntimeException{
    public InsufficientSalaryBalanceException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
