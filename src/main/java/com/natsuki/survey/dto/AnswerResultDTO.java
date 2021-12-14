package com.natsuki.survey.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerResultDTO {

    private Long answerNum;
    private Long questionID;
    private String questionString;
    private String answerString;
    private Integer answerCount = 0;
    private Double percentage;
}
