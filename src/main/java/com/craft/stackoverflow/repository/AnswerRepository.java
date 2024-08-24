package com.craft.stackoverflow.repository;

import com.craft.stackoverflow.entities.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository  extends JpaRepository<Answer, Long> {

}
