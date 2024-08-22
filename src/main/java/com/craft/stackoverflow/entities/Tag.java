package com.craft.stackoverflow.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tag {
    @Id
    @GeneratedValue
    Long id;
    @Column(unique = true)
    String name;
    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    Set<Question> questions = new HashSet<>();

    public Tag(String name) {
        this.name = name;
    }

    @JsonIgnore
    public Set<Question> getQuestions() {
        return questions;
    }
}
