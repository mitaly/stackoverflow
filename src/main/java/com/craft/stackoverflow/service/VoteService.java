package com.craft.stackoverflow.service;

import com.craft.stackoverflow.dto.PostDto;
import com.craft.stackoverflow.dto.QuestionDTO;
import com.craft.stackoverflow.entities.*;
import com.craft.stackoverflow.exception.BusinessException;
import com.craft.stackoverflow.mapper.PostMapper;
import com.craft.stackoverflow.repository.PostRepository;
import com.craft.stackoverflow.repository.VoteRepository;
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

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private PostMapper postMapper;

    public PostDto addVote(Long postId, VoteType voteType, User user) {
        Optional<Post> postOptional = postRepository.findById(postId);

        if (postOptional.isEmpty()) {
            throw new BusinessException(HttpStatus.NOT_FOUND.value(), "post.not.found", postId);
        }
        Post post = postOptional.get();
        Optional<Vote> usersVote = post.
                getVotes()
                .stream()
                .filter(upVote ->
                        Objects.equals(upVote.getUser().getId(), user.getId()))
                .findFirst();

        //user has not upvoted/downvoted the question.
        if (usersVote.isEmpty()) {
            Vote vote = new Vote();
            vote.setUser(user);
            vote.setVoteType(voteType);
            vote.setPost(post);
            vote = voteRepository.save(vote);
            List<Vote> voteList = post.getVotes();
            voteList.add(vote);
            post.setVotes(voteList);
        } else {
            Vote vote = usersVote.get();
            if(vote.getVoteType() == voteType) {
                voteRepository.delete(vote);
                List<Vote> voteList = post.getVotes();
                voteList.remove(vote);
            }else{
                List<Vote> voteList = post.getVotes();
                voteList.remove(vote);
                vote.setVoteType(voteType);
                voteList.add(vote);
                post.setVotes(voteList);
                voteRepository.save(vote);
            }
        }
        return postMapper.postToPostDto(post);


    }

}
