package com.craft.stackoverflow.controller;

import com.craft.stackoverflow.dto.PostResponseDto;
import com.craft.stackoverflow.entities.User;
import com.craft.stackoverflow.entities.VoteType;
import com.craft.stackoverflow.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private VoteService voteService;

    @PostMapping("{postId}/vote")
    public ResponseEntity<PostResponseDto> votePost(@PathVariable Long postId,
                                                    @RequestParam("voteType") VoteType voteType,
                                                    @AuthenticationPrincipal User user){
        return ResponseEntity.ok(voteService.addVote(postId, voteType, user));
    }


}
