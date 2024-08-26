package com.craft.stackoverflow.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Data
@EntityListeners(AuditingEntityListener.class)
public class Vote {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    private VoteType voteType;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;


}
