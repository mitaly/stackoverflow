package com.craft.stackoverflow.repository;

import com.craft.stackoverflow.model.QuestionModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<List<QuestionModel>> searchByText(String text, Pageable pageable);

    @Query("{\n" +
            "     \"terms\": {\n" +
            "        \"tags\": ?0\n" +
            "      }\n" +
            "  }")
    public Page<List<QuestionModel>> searchByTags(List<String> tags, Pageable pageable);

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
    public Page<List<QuestionModel>> searchByTextAndTags(String text, List<String> tags, Pageable pageable);

    @Query("{\n" +
            "    \"match_all\": {}\n" +
            "  }")
    public Page<List<QuestionModel>> findTopQuestions(Pageable pageable);
}
