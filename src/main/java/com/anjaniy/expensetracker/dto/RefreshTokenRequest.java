package com.anjaniy.expensetracker.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RefreshTokenRequest {
    @NotBlank(message = "Refresh Token Is Required!")
    private String refreshToken;
    @NotBlank(message = "Email Address Is Required!")
    private String email;
}
