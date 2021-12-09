package com.natuski.survey.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Survey {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String surveyName;
    private Integer questionsSize;
    private Integer responseSize;
    @OneToMany(targetEntity = SurveyQuestion.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "survey_survey_questions")
    private List<SurveyQuestion>surveyQuestions;
    @OneToMany(mappedBy = "")
    private List<SurveyResponse> surveyResponses;
    @ManyToOne
    private RegisteredUser registeredUser;
}
