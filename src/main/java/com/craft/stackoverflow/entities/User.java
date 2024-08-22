package com.craft.stackoverflow.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue
    Long id;
    String username;
    String email;
    String passwordHash;
    @OneToMany(mappedBy = "user")
    List<Question> questions = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    List<Answer> answers = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    List<Comment> comments = new ArrayList<>();
}
