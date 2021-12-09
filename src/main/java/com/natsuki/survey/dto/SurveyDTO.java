package com.natsuki.survey.dto;

import com.natsuki.survey.model.RegisteredUser;
import com.natsuki.survey.model.SurveyQuestion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class SurveyDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotEmpty
    private String surveyName;
    private HashMap<Long, SurveyQuestion> surveyQuestions;

    @ManyToOne
    private RegisteredUser registeredUser;
}
