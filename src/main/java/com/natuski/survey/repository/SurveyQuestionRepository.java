package com.natuski.survey.repository;

import com.natuski.survey.model.Survey;
import com.natuski.survey.model.SurveyQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface SurveyQuestionRepository extends JpaRepository<SurveyQuestion, Long> {
    ArrayList<SurveyQuestion>getSurveyQuestionsBySurvey(Survey survey);

    void deleteAllBySurvey(Survey survey);

    List<SurveyQuestion> findBySurveyQuestionId(Long SurveyQuestionId);
}
