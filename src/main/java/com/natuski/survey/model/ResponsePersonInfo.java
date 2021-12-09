package com.natuski.survey.model;

import com.natuski.survey.common.Authority;
import com.natuski.survey.common.Sex;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ResponsePersonInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String personId;
    private String pw;
    private Integer age;
    @Enumerated(EnumType.STRING)
    private Sex sex;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @OneToOne
    SurveyResponse surveyResponse;


}
