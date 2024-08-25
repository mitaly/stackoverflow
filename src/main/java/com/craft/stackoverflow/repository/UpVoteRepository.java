package com.craft.stackoverflow.repository;

import com.craft.stackoverflow.entities.UpVote;
import com.craft.stackoverflow.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpVoteRepository  extends JpaRepository<UpVote, Long> {

}
