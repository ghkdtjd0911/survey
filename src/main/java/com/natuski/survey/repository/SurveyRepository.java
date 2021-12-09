package com.natuski.survey.repository;

import com.natuski.survey.model.RegisteredUser;
import com.natuski.survey.model.Survey;
import com.natuski.survey.model.SurveyResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SurveyRepository extends JpaRepository<Survey, Long> {

    Optional<Survey> findById(Long id);


    List<Survey> findAllByRegisteredUser(RegisteredUser registeredUser);
}
