package com.craft.stackoverflow.controller;

import com.craft.stackoverflow.model.PostModel;
import com.craft.stackoverflow.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<Page<List<PostModel>>> search(@RequestParam("query") String query,
                                                        @RequestParam("tags") String tags,
                                                        @RequestParam(value = "page", required = true) Integer page,
                                                        @RequestParam(value = "size", required = false) Integer size) {
        return ResponseEntity.ok(searchService.search(page, size, query, tags));
    }

    @GetMapping("questions/top")
    public ResponseEntity<Page<List<PostModel>>> getTopQuestions(@RequestParam(value = "page", required = true) Integer page,
                                                                 @RequestParam(value = "size", required = false) Integer size) {
        return ResponseEntity.ok(searchService.getTopQuestions(page, size));
    }
}
