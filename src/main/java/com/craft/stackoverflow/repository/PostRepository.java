package com.craft.stackoverflow.repository;

import com.craft.stackoverflow.entities.Answer;
import com.craft.stackoverflow.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
