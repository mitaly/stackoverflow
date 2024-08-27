package com.craft.stackoverflow.service;


import com.craft.stackoverflow.exception.BusinessException;
import com.craft.stackoverflow.repository.PostSearchRepository;
import com.craft.stackoverflow.model.PostModel;
import com.craft.stackoverflow.util.AppConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SearchService {
    @Value("${search.default.page.size}")
    private int defaultPageSize;

    @Autowired
    private PostSearchRepository searchRepository;

    private Pageable getPageConfiguration(Integer page, Integer size){
        return PageRequest.of(page, size == null ? defaultPageSize : size, Sort.Direction.DESC,
                "votes", "updatedAt");
    }

    public Page<List<PostModel>> search(Integer page, Integer size, String query, String tags) {
        Pageable pageable = getPageConfiguration(page, size);
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

    public Page<List<PostModel>> getTopQuestions(Integer page, Integer size) {
        return searchRepository.findTopQuestions(getPageConfiguration(page, size));
    }

    public void updateVoteForId(int voteCount, Long id) {
        Optional<PostModel> postModel = searchRepository.findById(String.valueOf(id));
        if(postModel.isEmpty()) {
            throw new BusinessException(HttpStatus.NOT_FOUND.value(), "post.not.found", id);
        }
        postModel.get().setVotes(voteCount);
        searchRepository.save(postModel.get());
    }

    public void saveAnswer(PostModel answerModel) {
        searchRepository.save(answerModel);
        Optional<PostModel> questionModel = searchRepository.findById(answerModel.getQuestionPostId());
        if (questionModel.isEmpty()) {
            throw new BusinessException(HttpStatus.NOT_FOUND.value(), "question.not.found", answerModel.getQuestionPostId())
                    ;
        }
        questionModel.get().getAnswerPostIds().add(answerModel.getId());
        searchRepository.save(questionModel.get());
    }

    public void saveQuestion(PostModel questionModel) {
        searchRepository.save(questionModel);
    }

    private List<String> parseTagList(String tags) {
        if(tags.isBlank()) return List.of();
        return List.of(tags.split(AppConstant.TAG_SEPARATOR));
    }
}
