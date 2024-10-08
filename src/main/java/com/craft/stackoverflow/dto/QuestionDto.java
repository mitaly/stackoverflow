package com.craft.stackoverflow.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QuestionDto {
    private Long id;
    @NotBlank
    @Size(max = 200)
    private String title;
    @NotBlank
    @Size(max = 1000)
    private String body;
    private int votes;
    private List<String> tags;
}
