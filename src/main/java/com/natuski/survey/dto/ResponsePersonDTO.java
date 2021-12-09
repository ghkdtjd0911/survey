package com.natuski.survey.dto;

import com.natuski.survey.common.Sex;
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
    private Integer age;
    @Enumerated(EnumType.STRING)
    private Sex sex;
}
