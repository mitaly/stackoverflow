
package com.craft.stackoverflow.mapper;

import com.craft.stackoverflow.dto.QuestionDTO;
import com.craft.stackoverflow.entities.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Service;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    @Mapping(ignore = true, target = "tags")
    Question questionDTOToQuestion(QuestionDTO questionDTO);
}
