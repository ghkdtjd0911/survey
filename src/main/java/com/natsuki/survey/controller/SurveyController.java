package com.natsuki.survey.controller;

import com.natsuki.survey.common.Authority;
import com.natsuki.survey.model.ResponsePersonInfo;
import com.natsuki.survey.dto.ResponseAnswersDTO;
import com.natsuki.survey.dto.ResponsePersonDTO;
import com.natsuki.survey.model.*;
import com.natsuki.survey.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.*;

@Controller
@Transactional
@RequestMapping("/survey")
public class SurveyController {

    private final HashMap<String, ArrayList<ResponseData>> tempMap = new HashMap<>();
    private final HashMap<String, ResponsePersonInfo> responsePersonInfoHashMap = new HashMap<>();

    @Autowired
    private SurveyService surveyService;

    @GetMapping("/{id}")
    public String register(
            @PathVariable("id") Long id,
            Model model) {
        Survey survey = surveyService.getSurveyById(id);
        if(survey.getAvailable()==true){
            model.addAttribute("survey", survey);
            ResponsePersonInfo responsePersonInfo = new ResponsePersonInfo();
            model.addAttribute("responsePersonInfo", responsePersonInfo);
            return "registerForm";
        }
       else{
            return "error";
        }
    }

    @PostMapping("/{id}")
    public String register(@PathVariable("id") Long id,
                           @ModelAttribute ResponsePersonDTO responsePersonDTO) {
        SurveyQuestion surveyQuestion = surveyService.findBySurveyQuestionId(1L, id);
        Long nextQ = surveyQuestion.getId();
        String personId = responsePersonDTO.getPersonId();
        Survey survey = surveyService.getSurveyById(id);
        try{
            if(surveyService.getResponsePersonInfoByPersonId(personId,id).getSurvey().equals(survey)){
                return "redirect:/survey/end";
            }

        }catch (NullPointerException e){
            e.printStackTrace();
            ResponsePersonInfo responsePersonInfo = new ResponsePersonInfo();
            responsePersonInfo.setPersonId(personId);
            responsePersonInfo.setAge(Integer.parseInt(responsePersonDTO.getAge()));
            responsePersonInfo.setPw(responsePersonDTO.getPw());
            responsePersonInfo.setSex(responsePersonDTO.getSex());
            responsePersonInfo.setAuthority(Authority.ROLE_USER);
            responsePersonInfo.setSurvey(survey);
            surveyService.saveResponsePersonInfo(responsePersonInfo);
            surveyService.saveSurveyResponse(id,responsePersonInfo,new ArrayList<>());
            responsePersonInfo.setSurveyResponse(surveyService.getSurveyResponseByResponsePersonInfo(responsePersonInfo));
            surveyService.saveResponsePersonInfo(responsePersonInfo);
            tempMap.put(personId, new ArrayList<>());
            responsePersonInfoHashMap.put(personId,responsePersonInfo);
            return "redirect:/survey/"+id+"/"+nextQ+"?personId=" + responsePersonDTO.getPersonId();
        }

        ResponsePersonInfo responsePersonInfo = new ResponsePersonInfo();
        responsePersonInfo.setPersonId(personId);
        responsePersonInfo.setAge(Integer.parseInt(responsePersonDTO.getAge()));
        responsePersonInfo.setPw(responsePersonDTO.getPw());
        responsePersonInfo.setSex(responsePersonDTO.getSex());
        responsePersonInfo.setAuthority(Authority.ROLE_USER);
        responsePersonInfo.setSurvey(survey);
        surveyService.saveResponsePersonInfo(responsePersonInfo);
        surveyService.saveSurveyResponse(id,responsePersonInfo,new ArrayList<>());
        responsePersonInfo.setSurveyResponse(surveyService.getSurveyResponseByResponsePersonInfo(responsePersonInfo));
        surveyService.saveResponsePersonInfo(responsePersonInfo);
        tempMap.put(personId, new ArrayList<>());
        responsePersonInfoHashMap.put(personId,responsePersonInfo);
        return "redirect:/survey/"+id+"/"+nextQ+"?personId=" + responsePersonDTO.getPersonId();
    }

    @GetMapping("/{id}/{qid}")
    public String questionForm(
            @PathVariable("id") Long id,
            @PathVariable("qid") Long qid,
            @RequestParam("personId") String personId,
            Model model) {
        if (tempMap.containsKey(personId)) {
            Survey survey = surveyService.getSurveyById(id);
            SurveyQuestion surveyQuestion = surveyService.findById(qid);
            Long rate = Math.round(surveyQuestion.getSurveyQuestionId().doubleValue() / survey.getSurveyQuestions().size() * 100);
            List<Answer> answers = surveyService.findAnswersByQuestionId(qid);
            ResponseAnswersDTO responseAnswersDTO = new ResponseAnswersDTO();
            model.addAttribute("survey", survey);
            model.addAttribute("surveyQuestion", surveyQuestion);
            model.addAttribute("answers", answers);
            model.addAttribute("rate", rate);
            model.addAttribute("personId", personId);
            model.addAttribute("responseAnswersDTO", responseAnswersDTO);

        }
        return "questionForm";
    }

    @PostMapping("/{id}/{qid}/response")
    public String responseSubmit(@PathVariable("id") Long id,
                                 @PathVariable("qid") Long qid,
                                 @RequestParam("personId") String personId,
                                 @ModelAttribute("responseAnswersDTO")@Valid ResponseAnswersDTO responseAnswersDTO
                                 ) {
        ArrayList list = tempMap.get(personId);
        ResponsePersonInfo responsePersonInfo = surveyService.getResponsePersonInfoByPersonId(personId,id);
        ResponseData responseData = new ResponseData();
        responseData.setPersonId(personId);
        responseData.setSurveyId(id);
        responseData.setSurveyQuestionId(qid);
        responseData.setSurveyQuestionString(surveyService.getSurveyQuestionById(qid).getQuestion());
        responseData.setSurveyResponse(surveyService.getSurveyResponseByResponsePersonInfo(responsePersonInfo));
        List<Answer> answerList = new ArrayList<>();
        Long nextQ = -1L;
        for (int i = 0; i < responseAnswersDTO.getAnswerList().size(); i++) {
            Long answerId = responseAnswersDTO.getAnswerList().get(i);

            answerList.add(surveyService.findAnswerByAnswerId(answerId));
            nextQ = surveyService.findAnswerByAnswerId(answerId).getNextQid();

        }
        responseData.setAnswerList(answerList);
        SurveyQuestion surveyQuestion = surveyService.findBySurveyQuestionId(nextQ,id);
        list.add(responseData);
        tempMap.put(personId, list);
        if (nextQ == -1) {
            ArrayList finalList = tempMap.get(personId);
            for (int i = 0; i < finalList.size(); i++) {
                ResponseData responseData1 = (ResponseData)finalList.get(i);
                surveyService.saveResponseData(responseData1);
            }
            surveyService.saveSurveyResponse(id,responsePersonInfoHashMap.get(personId),finalList);
            responsePersonInfoHashMap.remove(personId);
            tempMap.remove(personId);
            return "redirect:/survey/end";

        } else {
            Long questionId = surveyQuestion.getId();
            return "redirect:/survey/"+id+"/"+questionId+"?personId=" + personId;
        }

    }

    @GetMapping("/end")
    public String surveyEnd(){

        return "surveyEnd";
    }
}
