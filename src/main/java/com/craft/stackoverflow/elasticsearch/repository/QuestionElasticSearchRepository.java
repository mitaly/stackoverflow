package com.craft.stackoverflow.elasticsearch.repository;

import com.craft.stackoverflow.model.QuestionModel;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionElasticSearchRepository extends ElasticsearchRepository<QuestionModel, String> {

    @Query("{\n" +
            "\t\"query_string\": {\n" +
            "\t  \"query\": \"?0\"\n" +
            "\t}\n" +
            "}")
    public List<QuestionModel> findBySearchOnAllFields(String title);
}
