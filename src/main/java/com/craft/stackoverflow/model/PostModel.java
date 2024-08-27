package com.craft.stackoverflow.model;

import com.craft.stackoverflow.entities.*;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Document(indexName = "post")
public class PostModel {
    // _id field in elasticsearch is a string
    @Id
    String id;
    String title;
    String body;
    Date createdAt;
    Date updatedAt;
    int votes;
    @Enumerated(EnumType.STRING)
    PostType postType;
    List<String> tags;
    String questionPostId;
    List<String> answerPostIds;

    public PostModel(Question question) {
        this.answerPostIds = question.getAnswers().stream().map(Answer::getId).map(String::valueOf).toList();
        this.body = question.getBody();
        this.id = String.valueOf(question.getId());
        this.updatedAt = question.getLastModifiedDate();
        this.tags = question.getTags().stream().map(Tag::getName).toList();
        this.createdAt = question.getCreatedDate();
        this.votes = question.getVotesCount();
        this.title = question.getTitle();
        this.postType = PostType.QUESTION;
    }

    public PostModel(Answer answer) {
        this.questionPostId = String.valueOf(answer.getQuestion().getId());
        this.body = answer.getBody();
        this.id = String.valueOf(answer.getId());
        this.updatedAt = answer.getLastModifiedDate();
        this.createdAt = answer.getCreatedDate();
        this.votes = answer.getVotesCount();
        this.postType = PostType.ANSWER;
    }
}
