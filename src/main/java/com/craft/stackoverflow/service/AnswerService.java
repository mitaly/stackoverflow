package com.craft.stackoverflow.service;

import com.craft.stackoverflow.dto.AnswerDto;
import com.craft.stackoverflow.entities.Answer;
import com.craft.stackoverflow.entities.Question;
import com.craft.stackoverflow.entities.User;
import com.craft.stackoverflow.exception.BusinessException;
import com.craft.stackoverflow.mapper.AnswerMapper;
import com.craft.stackoverflow.model.ErrorResponse;
import com.craft.stackoverflow.model.PostModel;
import com.craft.stackoverflow.repository.AnswerRepository;
import com.craft.stackoverflow.repository.PostSearchRepository;
import com.craft.stackoverflow.repository.QuestionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private AnswerMapper answerMapper;

    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    private SearchService searchService;

    @Transactional
    public AnswerDto create(AnswerDto answerDto, User user) {
        final Answer answer = answerMapper.answerDtoToAnswer(answerDto);
        answer.setUser(user);
        Optional<Question> question = questionRepository.findById(answerDto.getQuestionId());
        if (question.isPresent()) {
            answer.setQuestion(question.get());
            Answer savedAnswer = answerRepository.save(answer);

            // put answer in elastic search db
            PostModel answerModel = new PostModel(answer);
            searchService.saveAnswer(answerModel);

            return answerMapper.answerToAnswerDto(savedAnswer);
        } else {
            throw new BusinessException(HttpStatus.NOT_FOUND.value(), "question.not.found",
                    answerDto.getQuestionId());
        }
    }
}
