package com.craft.stackoverflow.controller;

import com.craft.stackoverflow.dto.RegisterUserDto;
import com.craft.stackoverflow.exception.BusinessException;
import com.craft.stackoverflow.service.AuthenticationService;
import com.craft.stackoverflow.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
@ExtendWith(MockitoExtension.class)
public class AuthenticationControllerTest {
    @Mock
    private AuthenticationService authenticationService;
    @InjectMocks
    private AuthenticationController authenticationController;

    @Test
    void whenUserAlreadyExists_signupThrowsException() {
        RegisterUserDto registerUserDto = new RegisterUserDto();
        registerUserDto.setEmail("mitaly@gmail.com");
        registerUserDto.setUsername("mitaly");
        registerUserDto.setPassword("secret");
        when(authenticationService.signup(registerUserDto)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> {
            authenticationController.signup(registerUserDto);
        });
    }

}
