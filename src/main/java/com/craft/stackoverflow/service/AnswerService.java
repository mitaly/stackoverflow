package com.craft.stackoverflow.service;

import com.craft.stackoverflow.dto.AnswerDto;
import com.craft.stackoverflow.entities.Answer;
import com.craft.stackoverflow.entities.MultimediaPath;
import com.craft.stackoverflow.entities.Question;
import com.craft.stackoverflow.exception.BusinessException;
import com.craft.stackoverflow.mapper.AnswerMapper;
import com.craft.stackoverflow.model.ErrorResponse;
import com.craft.stackoverflow.repository.AnswerRepository;
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
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private AnswerMapper answerMapper;
    @Autowired
    private FileUploadService fileUploadService;
    @Autowired QuestionRepository questionRepository;
    @Autowired
    private MultimediaPathService multimediaPathService;

    @Transactional
    public Answer create(AnswerDto answerDto, MultipartFile file) {
        final Answer answer = answerMapper.answerDtoToAnswer(answerDto);
        Optional<Question> question = questionRepository.findById(answerDto.getQuestionId());
        if(question.isPresent()){
            answer.setQuestion(question.get());
            Answer savedAnswer = answerRepository.save(answer);
            uploadMultimedia(file, savedAnswer);
            return savedAnswer;
        }else {
            throw new BusinessException(HttpStatus.BAD_REQUEST.value(), "question.not.found", answerDto.getQuestionId());
        }


    }

    private void uploadMultimedia(MultipartFile file, Answer answer) {
        if (file != null) {
            List<String> paths = new ArrayList<>();

            String fileStoredPath = fileUploadService.uploadFile(file);
            paths.add(fileStoredPath);

            List<MultimediaPath> multimediaPaths = new ArrayList<>();
            multimediaPaths.add(multimediaPathService.create(new MultimediaPath(fileStoredPath,
                    'A', null, answer)));
            answer.getMultimediaPaths().addAll(multimediaPaths);
        }
    }
}
