package com.natuski.survey.repository;

import com.natuski.survey.model.Answer;
import com.natuski.survey.model.Survey;
import com.natuski.survey.model.SurveyQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    List<Answer> findAllBySurveyQuestion(SurveyQuestion surveyQuestion);

    void deleteAllBySurvey(Survey byId);

    void deleteAllBySurveyQuestion(SurveyQuestion byId);
}
