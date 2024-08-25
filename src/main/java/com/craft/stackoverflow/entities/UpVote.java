package com.craft.stackoverflow.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Data
@EntityListeners(AuditingEntityListener.class)
public class UpVote {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    private Vote vote;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
    @ManyToOne
    @JoinColumn(name = "answer_id")
    private Answer answer;


}
