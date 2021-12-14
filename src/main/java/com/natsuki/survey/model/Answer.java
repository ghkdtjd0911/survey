package com.natsuki.survey.model;

import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Answer {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String Answer;
    private Long nextQid;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotFound(
            action = NotFoundAction.IGNORE)
    private SurveyQuestion surveyQuestion;
    @ManyToOne(fetch = FetchType.LAZY)
    private Survey survey;

    @ManyToMany(mappedBy = "",fetch = FetchType.LAZY)
    private List<SurveyResponse> surveyResponses;

    @ManyToOne(fetch = FetchType.LAZY)
    private RegisteredUser registeredUser;


}
