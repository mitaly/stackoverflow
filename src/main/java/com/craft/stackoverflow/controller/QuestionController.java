package com.craft.stackoverflow.controller;

import com.craft.stackoverflow.dto.QuestionDTO;
import com.craft.stackoverflow.entities.MultimediaPath;
import com.craft.stackoverflow.entities.Question;
import com.craft.stackoverflow.entities.Tag;
import com.craft.stackoverflow.entities.User;
import com.craft.stackoverflow.service.*;
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


//    @PostMapping
//    ResponseEntity<Question> create(@RequestPart("question") QuestionDTO questionDTO,
//                                    @RequestPart(value = "multimedia") MultipartFile file) {
//        return ResponseEntity.ok(questionService.create(questionDTO, file));
//    }

    @PostMapping
    ResponseEntity<Question> create(@RequestBody  QuestionDTO questionDTO) {
        return ResponseEntity.ok(questionService.create(questionDTO));
    }

}
