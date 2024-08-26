package com.craft.stackoverflow.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Post {

    @Id
    @GeneratedValue
    Long id;

    @OneToMany(mappedBy = "post")
    List<Vote> votes = new ArrayList<>();

    public int getVotesCount() {
        int counter = 0;
        for(Vote vote: votes){
            if(vote.getVoteType() == VoteType.UPVOTE){
                counter++;
            }else{
                counter--;
            }
        }
        return counter;
    }

}
