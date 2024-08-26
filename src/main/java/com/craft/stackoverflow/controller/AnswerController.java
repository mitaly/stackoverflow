package com.craft.stackoverflow.controller;


import com.craft.stackoverflow.dto.AnswerDto;
import com.craft.stackoverflow.dto.QuestionDTO;
import com.craft.stackoverflow.entities.Answer;
import com.craft.stackoverflow.entities.User;
import com.craft.stackoverflow.service.AnswerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/answers")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @PostMapping
    ResponseEntity<AnswerDto> create(@RequestPart("answer") String answerDto,
                                  @RequestPart(value = "multimedia", required = false) MultipartFile file,
                                  @AuthenticationPrincipal User user) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
//        TODO: apply validation on dto
        AnswerDto data = objectMapper.readValue(answerDto, AnswerDto.class);
        return ResponseEntity.ok(answerService.create(data, user));
    }

}
