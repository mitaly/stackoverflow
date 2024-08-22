package com.craft.stackoverflow.entities;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Question {
    @Id
    @GeneratedValue
    Long id;
    String title;
    String body;
    Timestamp createdAt;
    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;
    int upVotes;
    int downVotes;
    @OneToMany(mappedBy = "question")
    List<Answer> answers = new ArrayList<>();
    @OneToMany(mappedBy = "question")
    List<Comment> comments = new ArrayList<>();
    @ManyToMany
    @JoinTable(
            name = "question_tags",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    List<Tag> tags = new ArrayList<>();
    @OneToMany(mappedBy = "question")
    List<MultimediaPath> multimediaPaths = new ArrayList<>();
}
