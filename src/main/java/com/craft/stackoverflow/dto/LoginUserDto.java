package com.craft.stackoverflow.dto;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserDto {
    private String username;
    private String password;
}