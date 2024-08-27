package com.craft.stackoverflow.service;

import com.craft.stackoverflow.dto.QuestionDto;
import com.craft.stackoverflow.entities.*;
import com.craft.stackoverflow.repository.PostSearchRepository;
import com.craft.stackoverflow.exception.BusinessException;

import com.craft.stackoverflow.mapper.QuestionMapper;
import com.craft.stackoverflow.model.PostModel;
import com.craft.stackoverflow.repository.QuestionRepository;
import com.craft.stackoverflow.repository.VoteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private SearchService searchService;
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

    @Autowired
    private VoteRepository voteRepository;

    @Transactional
    public QuestionDto create(QuestionDto questionDTO, MultipartFile file, User user) {
        // mapping QuestionDto to Question entity
        Question question = questionMapper.questionDTOToQuestion(questionDTO);
        // persist the question
        question = questionRepository.save(question);

        // upload media files and link media files to question
        uploadMultimedia(file, question);

        // link tags to question
        saveTags(questionDTO, question);

        //linking question and user
        question.setUser(user);

        // put question in elastic search
        PostModel questionModel = new PostModel(question);
        searchService.saveQuestion(questionModel);

        return questionMapper.questionToQuestionDTO(question);
    }

    public QuestionDto getQuestionById(Long id) {
        Optional<Question> question = questionRepository.findById(id);
        if (question.isPresent()) {
            return questionMapper.questionToQuestionDTO(question.get());
        }
        throw new BusinessException(HttpStatus.NOT_FOUND.value(), "question.not.found", id);
    }

    private void saveTags(QuestionDto questionDTO, Question question) {
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
            multimediaPaths.add(multimediaPathService.create(new MultimediaPath(fileStoredPath, 'Q', question, null)));
            question.getMultimediaPaths().addAll(multimediaPaths);
        }
    }
}
