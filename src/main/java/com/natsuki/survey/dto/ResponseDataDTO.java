package com.natsuki.survey.dto;

import com.natsuki.survey.model.Answer;
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