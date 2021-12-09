package com.natsuki.survey.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class AnswerDTO {
    private Long id;
    @NotEmpty
    private String Answer;
    @NotNull
    private Long nextQid;
}
