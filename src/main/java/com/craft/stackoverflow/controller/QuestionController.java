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
    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private MultimediaPathService multimediaPathService;


    @PostMapping
    ResponseEntity<Question> create(@RequestBody QuestionDTO questionDTO) {

//        List<String> paths = new ArrayList<>();
//        for (MultipartFile file : files) {
//            String path = fileUploadService.uploadFile(file);
//            paths.add(path);
//        }
//        List<MultimediaPath> multimediaPaths = new ArrayList<>();
//        for (String path : paths) {
//            multimediaPaths.add(multimediaPathService.create(new MultimediaPath(path,
//                    'Q', question, null)));
//        }
//
//        question.getMultimediaPaths().addAll(multimediaPaths);
        return ResponseEntity.ok(questionService.create(questionDTO));
    }
}
