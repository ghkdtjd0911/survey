package com.natsuki.survey.repository;

import com.natsuki.survey.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResponseRepository extends JpaRepository<SurveyResponse, Long> {

    List<SurveyResponse> findAllBySurvey(Survey survey);


    boolean existsByResponsePersonInfo(ResponsePersonInfo responsePersonInfo);

    SurveyResponse findByResponsePersonInfo(ResponsePersonInfo responsePersonInfo);

    void deleteAllBySurvey(Survey byId);

    List<SurveyResponse> findByAnswers_Id(Long byId);

    SurveyResponse findByResponseData(ResponseData responseData);
}
