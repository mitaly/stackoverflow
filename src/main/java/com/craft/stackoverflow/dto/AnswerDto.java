package com.craft.stackoverflow.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDto {
    private Long id;
    @NotBlank
    @Size(max = 1000)
    private String body;
    @NotNull
    private Long questionId;
}
