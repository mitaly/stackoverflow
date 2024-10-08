package com.craft.stackoverflow.repository;

import com.craft.stackoverflow.entities.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository  extends JpaRepository<Vote, Long> {

}
