package com.natsuki.survey.service;

import com.natsuki.survey.dto.RegisteredUserDTO;
import com.natsuki.survey.model.ResponsePersonInfo;
import com.natsuki.survey.dto.AnswerDTO;
import com.natsuki.survey.dto.QuestionDTO;
import com.natsuki.survey.model.*;
import com.natsuki.survey.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class SurveyServiceImpl implements SurveyService {

    @Autowired
    private SurveyRepository surveyRepository;
    @Autowired
    private SurveyQuestionRepository surveyQuestionRepository;
    @Autowired
    private ResponseRepository responseRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private ResponsePersonInfoRepository responsePersonInfoRepository;
    @Autowired
    private ResponseDataRepository responseDataRepository;
    @Autowired
    private RegisteredUserRepository registeredUserRepository;

    @Override
    public void createSurvey(String name,String user) {
        Survey survey = new Survey();
        RegisteredUser registeredUser = registeredUserRepository.findByUserId(user);
        survey.setSurveyName(name);
        survey.setRegisteredUser(registeredUser);
        survey.setQuestionsSize(0);
        survey.setResponseSize(0);
        surveyRepository.save(survey);
    }


    @Override
    public void deleteSurvey(Long id) {
        responseRepository.deleteAllBySurvey(surveyRepository.getById(id));
        responseDataRepository.deleteAllBySurveyId(id);
        answerRepository.deleteAllBySurvey(surveyRepository.getById(id));
        surveyQuestionRepository.deleteAllBySurvey(surveyRepository.getById(id));
        surveyRepository.delete(surveyRepository.getById(id));


    }

    @Override
    public void createQuestion(Long SurveyID, SurveyQuestion surveyQuestion,String principal) {
        Survey survey = surveyRepository.getById(SurveyID);
        surveyQuestion.setRegisteredUser(registeredUserRepository.findByUserId(principal));
        surveyQuestionRepository.save(surveyQuestion);
        surveyRepository.save(survey);

    }

    @Override
    public void modifyQuestion(Long QuestionID, QuestionDTO surveyQuestion) {
        SurveyQuestion surveyQuestion1 = surveyQuestionRepository.getById(QuestionID);
        surveyQuestion1.setQuestion(surveyQuestion.getQuestion());
        surveyQuestion1.setSurveyQuestionId(surveyQuestion.getSurveyQuestionId());
        surveyQuestionRepository.save(surveyQuestion1);
    }



    @Override
    public void deleteQuestion(Long id) {
        responseDataRepository.deleteAllByQuestion(surveyQuestionRepository.getById(id));
        answerRepository.deleteAllBySurveyQuestion(surveyQuestionRepository.getById(id));
        surveyQuestionRepository.deleteById(id);

    }

    @Override
    public SurveyQuestion findById(Long id) {
        return surveyQuestionRepository.getById(id);
    }

    @Override
    public List<Survey> getAllSurvey() {
        return surveyRepository.findAll();
    }

    @Override
    public Survey getSurveyById(Long id) {
        return surveyRepository.getById(id);
    }

    @Override
    public List<SurveyQuestion> getSurveyQuestionsBySurvey(Survey survey) {
        return surveyQuestionRepository.getSurveyQuestionsBySurvey(survey);
    }

    @Override
    public Integer getSurveySize(Survey survey) {

        return  surveyQuestionRepository.getSurveyQuestionsBySurvey(survey).size();
    }


    @Override
    public void createAnswer(Answer answer,String principal) {
        answer.setRegisteredUser(registeredUserRepository.findByUserId(principal));
        answerRepository.save(answer);


    }

    @Override
    public List<Answer> findAnswersByQuestionId(Long id) {
        SurveyQuestion surveyQuestion = surveyQuestionRepository.getById(id);
        return answerRepository.findAllBySurveyQuestion(surveyQuestion);
    }

    @Override
    public void deleteAnswer(Long aid) {
        answerRepository.deleteById(aid);
    }

    @Override
    public Answer findAnswerByAnswerId(Long aid) {
        return answerRepository.getById(aid);
    }

    @Override
    public void modifyAnswer(Long aid, AnswerDTO answerDTO) {
        Answer answer = answerRepository.getById(aid);
        answer.setAnswer(answerDTO.getAnswer());
        answer.setNextQid(answerDTO.getNextQid());
        answerRepository.save(answer);
    }

    @Override
    public SurveyQuestion findBySurveyQuestionId(Long SQId, Long id) {
        List<SurveyQuestion> list  = surveyQuestionRepository.findBySurveyQuestionId(SQId);
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getSurvey().getId().equals(id)){
                return list.get(i);
            }
        }
        return null;
    }

    @Override
    public void saveSurveyResponse(Long surveyId, ResponsePersonInfo responsePersonInfo, List<ResponseData> final_responseData) {
       SurveyResponse surveyResponse;
        if(responseRepository.existsByResponsePersonInfo(responsePersonInfo)){
            surveyResponse = responseRepository.findByResponsePersonInfo(responsePersonInfo);

        }else{
            surveyResponse = new SurveyResponse();
        }

        Date date = new Date();
        surveyResponse.setResponseData(final_responseData);
        surveyResponse.setResponsePersonInfo(responsePersonInfo);
        surveyResponse.setCreateAt(date);
        surveyResponse.setSurvey(surveyRepository.getById(surveyId));

        responseRepository.save(surveyResponse);
    }

    @Override
    public List<SurveyResponse> getAllSurveyResponse() {
        return responseRepository.findAll();
    }

    @Override
    public List<SurveyResponse> findBySurvey(Survey survey) {
        return responseRepository.findAllBySurvey(survey);
    }

    @Override
    public void saveResponsePersonInfo(ResponsePersonInfo responsePersonInfo) {
        responsePersonInfoRepository.save(responsePersonInfo);
    }

    @Override
    public void saveResponseData(ResponseData responseData) {
        responseDataRepository.save(responseData);
    }

    @Override
    public ResponsePersonInfo getResponsePersonInfoByPersonId(String personId) {
        return responsePersonInfoRepository.getByPersonId(personId);
    }

    @Override
    public SurveyResponse getSurveyResponseByResponsePersonInfo(ResponsePersonInfo responsePersonInfo) {
        return responseRepository.findByResponsePersonInfo(responsePersonInfo);
    }

    @Override
    public List<Survey> findByRegisteredUser(RegisteredUserDTO user) {
        RegisteredUser registeredUser = registeredUserRepository.findByUserId(user.getUserId());

        return  surveyRepository.findAllByRegisteredUser(registeredUser);
    }

    @Override
    public List<SurveyResponse> getAllSurveyResponseBySurvey(Survey survey) {
        return responseRepository.findAllBySurvey(survey);
    }

    @Override
    public List<ResponseData> getAllResponseDataBySurveyId(Long id) {
        return responseDataRepository.findAllBySurveyId(id);
    }

    @Override
    public SurveyQuestion getSurveyQuestionById(Long qid) {
        return surveyQuestionRepository.getById(qid);
    }

    @Override
    public List<Answer> getAllAnswers() {
        return answerRepository.findAll();
    }

}
