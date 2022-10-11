package com.anjaniy.expensetracker.services;

import com.anjaniy.expensetracker.dto.ExpenseDto;
import com.anjaniy.expensetracker.exceptions.ExpenseNotFoundException;
import com.anjaniy.expensetracker.exceptions.InsufficientSalaryBalanceException;
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
import java.util.stream.Collectors;

@Service
@Slf4j
public class ExpenseService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AuthService authService;

    public List<ExpenseDto> getAllExpenses() {
        List<Expense> expenses = expenseRepository.findByUserId(
                modelMapper.map(authService.getCurrentUser(), AppUser.class)
                        .getId()
        );
        return expenses.stream().map(expense -> modelMapper.map(expense, ExpenseDto.class)).collect(Collectors.toList());
    }

    public void addExpense(ExpenseDto expenseDto) {
        AppUser appUser = modelMapper.map(authService.getCurrentUser(), AppUser.class);
        AppUser currentUser = appUserRepository.findById(appUser.getId()).orElseThrow(()->new UserNotFoundException("User Not Found With ID: " + appUser.getId()));

        if (expenseDto.getExpenseAmount() > currentUser.getSalary()) {
            throw new InsufficientSalaryBalanceException("Unable To Proceed, You Have Insufficient Salary Balance!");
        } else {
            expenseDto.setUserId(currentUser.getId());
            currentUser.setSalary(currentUser.getSalary() - expenseDto.getExpenseAmount());
            appUserRepository.save(currentUser);
            expenseRepository.save(modelMapper.map(expenseDto, Expense.class));
        }
    }

    public ExpenseDto getExpense(String expenseId) {
        return modelMapper.map(expenseRepository.findById(expenseId), ExpenseDto.class);
    }

    public void deleteExpense(String expenseId) {
        AppUser appUser = modelMapper.map(authService.getCurrentUser(), AppUser.class);
        AppUser currentUser = appUserRepository.findById(appUser.getId()).orElseThrow(()->new UserNotFoundException("User Not Found With ID: " + appUser.getId()));
        Expense expense = expenseRepository.findById(expenseId).orElseThrow(() ->new ExpenseNotFoundException("Expense Not Found With ID: " + expenseId));
        currentUser.setSalary(currentUser.getSalary() + expense.getExpenseAmount());
        appUserRepository.save(currentUser);
        expenseRepository.deleteById(expenseId);
    }

    public void updateExpense(ExpenseDto expenseDto) {
        AppUser appUser = modelMapper.map(authService.getCurrentUser(), AppUser.class);
        AppUser currentUser = appUserRepository.findById(appUser.getId()).orElseThrow(()->new UserNotFoundException("User Not Found With ID: " + appUser.getId()));
        Expense expense = expenseRepository.findById(expenseDto.getId()).orElseThrow(() ->new ExpenseNotFoundException("Expense Not Found With ID: " + expenseDto.getId()));

        if(expense.getExpenseAmount() > expenseDto.getExpenseAmount()){
            currentUser.setSalary(currentUser.getSalary() + (expense.getExpenseAmount() - expenseDto.getExpenseAmount()));
        }else{
            currentUser.setSalary(currentUser.getSalary() - (expenseDto.getExpenseAmount() - expense.getExpenseAmount()));
        }

        appUserRepository.save(currentUser);
        modelMapper.map(expenseRepository.save(modelMapper.map(expenseDto, Expense.class)), ExpenseDto.class);
    }
}




