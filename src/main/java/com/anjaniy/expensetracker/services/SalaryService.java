package com.anjaniy.expensetracker.services;

import com.anjaniy.expensetracker.exceptions.SalaryUpdateException;
import com.anjaniy.expensetracker.exceptions.UserNotFoundException;
import com.anjaniy.expensetracker.models.AppUser;
import com.anjaniy.expensetracker.models.Expense;
import com.anjaniy.expensetracker.repositories.AppUserRepository;
import com.anjaniy.expensetracker.repositories.ExpenseRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class SalaryService {

    @Autowired
    private AuthService authService;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ExpenseRepository expenseRepository;


    public void updateSalary(int salary) {
        AppUser appUser = modelMapper.map(authService.getCurrentUser(), AppUser.class);
        AppUser currentUser = appUserRepository.findById(appUser.getId()).orElseThrow(() -> new UserNotFoundException("User Not Found With ID: " + appUser.getId()));

        int totalExpenseAmount = getTotalExpenseAmount();

        if(totalExpenseAmount < salary){
            currentUser.setSalary(salary - totalExpenseAmount);
            appUserRepository.save(currentUser);
        }else {
            throw new SalaryUpdateException("Your Updated Salary Must Be Greater Than Your Total Expense Amount!");
        }

    }

    public int getSalary() {
        AppUser appUser = modelMapper.map(authService.getCurrentUser(), AppUser.class);
        AppUser currentUser = appUserRepository.findById(appUser.getId()).orElseThrow(() -> new UserNotFoundException("User Not Found With ID: " + appUser.getId()));
        return currentUser.getSalary();
    }

    public void reset() {
        AppUser appUser = modelMapper.map(authService.getCurrentUser(), AppUser.class);
        AppUser currentUser = appUserRepository.findById(appUser.getId()).orElseThrow(() -> new UserNotFoundException("User Not Found With ID: " + appUser.getId()));
        currentUser.setSalary(0);
        expenseRepository.deleteByUserId(currentUser.getId());
        appUserRepository.save(currentUser);
    }

    public int getTotalExpenseAmount() {
        AppUser appUser = modelMapper.map(authService.getCurrentUser(), AppUser.class);
        AppUser currentUser = appUserRepository.findById(appUser.getId()).orElseThrow(() -> new UserNotFoundException("User Not Found With ID: " + appUser.getId()));

        List<Expense> expenses = expenseRepository.findByUserId(currentUser.getId());
        int totalExpenseAmount = 0;
        for(Expense expense: expenses){
            totalExpenseAmount = totalExpenseAmount + expense.getExpenseAmount();
        }

        return totalExpenseAmount;
    }
}
