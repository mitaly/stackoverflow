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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.util.*;

@RestController
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    private QuestionService questionService;


    @PostMapping
    ResponseEntity<Question> create(@RequestPart("question") String questionDTO,
                                    @RequestPart(value = "multimedia") MultipartFile file) throws JsonProcessingException {
        System.out.println("question " + questionDTO);
        ObjectMapper objectMapper = new ObjectMapper();
        QuestionDTO data = objectMapper.readValue(questionDTO, QuestionDTO.class);
        return ResponseEntity.ok(questionService.create(data, file));
    }

}
