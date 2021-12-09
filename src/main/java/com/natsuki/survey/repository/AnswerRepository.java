package com.natsuki.survey.repository;

import com.natsuki.survey.model.Answer;
import com.natsuki.survey.model.Survey;
import com.natsuki.survey.model.SurveyQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    List<Answer> findAllBySurveyQuestion(SurveyQuestion surveyQuestion);

    void deleteAllBySurvey(Survey byId);

    void deleteAllBySurveyQuestion(SurveyQuestion byId);
}
