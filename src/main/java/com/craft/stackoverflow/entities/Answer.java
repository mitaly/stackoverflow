package com.craft.stackoverflow.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Data
@EntityListeners(AuditingEntityListener.class)
public class Answer {
    @Id
    @GeneratedValue
    Long id;

    @ManyToOne
    @JoinColumn(name = "question_id")
    Question question;

    String body;
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    Date createdDate;
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    Date lastModifiedDate;
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
