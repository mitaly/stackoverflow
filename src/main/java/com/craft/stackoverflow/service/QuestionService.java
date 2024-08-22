package com.craft.stackoverflow.service;

import com.craft.stackoverflow.dto.QuestionDTO;
import com.craft.stackoverflow.entities.MultimediaPath;
import com.craft.stackoverflow.entities.Question;
import com.craft.stackoverflow.entities.Tag;
import com.craft.stackoverflow.entities.User;
import com.craft.stackoverflow.exception.AppException;

import com.craft.stackoverflow.repository.QuestionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.ArrayList;
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
    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private MultimediaPathService multimediaPathService;


    //    private QuestionMapper questionMapper;
//    @Autowired
//    public QuestionService(QuestionMapper questionMapper) {
//        this.questionMapper = questionMapper;
//    }
    @Transactional
    public Question create(QuestionDTO questionDTO, MultipartFile file) {
//        Question question = questionMapper.questionDTOToQuestion(questionDTO);
        Question question = new Question();
        question.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        question.setTitle(questionDTO.getTitle());
        question.setBody(questionDTO.getBody());
        question = questionRepository.save(question);

        uploadMultimedia(file, question);

        saveTags(questionDTO, question);

        // set user
        saveUser(questionDTO, question);
        return question;
    }

    private void saveUser(QuestionDTO questionDTO, Question question) {
        Optional<User> user = userService.findById(questionDTO.getPostedByUser());
        if (user.isEmpty()) {
            throw new AppException(HttpStatus.BAD_REQUEST.value(), "user.id.not.found",
                    questionDTO.getPostedByUser());
        }
        question.setUser(user.get());
        user.get().getQuestions().add(question);
    }

    private void saveTags(QuestionDTO questionDTO, Question question) {
        List<Tag> savedTags = tagService.saveIfNotPresent(questionDTO.getTags(), question);
        question.getTags().addAll(savedTags);
    }

    private void uploadMultimedia(MultipartFile file, Question question) {
        List<String> paths = new ArrayList<>();

        String fileStoredPath = fileUploadService.uploadFile(file);
        paths.add(fileStoredPath);

        List<MultimediaPath> multimediaPaths = new ArrayList<>();

        multimediaPaths.add(multimediaPathService.create(new MultimediaPath(fileStoredPath,
                'Q', question, null)));
        question.getMultimediaPaths().addAll(multimediaPaths);
    }
}
