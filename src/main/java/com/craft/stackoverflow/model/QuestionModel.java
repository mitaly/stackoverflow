package com.craft.stackoverflow.model;

import com.craft.stackoverflow.entities.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Document(indexName = "question")
public class QuestionModel {

    @Id
    Long id;
    String title;
    String body;
    Date createdAt;
    Date updatedAt;
    int votes;
    List<Long> answersId = new ArrayList<>();
    //TODO: Handle comment in elastic search
//    List<Comment> comments = new ArrayList<>();
    List<String> tags = new ArrayList<>();

    public QuestionModel(){};
    public QuestionModel(Question question) {
        this.answersId = question.getAnswers().stream().map(answer ->
             answer.getId()
        ).toList();
        this.body = question.getBody();
        this.id = question.getId();
        this.updatedAt = question.getLastModifiedDate();
        this.tags = question.getTags().stream().map(t -> t.getName()).toList();
        this.createdAt = question.getCreatedDate();
        this.votes = question.getVotes().size();
        this.title = question.getTitle();
    }
}
