
package com.craft.stackoverflow.mapper;

import com.craft.stackoverflow.dto.QuestionDto;
import com.craft.stackoverflow.entities.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    @Mapping(ignore = true, target = "tags")
    @Mapping(ignore = true, target = "votes")
    @Mapping(ignore = true, target = "user")
    Question questionDTOToQuestion(QuestionDto questionDTO);

    @Mapping(source = "question", target="votes", qualifiedByName = "getVotes")
    @Mapping(source = "question", target="tags", qualifiedByName = "getTags")
    QuestionDto questionToQuestionDTO(Question question);

    @Named("getVotes")
    default int votes(Question question){
        return question.getVotesCount();
    }

    @Named("getTags")
    default List<String> map(Question question){
        return question.getTags().stream().map(
                tag -> tag.getName()
        ).toList();
    }


}
