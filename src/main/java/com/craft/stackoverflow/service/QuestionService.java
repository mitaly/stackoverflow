package com.craft.stackoverflow.service;

import com.craft.stackoverflow.dto.QuestionDTO;
import com.craft.stackoverflow.repository.QuestionSearchRepository;
import com.craft.stackoverflow.entities.MultimediaPath;
import com.craft.stackoverflow.entities.Question;
import com.craft.stackoverflow.entities.Tag;
import com.craft.stackoverflow.entities.User;
import com.craft.stackoverflow.exception.BusinessException;

import com.craft.stackoverflow.mapper.QuestionMapper;
import com.craft.stackoverflow.model.QuestionModel;
import com.craft.stackoverflow.repository.QuestionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private QuestionSearchRepository questionElasticSearchRepository;
    @Autowired
    private TagService tagService;
    @Autowired
    private UserService userService;
    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private MultimediaPathService multimediaPathService;
    @Autowired
    private QuestionMapper questionMapper;

    @Transactional
    public Question create(QuestionDTO questionDTO, MultipartFile file, long userId) {
        // mapping QuestionDto to Question entity
        Question question = questionMapper.questionDTOToQuestion(questionDTO);
        question = questionRepository.save(question);

        uploadMultimedia(file, question);

        saveTags(questionDTO, question);

        //linking question and user
        linkQuestionToUser(question, userId);

        QuestionModel questionModel = new QuestionModel(question);
        //TODO: handle elastic search error and rollback
        questionElasticSearchRepository.save(questionModel);
        return question;
    }

    private void linkQuestionToUser(Question question, long userId) {
        Optional<User> user = userService.findById(userId);
        if (user.isEmpty()) {
            throw new BusinessException(HttpStatus.BAD_REQUEST.value(), "user.id.not.found",
                    userId);
        }
        question.setUser(user.get());
        user.get().getQuestions().add(question);
    }

    private void saveTags(QuestionDTO questionDTO, Question question) {
        if (questionDTO.getTags() != null && !questionDTO.getTags().isEmpty()) {
            List<Tag> savedTags = tagService.saveIfNotPresent(questionDTO.getTags(), question);
            question.getTags().addAll(savedTags);
        }
    }

    private void uploadMultimedia(MultipartFile file, Question question) {
        if (file != null) {
            List<String> paths = new ArrayList<>();

            String fileStoredPath = fileUploadService.uploadFile(file);
            paths.add(fileStoredPath);

            List<MultimediaPath> multimediaPaths = new ArrayList<>();
            multimediaPaths.add(multimediaPathService.create(new MultimediaPath(fileStoredPath,
                    'Q', question, null)));
            question.getMultimediaPaths().addAll(multimediaPaths);
        }
    }
}
