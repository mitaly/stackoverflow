package com.craft.stackoverflow.service;

import com.craft.stackoverflow.dto.PostResponseDto;
import com.craft.stackoverflow.entities.*;
import com.craft.stackoverflow.exception.BusinessException;
import com.craft.stackoverflow.mapper.PostMapper;
import com.craft.stackoverflow.model.PostModel;
import com.craft.stackoverflow.repository.PostRepository;
import com.craft.stackoverflow.repository.PostSearchRepository;
import com.craft.stackoverflow.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private PostSearchRepository searchRepository;
    @Autowired
    private SearchService searchService;

    @Autowired
    private PostMapper postMapper;

    public PostResponseDto updateVote(Long postId, VoteType voteType, User user) {
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
            handleNewVoteForUser(voteType, user, post);
        } else {
            handleUserVoteAlreadyPresent(voteType, post, usersVote);
        }

        int updatedVoteCnt= post.getVotesCount();
        // update vote for post in elastic search DB
        searchService.updateVoteForId(updatedVoteCnt, post.getId());

        return postMapper.postToPostDto(post);
    }

    private void handleNewVoteForUser(VoteType voteType, User user, Post post) {
        Vote vote = new Vote();
        vote.setUser(user);
        vote.setVoteType(voteType);
        vote.setPost(post);
        vote = voteRepository.save(vote);
        List<Vote> voteList = post.getVotes();
        voteList.add(vote);
        post.setVotes(voteList);
    }

    private void handleUserVoteAlreadyPresent(VoteType voteType, Post post, Optional<Vote> usersVote) {
        Vote vote = usersVote.get();
        // user trying to cast same vote, then delete the vote
        if(vote.getVoteType() == voteType) {
            voteRepository.delete(vote);
            List<Vote> voteList = post.getVotes();
            voteList.remove(vote);
        }else{
            // remove previous vote and cast new vote for that user
            List<Vote> voteList = post.getVotes();
            voteList.remove(vote);
            vote.setVoteType(voteType);
            voteList.add(vote);
            post.setVotes(voteList);
            voteRepository.save(vote);
        }
    }

}
