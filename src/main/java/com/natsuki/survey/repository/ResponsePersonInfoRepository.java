package com.natsuki.survey.repository;

import com.natsuki.survey.model.ResponsePersonInfo;
import com.natsuki.survey.model.Survey;
import com.natsuki.survey.model.SurveyResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResponsePersonInfoRepository extends JpaRepository<ResponsePersonInfo,String> {

    boolean existsById(String id);

    ResponsePersonInfo getById(Long id);

    ResponsePersonInfo getByPersonId(String personId);

    ResponsePersonInfo findBySurveyResponse(SurveyResponse surveyResponse);

    void deleteById(Long responseUserId);

    List<ResponsePersonInfo> findAllByPersonId(String personId);
}
