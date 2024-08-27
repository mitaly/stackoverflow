package com.craft.stackoverflow.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserDto {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;
}