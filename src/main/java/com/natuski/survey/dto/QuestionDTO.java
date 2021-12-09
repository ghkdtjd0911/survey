package com.natuski.survey.dto;

import com.natuski.survey.model.Answer;
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
public class QuestionDTO {

    @NotNull
    private Long SurveyQuestionId;
    @NotEmpty
    private String question;
    @NotNull
    private Boolean multiAnswers;
}
