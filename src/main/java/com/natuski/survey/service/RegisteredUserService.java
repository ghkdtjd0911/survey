package com.natuski.survey.service;

import com.natuski.survey.dto.RegisteredUserDTO;
import com.natuski.survey.model.RegisteredUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface RegisteredUserService extends UserDetailsService {

    RegisteredUser save(RegisteredUserDTO registeredUserDTO);

    RegisteredUserDTO getMemberInfo(String userId);

}
