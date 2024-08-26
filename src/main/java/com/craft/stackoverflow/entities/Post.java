package com.craft.stackoverflow.entities;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@MappedSuperclass
public class Post {


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
