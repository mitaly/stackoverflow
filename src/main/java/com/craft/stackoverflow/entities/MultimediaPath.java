package com.craft.stackoverflow.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @ManyToOne
    @JoinColumn(name = "question_id")
    Question question;
    @ManyToOne
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
}
