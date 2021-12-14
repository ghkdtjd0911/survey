package com.natsuki.survey.repository;

import com.natsuki.survey.model.Answer;
import com.natsuki.survey.model.ResponseData;
import com.natsuki.survey.model.Survey;
import com.natsuki.survey.model.SurveyQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResponseDataRepository extends JpaRepository<ResponseData,Long> {
    List<ResponseData> findAllBySurveyId(Long id);

    void deleteAllBySurveyId(Long byId);

    void deleteAllByQuestion(SurveyQuestion byId);

}
