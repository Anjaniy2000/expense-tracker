package com.anjaniy.expensetracker.services;

import com.anjaniy.expensetracker.dto.*;
import com.anjaniy.expensetracker.exceptions.UserAlreadyPresentException;
import com.anjaniy.expensetracker.exceptions.UserNotFoundException;
import com.anjaniy.expensetracker.models.AppUser;
import com.anjaniy.expensetracker.repositories.AppUserRepository;
import com.anjaniy.expensetracker.security.JwtUtilities;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtilities jwtUtilities;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RefreshTokenService refreshTokenService;

    public void register(RegisterRequest registerRequest) {
        Optional<AppUser> appUserOptional = appUserRepository.findByEmail(registerRequest.getEmail());
        if(appUserOptional.isEmpty()){
            AppUser appUser = new AppUser();
            appUser.setName(registerRequest.getName());
            appUser.setEmail(registerRequest.getEmail());
            appUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            appUserRepository.save(appUser);
        }else{
            throw new UserAlreadyPresentException("User Is Already Present With The Given Email Address:- " + registerRequest.getEmail());
        }

    }

    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtUtilities.generateToken(authenticate);
        return LoginResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                .expiresAt(Instant.now().plusMillis(jwtUtilities.getJwtExpirationInMillis()))
                .email(loginRequest.getEmail())
                .build();
    }

    public AppUserDto getCurrentUser() {
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser appUser = appUserRepository.findByEmail(user.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User Not Found With The Given Email Address:- " + user.getUsername()));

        return modelMapper.map(appUser, AppUserDto.class);
    }

    public LoginResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        String token = jwtUtilities.generateTokenWithUserName(refreshTokenRequest.getEmail());
        return LoginResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtUtilities.getJwtExpirationInMillis()))
                .email(refreshTokenRequest.getEmail())
                .build();
    }
}
