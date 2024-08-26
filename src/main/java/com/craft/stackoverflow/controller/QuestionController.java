package com.craft.stackoverflow.controller;

import com.craft.stackoverflow.dto.QuestionDTO;
import com.craft.stackoverflow.entities.MultimediaPath;
import com.craft.stackoverflow.entities.Question;
import com.craft.stackoverflow.entities.Tag;
import com.craft.stackoverflow.entities.User;
import com.craft.stackoverflow.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.security.Principal;
import java.util.*;

@RestController
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @PostMapping
    ResponseEntity<QuestionDTO> create(@RequestPart("question") String questionDTO,
                                    @RequestPart(value = "multimedia", required = false) MultipartFile file,
                                    @AuthenticationPrincipal User user) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
//        TODO: apply validation on dto
        QuestionDTO data = objectMapper.readValue(questionDTO, QuestionDTO.class);
        return ResponseEntity.ok(questionService.create(data, file, user.getId()));
    }

    @PatchMapping("/upvote/{questionId}/{upVoteValue}")
    ResponseEntity<QuestionDTO> update(@PathVariable Long questionId, @PathVariable int upVoteValue, @AuthenticationPrincipal User user){


        return ResponseEntity.ok(questionService.upvoteQuestion(questionId, upVoteValue, user));
    }

    @GetMapping("/{questionId}")
    ResponseEntity<QuestionDTO> getQuestion(@PathVariable Long questionId){
        return ResponseEntity.ok(questionService.getQuestionById(questionId));
    }

}
