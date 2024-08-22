package com.craft.stackoverflow.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Tag {
    @Id
    @GeneratedValue
    Long id;
    String name;
    @ManyToMany(mappedBy = "tags")
    Set<Question> questions = new HashSet<>();
}
