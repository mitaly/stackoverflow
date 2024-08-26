package com.craft.stackoverflow.repository;

import com.craft.stackoverflow.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    TestEntityManager entityManager;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setEmail("mitaly@gmail.com");
        user.setUsername("mitaly");
        entityManager.persist(user);
    }

    @Test
    public void whenFindByUsername_thenReturnUser() {
        String username = "mitaly";
        Optional<User> user = userRepository.findByUsername(username);
        assertNotNull(user);
        assertThat(user.get().getUsername()).isEqualTo(username);
    }
}
