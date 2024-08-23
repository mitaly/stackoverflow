package com.craft.stackoverflow.entities;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
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
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    Date createdDate;
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    Date lastModifiedDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}
