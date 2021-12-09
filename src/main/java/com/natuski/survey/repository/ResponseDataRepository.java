package com.natuski.survey.repository;

import com.natuski.survey.model.ResponseData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponseDataRepository extends JpaRepository<ResponseData,Long> {
}
