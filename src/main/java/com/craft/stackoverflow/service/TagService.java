package com.craft.stackoverflow.service;

import com.craft.stackoverflow.entities.Tag;
import com.craft.stackoverflow.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    public Tag create(Tag tag) {

    }
}
