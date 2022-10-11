package com.anjaniy.expensetracker.dto;

import com.anjaniy.expensetracker.enums.ExpenseCategory;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExpenseDto {
    private String id;
    private String expenseName;
    private String expenseDescription;
    private ExpenseCategory expenseCategory;
    private int expenseAmount;
    private String userId;
}