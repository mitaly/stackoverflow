package com.craft.stackoverflow.controller;


import com.craft.stackoverflow.dto.AnswerDto;
import com.craft.stackoverflow.entities.Answer;
import com.craft.stackoverflow.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/answers")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @PostMapping
    ResponseEntity<Answer> create(@RequestBody AnswerDto answerDto) {
        System.out.println("Create create" + answerDto);
        return ResponseEntity.ok(answerService.create(answerDto));
    }

}
