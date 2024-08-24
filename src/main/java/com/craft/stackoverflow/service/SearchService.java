package com.craft.stackoverflow.service;


import com.craft.stackoverflow.repository.QuestionSearchRepository;
import com.craft.stackoverflow.model.QuestionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {
    @Value("${search.default.page.size}")
    private int defaultPageSize;

    @Autowired
    private QuestionSearchRepository searchRepository;

    public Page<List<QuestionModel>> search(Integer page, Integer size, String query, String tags) {
        Pageable pageable = PageRequest.of(page, size == null ? defaultPageSize : size, Sort.Direction.DESC,
                "upVotes", "updatedAt");
        List<String> tagList = parseTagList(tags);
        if (!query.trim().isEmpty() && !tagList.isEmpty()) {
            return searchRepository.searchByTextAndTags(query, tagList, pageable);
        } else if (!query.trim().isEmpty()) {
            return searchRepository.searchByText(query, pageable);
        } else if (!tagList.isEmpty()) {
            return searchRepository.searchByTags(tagList, pageable);
        }
        return Page.empty();
    }

    public Page<List<QuestionModel>> getTopQuestions(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size == null ? defaultPageSize : size, Sort.Direction.DESC,
                "upVotes", "updatedAt");
        return searchRepository.findTopQuestions(pageable);
    }

    private List<String> parseTagList(String tags) {
        if(tags.isBlank()) return List.of();
        return List.of(tags.split(","));
    }
}
