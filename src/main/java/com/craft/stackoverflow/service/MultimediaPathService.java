package com.craft.stackoverflow.service;

import com.craft.stackoverflow.entities.MultimediaPath;
import com.craft.stackoverflow.repository.MultimediaPathRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MultimediaPathService {
    @Autowired
    private MultimediaPathRepository multimediaPathRepository;

    public MultimediaPath create(MultimediaPath multimediaPath) {
        return multimediaPathRepository.save(multimediaPath);
    }
}
