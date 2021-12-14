package com.natsuki.survey.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SurveyQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long surveyQuestionId;
    private String question;
    private Boolean multiAnswers;
    @OneToMany(mappedBy = "Answer")
    private List<Answer> answer;
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
    @JoinColumn(name = "survey_survey_questions")
    private Survey survey;
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private RegisteredUser registeredUser;
    @ManyToMany(mappedBy = "",fetch = FetchType.LAZY)
    private List<SurveyResponse> surveyResponses;
}

