package com.natsuki.survey.service;

import com.natsuki.survey.dto.RegisteredUserDTO;
import com.natsuki.survey.model.RegisteredUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface RegisteredUserService extends UserDetailsService {

    RegisteredUser save(RegisteredUserDTO registeredUserDTO);

    RegisteredUserDTO getMemberInfo(String userId);

}
