package com.craft.stackoverflow.mapper;


import com.craft.stackoverflow.dto.AnswerDto;
import com.craft.stackoverflow.dto.QuestionDTO;
import com.craft.stackoverflow.entities.Answer;
import com.craft.stackoverflow.entities.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AnswerMapper {
    Answer answerDtoToAnswer(AnswerDto answerDto);
}
