package com.craft.stackoverflow.controller;

import com.craft.stackoverflow.dto.QuestionDTO;
import com.craft.stackoverflow.entities.MultimediaPath;
import com.craft.stackoverflow.entities.Question;
import com.craft.stackoverflow.entities.Tag;
import com.craft.stackoverflow.entities.User;
import com.craft.stackoverflow.service.*;
import com.craft.stackoverflow.util.ValidatorUtil;
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
    @Autowired
    private ValidatorUtil validatorUtil;

    @PostMapping
    ResponseEntity<Question> create(@RequestPart("question") String questionRequest,
                                    @RequestPart(value = "multimedia", required = false) MultipartFile file,
                                    @AuthenticationPrincipal User user) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        QuestionDTO questionDto = objectMapper.readValue(questionRequest, QuestionDTO.class);
        validatorUtil.isValid(questionDto);
        return ResponseEntity.ok(questionService.create(questionDto, file, user.getId()));
    }

}
