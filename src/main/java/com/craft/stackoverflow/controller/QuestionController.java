package com.craft.stackoverflow.controller;

import com.craft.stackoverflow.dto.QuestionDTO;
import com.craft.stackoverflow.entities.MultimediaPath;
import com.craft.stackoverflow.entities.Question;
import com.craft.stackoverflow.service.FileUploadService;
import com.craft.stackoverflow.service.MultimediaPathService;
import com.craft.stackoverflow.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private MultimediaPathService multimediaPathService;

    @PostMapping
    ResponseEntity<Question> create(@RequestBody QuestionDTO questionDTO,
                                    @RequestParam("images") MultipartFile[] files) {
        Question question = questionService.create(questionDTO);
        List<String> paths = new ArrayList<>();
        for (MultipartFile file : files) {
            String path = fileUploadService.uploadFile(file);
            paths.add(path);
        }
        List<MultimediaPath> multimediaPaths = new ArrayList<>();
        for (String path : paths) {
            multimediaPaths.add(multimediaPathService.create(new MultimediaPath(path,
                    'Q', question, null)));
        }
        question.getMultimediaPaths().addAll(multimediaPaths);
        return ResponseEntity.ok(question);
    }
}
