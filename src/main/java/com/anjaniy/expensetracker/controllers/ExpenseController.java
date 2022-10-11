package com.anjaniy.expensetracker.controllers;

import com.anjaniy.expensetracker.dto.ExpenseDto;
import com.anjaniy.expensetracker.services.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expense")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ExpenseDto> getAllExpenses(){
        return expenseService.getAllExpenses();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ExpenseDto getExpense(@PathVariable ("id") String id){
        return expenseService.getExpense(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String addExpense(@RequestBody ExpenseDto expenseDto){
        expenseService.addExpense(expenseDto);
        return "Expense Added Successfully";
    }

    @PutMapping("/updateExpense")
    @ResponseStatus(HttpStatus.OK)
    public String updateExpense(@RequestBody ExpenseDto expenseDto){
        expenseService.updateExpense(expenseDto);
        return "Expense Updated Successfully";
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteExpense(@PathVariable ("id") String id){
        expenseService.deleteExpense(id);
        return "Expense Deleted Successfully";
    }
}
