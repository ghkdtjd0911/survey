package com.natsuki.survey.service;

import com.natsuki.survey.dto.RegisteredUserDTO;
import com.natsuki.survey.model.ResponsePersonInfo;
import com.natsuki.survey.dto.AnswerDTO;
import com.natsuki.survey.dto.QuestionDTO;
import com.natsuki.survey.model.*;

import java.util.List;

public interface SurveyService {

    void createSurvey(String surveyName,String user);
    void deleteSurvey(Long id);

    void createQuestion(Long SurveyID, SurveyQuestion surveyQuestion, String principal);
    void modifyQuestion(Long QuestionID, QuestionDTO surveyQuestion);
    void deleteQuestion(Long id);

    SurveyQuestion findById(Long id);

    List<Survey> getAllSurvey();

    Survey getSurveyById(Long id);

    List<SurveyQuestion> getSurveyQuestionsBySurvey(Survey survey);

    Integer getSurveySize(Survey survey);


    void createAnswer(Answer answer,String principal);

    List<Answer> findAnswersByQuestionId(Long id);

    void deleteAnswer(Long aid);

    Answer findAnswerByAnswerId(Long aid);

 void modifyAnswer(Long aid, AnswerDTO answerDTO);

    SurveyQuestion findBySurveyQuestionId(Long SQId, Long id);

    void saveSurveyResponse(Long surveyId, ResponsePersonInfo responsePersonInfo, List<ResponseData> final_responseData);

    List<SurveyResponse> getAllSurveyResponse();

    List<SurveyResponse> findBySurvey(Survey survey);

    void saveResponsePersonInfo(ResponsePersonInfo responsePersonInfo);

    void saveResponseData(ResponseData responseData);

    ResponsePersonInfo getResponsePersonInfoByPersonId(String personId);

    SurveyResponse getSurveyResponseByResponsePersonInfo(ResponsePersonInfo responsePersonInfo);

    List<Survey> findByRegisteredUser(RegisteredUserDTO user);

    List<SurveyResponse> getAllSurveyResponseBySurvey(Survey survey);

    List<ResponseData> getAllResponseDataBySurveyId(Long id);

    SurveyQuestion getSurveyQuestionById(Long qid);

    List<Answer> getAllAnswers();

    List<ResponsePersonInfo> findResponsePersonInfoByDataId(Long id);

    ResponsePersonInfo getResponsePersonInfoById(Long id);

    List<ResponseData> getAllResponseDataBySurveyResponse(SurveyResponse surveyResponse);

    void deleteResponseUserById(Long responseUserId);
}
