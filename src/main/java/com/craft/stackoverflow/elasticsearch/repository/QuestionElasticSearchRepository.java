package com.craft.stackoverflow.elasticsearch.repository;

import com.craft.stackoverflow.model.QuestionModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionElasticSearchRepository extends ElasticsearchRepository<QuestionModel, String> {
}
