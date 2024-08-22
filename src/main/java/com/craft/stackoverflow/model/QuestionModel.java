package com.craft.stackoverflow.model;

import com.craft.stackoverflow.entities.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Document(indexName = "question")
public class QuestionModel {

    @Id
    Long id;
    String title;
    String body;
    Timestamp createdAt;
    User user;
    int upVotes;
    int downVotes;
    List<Answer> answers = new ArrayList<>();
    List<Comment> comments = new ArrayList<>();
    List<Tag> tags = new ArrayList<>();
    List<MultimediaPath> multimediaPaths = new ArrayList<>();

    public QuestionModel(Question question) {
        this.answers = question.getAnswers();
        this.body = question.getBody();
        this.id = question.getId();
        this.comments = question.getComments();
        this.tags = question.getTags();
        this.createdAt = question.getCreatedAt();
        this.downVotes = question.getDownVotes();
        this.upVotes = question.getUpVotes();
        this.title = question.getTitle();
        this.multimediaPaths = question.getMultimediaPaths();
        this.user = question.getUser();
    }
}
