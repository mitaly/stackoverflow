package com.craft.stackoverflow.elasticsearch.repository;

import com.craft.stackoverflow.model.QuestionModel;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionElasticSearchRepository extends ElasticsearchRepository<QuestionModel, String> {

    @Query("{\n" +
            "    \"multi_match\": {\n" +
            "      \"query\": \"?0\",\n" +
            "      \"fields\": [\"title\", \"body\"]\n" +
            "    }\n" +
            "  }")
    public List<QuestionModel> searchByTitleOrBody(String title);
}
