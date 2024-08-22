package com.craft.stackoverflow.entities;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
public class Comment {
    @Id
    @GeneratedValue
    Long id;
    @ManyToOne
    @JoinColumn(name = "question_id")
    Question question;
    @ManyToOne
    @JoinColumn(name = "answer_id")
    Answer answer;
    String body;
    Timestamp createdAt;
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}
