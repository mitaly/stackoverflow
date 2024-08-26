package com.craft.stackoverflow.mapper;


import com.craft.stackoverflow.dto.AnswerDto;
import com.craft.stackoverflow.dto.QuestionDTO;
import com.craft.stackoverflow.entities.Answer;
import com.craft.stackoverflow.entities.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface AnswerMapper {
    @Mapping(ignore = true, target = "votes")
    Answer answerDtoToAnswer(AnswerDto answerDto);

    @Mapping(target="votes", expression = "java(answer.getVotesCount())")
    @Mapping(target="questionId", expression = "java(answer.getUser().getId())")
    @Mapping(target="userId", expression = "java(answer.getUser().getId())")
    AnswerDto answerToAnswerDto(Answer answer);


}
