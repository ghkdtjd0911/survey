package com.natuski.survey.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenDTO {

    private String grantType;
    private String accessToken;
    private Long accessTokenExpiresIn;
    private String refreshToken;


}
