package com.craft.stackoverflow.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails{
    @Id
    @GeneratedValue
    Long id;
    @Column(unique = true)
    String username;
    @Column(unique = true)
    String email;
    String passwordHash;

    String password;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
