package com.craft.stackoverflow.entities;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Answer {
    @Id
    @GeneratedValue
    Long id;

    @ManyToOne
    @JoinColumn(name = "question_id")
    Question question;

    String body;
    Timestamp createdAt;
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
    @OneToMany(mappedBy = "answer")
    List<Comment> comments = new ArrayList<>();

    int upVotes;
    int downVotes;
    boolean isAccepted;
    @OneToMany(mappedBy = "answer")
    List<MultimediaPath> multimediaPaths;
}
