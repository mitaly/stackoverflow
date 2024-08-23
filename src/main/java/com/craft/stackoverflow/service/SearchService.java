package com.craft.stackoverflow.service;


import com.craft.stackoverflow.elasticsearch.repository.QuestionElasticSearchRepository;
import com.craft.stackoverflow.model.QuestionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    @Autowired
    private QuestionElasticSearchRepository questionElasticSearchRepository;

    public List<QuestionModel> search(String query, String tags) {
        List<String> tagList = List.of(tags.split(","));
        List<QuestionModel> questionModel = questionElasticSearchRepository.searchByTitleOrBody(query);
        return questionModel;
    }

}
