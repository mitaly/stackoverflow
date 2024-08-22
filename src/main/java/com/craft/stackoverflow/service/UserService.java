package com.craft.stackoverflow.service;

import com.craft.stackoverflow.entities.User;
import com.craft.stackoverflow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
}
