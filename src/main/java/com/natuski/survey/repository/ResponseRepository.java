package com.natuski.survey.repository;

import com.natuski.survey.model.ResponsePersonInfo;
import com.natuski.survey.model.Survey;
import com.natuski.survey.model.SurveyResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResponseRepository extends JpaRepository<SurveyResponse, Long> {

    List<SurveyResponse> findAllBySurvey(Survey survey);


    boolean existsByResponsePersonInfo(ResponsePersonInfo responsePersonInfo);

    SurveyResponse findByResponsePersonInfo(ResponsePersonInfo responsePersonInfo);
}
