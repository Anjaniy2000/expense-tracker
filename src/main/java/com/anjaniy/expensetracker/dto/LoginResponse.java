package com.anjaniy.expensetracker.dto;

import lombok.*;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LoginResponse {
    private String email;
    private String authenticationToken;
    private Instant expiresAt;
    private String refreshToken;
}
