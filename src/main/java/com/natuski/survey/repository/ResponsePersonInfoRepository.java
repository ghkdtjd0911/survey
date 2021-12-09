package com.natuski.survey.repository;

import com.natuski.survey.model.ResponsePersonInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponsePersonInfoRepository extends JpaRepository<ResponsePersonInfo,String> {

    boolean existsById(String id);

    ResponsePersonInfo getByPersonId(String personId);
}
