package com.craft.stackoverflow.controller;
import com.craft.stackoverflow.dto.LoginUserDto;
import com.craft.stackoverflow.dto.RegisterUserDto;
import com.craft.stackoverflow.entities.User;
import com.craft.stackoverflow.exception.BusinessException;
import com.craft.stackoverflow.model.LoginResponse;
import com.craft.stackoverflow.service.AuthenticationService;
import com.craft.stackoverflow.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    @Autowired
    private JWTService jwtService;
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto) {
        Optional<User> registeredUser = authenticationService.signup(registerUserDto);
        if(registeredUser.isEmpty()) {
            throw new BusinessException(HttpStatus.BAD_REQUEST.value(),
                    "user.already.exist", registerUserDto.getEmail());
        }
        return ResponseEntity.ok(registeredUser.get());
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }
}