package com.craft.stackoverflow.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;
@Getter
@Setter
public class QuestionDTO {
    Long id;
    String title;
    String body;
    Long postedByUser;
    int upVotes;
    int downVotes;
    List<String> tags;
}
