package com.craft.stackoverflow.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Question {
    @Id
    @GeneratedValue
    Long id;
    @Column(length = 200)
    String title;
    @Column(length = 1000)
    String body;
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    Date createdDate;
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    Date lastModifiedDate;
    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;


    @OneToMany(mappedBy = "question")
    List<UpVote> upVotes = new ArrayList<>();

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
