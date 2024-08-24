package com.craft.stackoverflow.service;


import com.craft.stackoverflow.repository.QuestionSearchRepository;
import com.craft.stackoverflow.model.QuestionModel;
import com.craft.stackoverflow.util.AppConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    @Autowired
    private QuestionSearchRepository searchRepository;

    public List<QuestionModel> search(String query, String tags) {
        List<String> tagList = getTagList(tags);
        if (!query.trim().isEmpty() && !tagList.isEmpty()) {
            return searchRepository.searchByTextAndTags(query, tagList);
        } else if (!query.trim().isEmpty()) {
            return searchRepository.searchByText(query);
        } else if (!tagList.isEmpty()) {
            return searchRepository.searchByTags(tagList);
        }
        return List.of();
    }

    public Page<List<QuestionModel>> getTopQuestions(int page, int size) {
        Pageable pageable = PageRequest.of(page,size, Sort.Direction.DESC,
                "upVotes", "updatedAt");
        return searchRepository.findTopQuestions(pageable);
    }

    private List<String> getTagList(String tags) {
        List<String> tagList = List.of(tags.split(","));
        if(tagList.size() == AppConstant.ONE && tagList.get(0).trim().isBlank()) {
            tagList = List.of();
        }
        return tagList;
    }
}
