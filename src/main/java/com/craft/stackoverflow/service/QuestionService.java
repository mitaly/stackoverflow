package com.craft.stackoverflow.service;

import com.craft.stackoverflow.dto.QuestionDTO;
import com.craft.stackoverflow.entities.Question;
import com.craft.stackoverflow.mapper.QuestionMapper;
import com.craft.stackoverflow.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private QuestionMapper questionMapper;

    public Question create(QuestionDTO questionDTO) {
        Question question = questionMapper.questionDTOToQuestion(questionDTO);
        return questionRepository.save(question);
    }
}
