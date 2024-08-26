package com.craft.stackoverflow.mock;

import com.craft.stackoverflow.entities.User;

public class MockData {
    public static User getUser() {
        User user = new User();
        user.setEmail("mitaly@gmail.com");
        user.setUsername("mitaly");
        return user;
    }
}
