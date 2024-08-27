package com.craft.stackoverflow.mapper;

import com.craft.stackoverflow.dto.PostResponseDto;
import com.craft.stackoverflow.entities.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(target="votes", expression = "java(post.getVotesCount())")
    PostResponseDto postToPostDto(Post post);
}
