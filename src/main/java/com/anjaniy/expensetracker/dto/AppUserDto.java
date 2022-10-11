package com.anjaniy.expensetracker.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppUserDto {
    private String id;
    private String name;
    private String email;
    private String password;
}
