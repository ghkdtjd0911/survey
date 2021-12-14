package com.natsuki.survey.dto;

import com.natsuki.survey.model.Answer;
import com.natsuki.survey.model.ResponseData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResultDTO {

    private Long qid;
    private HashMap<Long, AnswerResultDTO> answerMap = new HashMap<>();


}
