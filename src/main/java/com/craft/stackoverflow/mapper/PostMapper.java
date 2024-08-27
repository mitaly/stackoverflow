package com.craft.stackoverflow.mapper;

import com.craft.stackoverflow.dto.AnswerDto;
import com.craft.stackoverflow.dto.PostDto;
import com.craft.stackoverflow.entities.Answer;
import com.craft.stackoverflow.entities.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(target="votes", expression = "java(post.getVotesCount())")
    PostDto postToPostDto(Post post);
}
