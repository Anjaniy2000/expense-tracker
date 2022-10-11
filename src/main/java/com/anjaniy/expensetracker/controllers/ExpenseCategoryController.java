package com.anjaniy.expensetracker.controllers;
import com.anjaniy.expensetracker.enums.ExpenseCategory;
import com.anjaniy.expensetracker.services.ExpenseCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/expenseCategory")
public class ExpenseCategoryController {

    @Autowired
    private ExpenseCategoryService expenseCategoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ExpenseCategory> getAllCategories() {
        return expenseCategoryService.getAllCategories();
    }
}
