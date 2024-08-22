package com.craft.stackoverflow.repository;

import com.craft.stackoverflow.entities.MultimediaPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MultimediaPathRepository extends JpaRepository<MultimediaPath, Long> {
}
