package com.natuski.survey.dto;

import com.natuski.survey.model.Answer;
import com.natuski.survey.model.Survey;
import com.natuski.survey.model.SurveyQuestion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ResponseDataDTO {

    private Long surveyId;
    private Long surveyQuestionId;
    private List<Answer> answers;

}