package com.craft.stackoverflow.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MultimediaPath {
    @Id
    @GeneratedValue
    Long id;
    String multimediaPath;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_id")
    Answer answer;
    //takes 'Q', 'A'
    char belongsTo;

    public MultimediaPath(String multimediaPath, char belongsTo, Question question, Answer answer) {
        this.multimediaPath = multimediaPath;
        this.belongsTo = belongsTo;
        this.question = question;
        this.answer = answer;
    }


    @JsonIgnore
    public Question getQuestion() {
        return question;
    }

    @JsonIgnore
    public Answer getAnswer() {
        return answer;
    }
}
