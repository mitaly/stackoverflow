package com.craft.stackoverflow.service;


import com.craft.stackoverflow.elasticsearch.repository.QuestionElasticSearchRepository;
import com.craft.stackoverflow.model.QuestionModel;
import com.craft.stackoverflow.util.AppConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    @Autowired
    private QuestionElasticSearchRepository questionElasticSearchRepository;

    public List<QuestionModel> search(String query, String tags) {
        List<String> tagList = getTagList(tags);
        if (!query.trim().isEmpty() && !tagList.isEmpty()) {
            return questionElasticSearchRepository.searchByTextAndTags(query, tagList);
        } else if (!query.trim().isEmpty()) {
            return questionElasticSearchRepository.searchByText(query);
        } else if (!tagList.isEmpty()) {
            return questionElasticSearchRepository.searchByTags(tagList);
        }
        return List.of();
    }

    private List<String> getTagList(String tags) {
        List<String> tagList = List.of(tags.split(","));
        if(tagList.size() == AppConstant.ONE && tagList.get(0).trim().isBlank()) {
            tagList = List.of();
        }
        return tagList;
    }
}
