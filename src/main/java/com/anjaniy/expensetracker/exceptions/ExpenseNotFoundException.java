package com.anjaniy.expensetracker.exceptions;

public class ExpenseNotFoundException extends RuntimeException{
    public ExpenseNotFoundException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
