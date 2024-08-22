package com.craft.stackoverflow.repository;

import com.craft.stackoverflow.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    public List<Tag> findByName(String name);
}
