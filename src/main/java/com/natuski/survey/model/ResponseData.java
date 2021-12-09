package com.natuski.survey.model;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

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
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Answer> answerList;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private SurveyResponse surveyResponse;

}
