package com.craft.stackoverflow.service;

import com.craft.stackoverflow.dto.QuestionDTO;
import com.craft.stackoverflow.entities.*;
import com.craft.stackoverflow.exception.BusinessException;
import com.craft.stackoverflow.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class VoteService {

    @Autowired
    private PostRepository postRepository;

    public Post addVote(Long postId, VoteType voteType, User user) {
        Optional<Post> post = postRepository.findById(postId);
        return post.get();
//        if (question.isEmpty()) {
//            throw new BusinessException(HttpStatus.NOT_FOUND.value(), "question.not.found", questionId);
//        }
//        Question question1 = question.get();
//        Optional<Vote> usersVote = question1.
//                getVotes()
//                .stream()
//                .filter(upVote ->
//                        Objects.equals(upVote.getUser().getId(), user.getId()))
//                .findFirst();
//
//        //user has not upvoted/downvoted the question.
//        if (usersVote.isEmpty()) {
//            Vote vote = new Vote();
//            vote.setUser(user);
//            vote.setVoteType(voteType);
//            vote.setPost(question1);
//            vote = voteRepository.save(vote);
//            List<Vote> voteList = question1.getVotes();
//            voteList.add(vote);
//            question1.setVotes(voteList);
//        } else {
//            Vote vote = usersVote.get();
//            if(vote.getVoteType() == voteType) {
//                voteRepository.delete(vote);
//                List<Vote> voteList = question1.getVotes();
//                voteList.remove(vote);
//            }else{
//                List<Vote> voteList = question1.getVotes();
//                voteList.remove(vote);
//                vote.setVoteType(voteType);
//                voteList.add(vote);
//                question1.setVotes(voteList);
//                voteRepository.save(vote);
//            }
//        }
//        return questionMapper.questionToQuestionDTO(question1);


    }

}
