package com.craft.stackoverflow.service;


import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    public String search(String query, String tags) {
        List<String> tagList = List.of(tags.split(","));
        System.out.println("tagList" + tagList);
        return query;
    }

}
