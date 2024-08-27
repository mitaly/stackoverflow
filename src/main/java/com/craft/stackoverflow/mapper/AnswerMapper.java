package com.craft.stackoverflow.mapper;


import com.craft.stackoverflow.dto.AnswerDto;
import com.craft.stackoverflow.entities.Answer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AnswerMapper {
    @Mapping(ignore = true, target = "votes")
    Answer answerDtoToAnswer(AnswerDto answerDto);

    @Mapping(target = "votes", expression = "java(answer.getVotesCount())")
    @Mapping(target = "questionId", expression = "java(answer.getUser().getId())")
    @Mapping(target = "userId", expression = "java(answer.getUser().getId())")
    AnswerDto answerToAnswerDto(Answer answer);
}
