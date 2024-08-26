package com.craft.stackoverflow.mapper;

import com.craft.stackoverflow.dto.QuestionDTO;
import com.craft.stackoverflow.dto.VoteDto;
import com.craft.stackoverflow.entities.Question;
import com.craft.stackoverflow.entities.Vote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {QuestionMapper.class})
public interface VoteMapper {

    @Mapping(source = "Post", target="votes", qualifiedByName = "getTags")
    int voteToInteger(List<Vote> votes);



}
