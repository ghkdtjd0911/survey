package com.natuski.survey.model;

import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.ArrayList;
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
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "survey_survey_questions")
    private Survey survey;

}
