package com.craft.stackoverflow.repository;

import com.craft.stackoverflow.model.QuestionModel;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionSearchRepository extends ElasticsearchRepository<QuestionModel, String> {

    @Query("{\n" +
            "    \"multi_match\": {\n" +
            "      \"query\": \"?0\",\n" +
            "      \"fields\": [\"title\", \"body\"]\n" +
            "    }\n" +
            "  }")
    public List<QuestionModel> searchByText(String text);

    @Query("{\n" +
            "     \"terms\": {\n" +
            "        \"tags\": ?0\n" +
            "      }\n" +
            "  }")
    public List<QuestionModel> searchByTags(List<String> tags);

    @Query("{\n" +
            "            \"bool\": {\n" +
            "                \"must\": [\n" +
            "                    {\n" +
            "                     \"terms\": {\n" +
            "                        \"tags\": ?1\n" +
            "                      }\n" +
            "                  },\n" +
            "                  {\n" +
            "                    \"multi_match\": {\n" +
            "                      \"query\": \"?0\",\n" +
            "                      \"fields\": [\"title\", \"body\"]\n" +
            "                    }\n" +
            "                  }\n" +
            "                ]\n" +
            "            }\n" +
            "        }")
    public List<QuestionModel> searchByTextAndTags(String text, List<String> tags);

    @Query("{\n" +
            "  \"query\": {\n" +
            "    \"match_all\": {}\n" +
            "  },\n" +
            "  \"sort\": [\n" +
            "    {\n" +
            "      \"upVotes\": {\n" +
            "        \"order\": \"desc\"\n" +
            "      }\n" +
            "    },\n" +
            "    {\n" +
            "      \"lastModifiedDate\": {\n" +
            "        \"order\": \"desc\"\n" +
            "      }\n" +
            "    }\n" +
            "  ],\n" +
            "  \"size\": 10\n" +
            "}")
    public List<QuestionModel> findTopQuestions();
}
