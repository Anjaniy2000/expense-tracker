package com.anjaniy.expensetracker.services;

import com.anjaniy.expensetracker.enums.ExpenseCategory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ExpenseCategoryService {

    public List<ExpenseCategory> getAllCategories() {
        return Arrays.stream(ExpenseCategory.values()).collect(Collectors.toList());
    }
}
