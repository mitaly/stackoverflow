package com.craft.stackoverflow.mapper;

import com.craft.stackoverflow.dto.QuestionDTO;
import com.craft.stackoverflow.entities.Question;
import org.mapstruct.Mapper;

@Mapper
public interface QuestionMapper {
    Question questionDTOToQuestion(QuestionDTO questionDTO);
}
