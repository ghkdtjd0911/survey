package com.natsuki.survey.repository;

import com.natsuki.survey.model.RegisteredUser;
import com.natsuki.survey.model.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SurveyRepository extends JpaRepository<Survey, Long> {

    Optional<Survey> findById(Long id);


    List<Survey> findAllByRegisteredUser(RegisteredUser registeredUser);
}
