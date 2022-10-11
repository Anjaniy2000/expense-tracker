package com.anjaniy.expensetracker.repositories;

import com.anjaniy.expensetracker.models.Expense;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends MongoRepository<Expense, String> {

    @Query("{'userId': ?0}")
    List<Expense> findByUserId(String userId);

    @Query(value = "{'userId': ?0}", delete = true)
    void deleteByUserId(String userId);
}
