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

    @Mapping(source = "answer", target="votes", qualifiedByName = "getVotes")
    Answer answerDtoToAnswer(AnswerDto answerDto);

    @Named("getVotes")
    default int votes(Answer answer){
        return answer.getVotesCount();
    }
}
