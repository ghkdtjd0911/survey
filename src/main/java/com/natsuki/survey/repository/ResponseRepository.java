package com.natsuki.survey.repository;

import com.natsuki.survey.model.ResponsePersonInfo;
import com.natsuki.survey.model.Survey;
import com.natsuki.survey.model.SurveyResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResponseRepository extends JpaRepository<SurveyResponse, Long> {

    List<SurveyResponse> findAllBySurvey(Survey survey);


    boolean existsByResponsePersonInfo(ResponsePersonInfo responsePersonInfo);

    SurveyResponse findByResponsePersonInfo(ResponsePersonInfo responsePersonInfo);

    void deleteAllBySurvey(Survey byId);
}
