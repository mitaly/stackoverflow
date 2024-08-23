package com.craft.stackoverflow.controller;

import com.craft.stackoverflow.model.QuestionModel;
import com.craft.stackoverflow.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping
    public ResponseEntity<List<QuestionModel>> search(@RequestParam("q") String query,
                                                      @RequestParam("t") String tags) {
        return ResponseEntity.ok(searchService.search(query, tags));
    }

    @GetMapping("questions/top")
    public ResponseEntity<List<QuestionModel>> getTopQuestions() {
        return ResponseEntity.ok(searchService.getTopQuestions());
    }
}
