package com.craft.stackoverflow.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerDto {
    private Long id;
    private String body;
    private int upVotes;
    private int downVotes;
    private Long questionId;
}
