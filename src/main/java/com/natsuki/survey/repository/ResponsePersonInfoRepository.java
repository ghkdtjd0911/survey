package com.natsuki.survey.repository;

import com.natsuki.survey.model.ResponsePersonInfo;
import com.natsuki.survey.model.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponsePersonInfoRepository extends JpaRepository<ResponsePersonInfo,String> {

    boolean existsById(String id);

    ResponsePersonInfo getByPersonId(String personId);

}
