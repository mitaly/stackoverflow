
package com.craft.stackoverflow.mapper;

import com.craft.stackoverflow.dto.QuestionDTO;
import com.craft.stackoverflow.entities.Question;
import com.craft.stackoverflow.entities.Tag;
import com.craft.stackoverflow.entities.UpVote;
import com.craft.stackoverflow.entities.Vote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    @Mapping(ignore = true, target = "tags")
    @Mapping(ignore = true, target = "upVotes")
    @Mapping(ignore = true, target = "user")
    Question questionDTOToQuestion(QuestionDTO questionDTO);

    @Mapping(source = "question", target="upVotes", qualifiedByName = "getUpVotes")
    @Mapping(source = "question", target="tags", qualifiedByName = "getTags")
    QuestionDTO questionToQuestionDTO(Question question);

    @Named("getUpVotes")
    default int upVotes(Question question){
        int counter = 0;
        for(UpVote upVote: question.getUpVotes()){
            if(upVote.getVote() == Vote.UPVOTE){
                counter++;
            }else{
                counter--;
            }
        }
        return counter;
    }

    @Named("getTags")
    default List<String> map(Question question){
        return question.getTags().stream().map(
                tag -> tag.getName()
        ).toList();
    }


}
