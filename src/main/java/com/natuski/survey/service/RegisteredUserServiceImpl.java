package com.natuski.survey.service;

import com.natuski.survey.common.Authority;
import com.natuski.survey.dto.RegisteredUserDTO;
import com.natuski.survey.model.RegisteredUser;
import com.natuski.survey.repository.RegisteredUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;

@Service
@Transactional
public class RegisteredUserServiceImpl implements RegisteredUserService{

    @Autowired
    private RegisteredUserRepository registeredUserRepository;


    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public RegisteredUser save(RegisteredUserDTO registeredUserDTO) {
        RegisteredUser registeredUser = RegisteredUser.builder().userId(registeredUserDTO.getUserId())
                .authority(Authority.ROLE_ADMIN)
                .pw(passwordEncoder.encode(registeredUserDTO.getPw()))
                .userId(registeredUserDTO.getUserId())
                .build();
        registeredUserRepository.save(registeredUser);

        return registeredUser;
    }

    @Override
    public RegisteredUserDTO getMemberInfo(String userId) {

        RegisteredUserDTO registeredUserDTO = new RegisteredUserDTO();
        try {
            RegisteredUser registeredUser = registeredUserRepository.findByUserId(userId);
            registeredUserDTO.setUserId(registeredUser.getUserId());
            registeredUserDTO.setAuthority(registeredUser.getAuthority());
        } catch (RuntimeException e) {
            throw new RuntimeException("유저 정보가 없습니다.");
        }
        return registeredUserDTO;

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        RegisteredUser registeredUser = registeredUserRepository.findByUserId(username);
        String id = registeredUser.getUserId();
        String pw = registeredUser.getPw();
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(registeredUser.getAuthority().toString()));
        User user = new User(id,pw,authorities);

        return user;
    }
}
