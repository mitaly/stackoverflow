package com.craft.stackoverflow.dto;

import java.util.Set;

public class QuestionDTO {
    Long id;
    String title;
    String body;
    Long postedByUser;
    int upVotes;
    int downVotes;
    Set<String> tags;
}
