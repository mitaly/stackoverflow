package com.craft.stackoverflow.service;

import com.craft.stackoverflow.dto.QuestionDTO;
import com.craft.stackoverflow.entities.*;
import com.craft.stackoverflow.repository.QuestionSearchRepository;
import com.craft.stackoverflow.exception.BusinessException;

import com.craft.stackoverflow.mapper.QuestionMapper;
import com.craft.stackoverflow.model.QuestionModel;
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

    @Autowired
    private VoteRepository voteRepository;

    @Transactional
    public QuestionDTO create(QuestionDTO questionDTO, MultipartFile file, long userId) {
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
        return questionMapper.questionToQuestionDTO(question);
    }

    public QuestionDTO getQuestionById(Long id) {
        Optional<Question> question = questionRepository.findById(id);
        if (question.isPresent()) {
            return questionMapper.questionToQuestionDTO(question.get());
        }
        throw new BusinessException(HttpStatus.NOT_FOUND.value(), "question.not.found", id);
    }

    public QuestionDTO upvoteQuestion(Long questionId, VoteType voteType, User user) {
        Optional<Question> question = questionRepository.findById(questionId);
        if (question.isEmpty()) {
            throw new BusinessException(HttpStatus.NOT_FOUND.value(), "question.not.found", questionId);
        }
        Question question1 = question.get();
        Optional<Vote> usersVote = question1.
                getVotes()
                .stream()
                .filter(upVote ->
                        Objects.equals(upVote.getUser().getId(), user.getId()))
                .findFirst();

        //user has not upvoted/downvoted the question.
        if (usersVote.isEmpty()) {
            Vote vote = new Vote();
            vote.setUser(user);
            vote.setVoteType(voteType);
            vote.setPost(question1);
            vote = voteRepository.save(vote);
            List<Vote> voteList = question1.getVotes();
            voteList.add(vote);
            question1.setVotes(voteList);
        } else {
            Vote vote = usersVote.get();
            if(vote.getVoteType() == voteType) {
                voteRepository.delete(vote);
                List<Vote> voteList = question1.getVotes();
                voteList.remove(vote);
            }else{
                List<Vote> voteList = question1.getVotes();
                voteList.remove(vote);
                vote.setVoteType(voteType);
                voteList.add(vote);
                question1.setVotes(voteList);
                voteRepository.save(vote);
            }
        }
        return questionMapper.questionToQuestionDTO(question1);


    }

    private void linkQuestionToUser(Question question, long userId) {
        Optional<User> user = userService.findById(userId);
        if (user.isEmpty()) {
            throw new BusinessException(HttpStatus.BAD_REQUEST.value(), "user.id.not.found", userId);
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
            multimediaPaths.add(multimediaPathService.create(new MultimediaPath(fileStoredPath, 'Q', question, null)));
            question.getMultimediaPaths().addAll(multimediaPaths);
        }
    }
}
