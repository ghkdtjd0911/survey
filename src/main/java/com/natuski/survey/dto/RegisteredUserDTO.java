package com.natuski.survey.dto;


import com.natuski.survey.common.Authority;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;


@Data
@Component
public class RegisteredUserDTO {

    private String userId;
    private String pw;
    @Enumerated(EnumType.STRING)
    private Authority authority;
}
