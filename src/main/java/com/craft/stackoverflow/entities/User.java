package com.craft.stackoverflow.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    Long id;
    String username;
    String email;
    String passwordHash;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    List<Question> questions = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    List<Answer> answers = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    List<Comment> comments = new ArrayList<>();

    @JsonIgnore
    public String getPasswordHash() {
        return passwordHash;
    }
    @JsonIgnore
    public List<Question> getQuestions() {
        return questions;
    }
    @JsonIgnore
    public List<Answer> getAnswers() {
        return answers;
    }
    @JsonIgnore
    public List<Comment> getComments() {
        return comments;
    }
}
