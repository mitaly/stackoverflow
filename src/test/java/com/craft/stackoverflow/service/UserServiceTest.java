package com.craft.stackoverflow.service;

import com.craft.stackoverflow.entities.User;
import com.craft.stackoverflow.mock.MockData;
import com.craft.stackoverflow.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Optional;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;
    @Test
    void givenExistingUser_whenQueried_returnsUser() {
        User user = MockData.getUser();
        user.setId(1L);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        Optional<User> response = userService.findById(1L);
        assertTrue(response.isPresent());
        assertThat(response.get()).isEqualTo(user);
    }
}
