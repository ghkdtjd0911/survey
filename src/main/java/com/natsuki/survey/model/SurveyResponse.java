package com.natsuki.survey.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SurveyResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date createAt;
    @OneToOne(mappedBy = "",cascade = CascadeType.MERGE)
    private ResponsePersonInfo responsePersonInfo;
    @OneToMany(mappedBy = "")
    private List<ResponseData> responseData;
    @ManyToOne(fetch = FetchType.LAZY,targetEntity = Survey.class)
    private Survey survey;

}