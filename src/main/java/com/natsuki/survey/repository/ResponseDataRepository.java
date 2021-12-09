package com.natsuki.survey.repository;

import com.natsuki.survey.model.ResponseData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponseDataRepository extends JpaRepository<ResponseData,Long> {
}
