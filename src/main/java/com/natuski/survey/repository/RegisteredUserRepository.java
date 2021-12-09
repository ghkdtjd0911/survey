package com.natuski.survey.repository;

import com.natuski.survey.model.RegisteredUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegisteredUserRepository extends JpaRepository<RegisteredUser,String> {

    RegisteredUser findByUserId(String userID);

}
