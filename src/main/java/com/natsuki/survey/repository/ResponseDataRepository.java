package com.natsuki.survey.repository;

import com.natsuki.survey.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResponseDataRepository extends JpaRepository<ResponseData,Long> {
    List<ResponseData> findAllBySurveyId(Long id);

    void deleteAllBySurveyId(Long byId);

    void deleteAllByQuestion(SurveyQuestion byId);

    List<ResponseData> findAllBySurveyResponse(SurveyResponse surveyResponse);

    List<ResponseData> findAllByAnswerList_Id(Long dataId);
}
