package com.anjaniy.expensetracker.exceptions;

public class ExpenseCategoryNotAvailableException extends RuntimeException{

    public ExpenseCategoryNotAvailableException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
