package com.natsuki.survey.repository;

import com.natsuki.survey.model.RegisteredUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegisteredUserRepository extends JpaRepository<RegisteredUser,String> {

    RegisteredUser findByUserId(String userID);

}
