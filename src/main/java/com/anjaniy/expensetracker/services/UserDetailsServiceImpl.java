package com.anjaniy.expensetracker.services;

import com.anjaniy.expensetracker.exceptions.UserNotFoundException;
import com.anjaniy.expensetracker.models.AppUser;
import com.anjaniy.expensetracker.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<AppUser> appUserOptional = appUserRepository.findByEmail(email);
        AppUser appUser = appUserOptional.orElseThrow(() -> new UserNotFoundException("User Not Found With The Given Email Address:- " + email));
        return new User(appUser.getEmail(), appUser.getPassword(), new ArrayList<>());
    }
}
