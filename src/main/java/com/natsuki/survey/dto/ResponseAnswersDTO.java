package com.natsuki.survey.dto;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseAnswersDTO {

    List<Long> answerList;
}
