package com.anjaniy.expensetracker.repositories;

import com.anjaniy.expensetracker.models.AppUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends MongoRepository<AppUser, String> {
    @Query("{'email': ?0}")
    Optional<AppUser> findByEmail(String email);


}
