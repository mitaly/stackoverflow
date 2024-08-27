package com.craft.stackoverflow.controller;

import com.craft.stackoverflow.dto.PostDto;
import com.craft.stackoverflow.entities.Post;
import com.craft.stackoverflow.entities.User;
import com.craft.stackoverflow.entities.VoteType;
import com.craft.stackoverflow.service.PostService;
import com.craft.stackoverflow.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private VoteService voteService;

    @GetMapping("{postId}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.getPost(postId));
    }

    @PostMapping("{postId}")
    public ResponseEntity<PostDto> votePost(@PathVariable Long postId,
                                            @RequestParam("voteType") VoteType voteType,
                                            @AuthenticationPrincipal User user){
        return ResponseEntity.ok(voteService.addVote(postId, voteType, user));
    }


}
