package com.craft.stackoverflow.service;

import com.craft.stackoverflow.entities.Question;
import com.craft.stackoverflow.entities.Tag;
import com.craft.stackoverflow.exception.AppException;
import com.craft.stackoverflow.repository.TagRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    public Tag create(Tag tag) {
        return tagRepository.save(tag);
    }

    @Transactional
    public List<Tag> saveIfNotPresent(List<String> tags, Question question) {
        List<Tag> savedTags = new ArrayList<>();
        try {
            for (String tagname : tags) {
                List<Tag> tag = tagRepository.findByName(tagname);
                if (tag == null || tag.isEmpty()) {
                    Tag newTag = new Tag(tagname);
                    newTag.getQuestions().add(question);
                    savedTags.add(tagRepository.save(newTag));
                } else {
                    Tag existingTag = tag.get(0);
                    existingTag.getQuestions().add(question);
                    savedTags.add(existingTag);
                }
            }
            return savedTags;
        } catch (Exception e) {
            throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "tags.save" , tags);
        }
    }
}
