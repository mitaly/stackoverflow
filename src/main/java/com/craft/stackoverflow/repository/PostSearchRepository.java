package com.craft.stackoverflow.repository;

import com.craft.stackoverflow.model.PostModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostSearchRepository extends ElasticsearchRepository<PostModel, String> {

    @Query("{\n" +
            "    \"multi_match\": {\n" +
            "      \"query\": \"?0\",\n" +
            "      \"fields\": [\"title\", \"body\"]\n" +
            "    }\n" +
            "  }")
    public Page<List<PostModel>> searchByText(String text, Pageable pageable);

    @Query("{\n" +
            "     \"terms\": {\n" +
            "        \"tags\": ?0\n" +
            "      }\n" +
            "  }")
    public Page<List<PostModel>> searchByTags(List<String> tags, Pageable pageable);

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
    public Page<List<PostModel>> searchByTextAndTags(String text, List<String> tags, Pageable pageable);

    @Query("{\n" +
            "    \"match\": {\n" +
            "      \"postType\": \"QUESTION\"\n" +
            "    }\n" +
            "  }")
    public Page<List<PostModel>> findTopQuestions(Pageable pageable);
}
