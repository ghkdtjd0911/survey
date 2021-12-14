package com.natsuki.survey.model;

import com.natsuki.survey.common.Authority;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class RegisteredUser {
    @Id
    private String userId;
    private String pw;
    @Enumerated(EnumType.STRING)
    private Authority authority;

    @OneToMany(targetEntity = Survey.class,fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Survey> survey;

    @OneToMany(targetEntity = SurveyQuestion.class,fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<SurveyQuestion> surveyQuestions;

    @OneToMany(targetEntity = Answer.class,fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Answer> answers;

    @Builder
    public RegisteredUser(String userId, String pw, Authority authority) {
        this.authority = authority;
        this.pw = pw;
        this.userId = userId;
    }

}
