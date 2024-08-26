package com.craft.stackoverflow.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;
@Getter
@Setter
public class QuestionDTO {
    private Long id;
    @NotBlank
    @Size(max = 200)
    private String title;
    @NotBlank
    @Size(max = 1000)
    private String body;
    private List<String> tags;
}
