package com.anjaniy.expensetracker.controllers;

import com.anjaniy.expensetracker.services.SalaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/salary")
@Slf4j
public class SalaryController {

    @Autowired
    private SalaryService salaryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public int getSalary(){
        return salaryService.getSalary();
    }

    @PutMapping("/{salary}")
    @ResponseStatus(HttpStatus.OK)
    public String updateSalary(@PathVariable("salary") int salary){
        salaryService.updateSalary(salary);
        return "Salary Updated Successfully!";
    }

    @GetMapping("/reset")
    @ResponseStatus(HttpStatus.OK)
    public String reset(){
        salaryService.reset();
        return "Everything Has Been Reset Successfully";
    }

    @GetMapping("/totalExpenseAmount")
    @ResponseStatus(HttpStatus.OK)
    public int getTotalExpenseAmount(){
        return salaryService.getTotalExpenseAmount();
    }
}
