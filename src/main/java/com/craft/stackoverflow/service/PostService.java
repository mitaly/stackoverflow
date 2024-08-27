package com.craft.stackoverflow.service;

import com.craft.stackoverflow.dto.PostDto;
import com.craft.stackoverflow.entities.Post;
import com.craft.stackoverflow.exception.BusinessException;
import com.craft.stackoverflow.mapper.PostMapper;
import com.craft.stackoverflow.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostMapper postMapper;

    public PostDto getPost(Long postId){
        Optional<Post> postOptional = postRepository.findById(postId);
        if(postOptional.isPresent()){
            return postMapper.postToPostDto(postOptional.get());
        }
        throw new BusinessException(HttpStatus.NOT_FOUND.value(), "post.not.found", postId);
    }

}
