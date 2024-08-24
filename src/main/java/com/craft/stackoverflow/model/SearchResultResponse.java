package com.craft.stackoverflow.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchResultResponse {

    public String title;
    public String text;
    public Long questionId;
    public Long answerId;
    public Long upvotes;
}
