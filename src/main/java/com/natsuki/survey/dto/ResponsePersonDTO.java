package com.natsuki.survey.dto;

import com.natsuki.survey.common.Sex;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponsePersonDTO {

    private String personId;
    private String pw;
    private String age;
    @Enumerated(EnumType.STRING)
    private Sex sex;
}
