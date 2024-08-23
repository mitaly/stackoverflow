package com.craft.stackoverflow.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;
@Getter
@Setter
public class QuestionDTO {
    private Long id;
    private String title;
    private String body;
    private int upVotes;
    private int downVotes;
    private List<String> tags;
}
