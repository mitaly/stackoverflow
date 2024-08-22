package com.craft.stackoverflow.service;

import com.craft.stackoverflow.dto.QuestionDTO;
import com.craft.stackoverflow.entities.Question;
import com.craft.stackoverflow.entities.Tag;
import com.craft.stackoverflow.entities.User;
import com.craft.stackoverflow.mapper.QuestionMapper;
import com.craft.stackoverflow.repository.QuestionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private TagService tagService;
    @Autowired
    private UserService userService;

    //    private QuestionMapper questionMapper;
//    @Autowired
//    public QuestionService(QuestionMapper questionMapper) {
//        this.questionMapper = questionMapper;
//    }
    @Transactional

    public Question create(QuestionDTO questionDTO) {
//        Question question = questionMapper.questionDTOToQuestion(questionDTO);
        Question question = new Question();
        question.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        question.setTitle(questionDTO.getTitle());
        question.setBody(questionDTO.getBody());
        question = questionRepository.save(question);

        List<Tag> savedTags = tagService.saveIfNotPresent(questionDTO.getTags(), question);
        question.getTags().addAll(savedTags);

        // set user
        Optional<User> user = userService.findById(questionDTO.getPostedByUser());
        if (user.isEmpty()) {
//            throw new Exception("s");
            return null;
        }
        question.setUser(user.get());
        user.get().getQuestions().add(question);
        return question;
    }
}
