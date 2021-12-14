package com.natsuki.survey.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String personId;
    private Long surveyId;
    private Long surveyQuestionId;
    private String surveyQuestionString;
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Answer> answerList;

    @ManyToOne(fetch = FetchType.LAZY)
    private SurveyQuestion question;

    @ManyToOne(fetch = FetchType.LAZY)
    private Survey surveyEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    private SurveyResponse surveyResponse;

}
