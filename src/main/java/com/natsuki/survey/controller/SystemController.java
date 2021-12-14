package com.natsuki.survey.controller;

import com.natsuki.survey.dto.*;
import com.natsuki.survey.model.*;
import com.natsuki.survey.service.RegisteredUserService;
import com.natsuki.survey.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.*;

@Controller
@RequestMapping("/system")
public class SystemController {

    @Autowired
    private SurveyService surveyService;
    @Autowired
    private RegisteredUserService registeredUserService;

    @GetMapping()
    public String systemHome(Model model, Principal principal) {
        RegisteredUserDTO user = registeredUserService.getMemberInfo(principal.getName());
        List<Survey> surveys = surveyService.findByRegisteredUser(user);
        for (int i = 0; i < surveys.size(); i++) {
            surveys.get(i).setQuestionsSize(surveyService.getSurveySize(surveys.get(i)));
            List<SurveyResponse> surveyResponses = surveyService.findBySurvey(surveys.get(i));
            surveys.get(i).setResponseSize(surveyResponses.size());
        }
        model.addAttribute("Survey", surveys);
        model.addAttribute("loginMember", principal.getName());
        return "home";
    }


    @GetMapping("/new")
    public String createSurvey(Model model) {
        Survey Survey = new Survey();
        model.addAttribute("Survey", Survey);

        return "createSurvey";
    }

    @PostMapping("/new")
    public String createSurvey(@Valid @ModelAttribute() SurveyDTO surveyDTO,Principal principal) {
        String surveyName = surveyDTO.getSurveyName();
        surveyService.createSurvey(surveyName,principal.getName());
        return "redirect:/system";
    }

    @GetMapping("/delete/{id}")
    public String deleteSurvey(@PathVariable Long id) {
        surveyService.deleteSurvey(id);
        return "redirect:/system";
    }

    @GetMapping("/{id}/questions")
    public String queryQuestions(@PathVariable Long id, Model model,Principal principal) {
        Survey survey = surveyService.getSurveyById(id);

        List<SurveyQuestion> surveyQuestions = surveyService.getSurveyQuestionsBySurvey(survey);
        model.addAttribute("surveyQuestions", surveyQuestions);
        model.addAttribute("Survey", survey);
        model.addAttribute("bySqid", Comparator.comparing(SurveyQuestion::getSurveyQuestionId));
        Boolean query_condition = surveyService.getAllResponseDataBySurveyId(survey.getId()).size()==0;
        model.addAttribute("query_condition",query_condition);
        model.addAttribute("loginMember", principal.getName());
        return "questions";
    }

    @GetMapping("/{id}/questions/new")
    public String createQuestion(@PathVariable Long id, Model model) {
        Survey Survey = surveyService.getSurveyById(id);
        model.addAttribute("Survey", Survey);
        SurveyQuestion surveyQuestion = new SurveyQuestion();
        model.addAttribute("surveyQuestion", surveyQuestion);

        return "createQuestion";
    }

    @PostMapping("/{id}/questions/new")
    public String createQuestion(@PathVariable Long id, @Valid @ModelAttribute() QuestionDTO questionDTO,Principal principal) {
        SurveyQuestion surveyQuestion = new SurveyQuestion();
        String question = questionDTO.getQuestion();
        surveyQuestion.setQuestion(question);
        surveyQuestion.setSurveyQuestionId(questionDTO.getSurveyQuestionId());
        surveyQuestion.setSurvey(surveyService.getSurveyById(id));
        surveyQuestion.setMultiAnswers(questionDTO.getMultiAnswers());
        surveyService.createQuestion(id, surveyQuestion,principal.getName());
        return "redirect:/system/{id}/questions";
    }

    @PostMapping("/{id}/questions/modify/{qid}")
    public String modifyQuestion(
            @PathVariable Long qid,
            @Valid @ModelAttribute() QuestionDTO questionDTO) {
        surveyService.modifyQuestion(qid, questionDTO);
        return "redirect:/system/{id}/questions";
    }

    @GetMapping("/{id}/questions/modify/{qid}")
    public String modifyQuestion(@PathVariable Long id,
                                 @PathVariable Long qid,
                                 Model model) {
        Survey survey = surveyService.getSurveyById(id);
        SurveyQuestion surveyQuestion = surveyService.findById(qid);
        model.addAttribute("survey", survey);
        model.addAttribute("surveyQuestion", surveyQuestion);
        return "modifyQuestion";
    }

    @GetMapping("/{id}/questions/delete/{qid}")
    public String deleteQuestion(@PathVariable Long id,
                                 @PathVariable Long qid) {
        surveyService.deleteQuestion(qid);
        return "redirect:/system/{id}/questions";
    }

    @GetMapping("/{id}/questions/{qid}/answers/new")
    public String createAnswers(@PathVariable Long id,
                                @PathVariable Long qid,
                                Model model) {
        Survey Survey = surveyService.getSurveyById(id);
        model.addAttribute("Survey", Survey);
        SurveyQuestion surveyQuestion = surveyService.findById(qid);
        model.addAttribute("surveyQuestion", surveyQuestion);
        Answer answer = new Answer();
        model.addAttribute("answer", answer);

        return "createAnswers";
    }

    @PostMapping("/{id}/questions/{qid}/answers/new")
    public String createAnswers(@PathVariable Long id,
                                @PathVariable Long qid,
                                @Valid @ModelAttribute() AnswerDTO answerDTO,Principal principal) {
        Answer answer = new Answer();
        String answerDetail = answerDTO.getAnswer();
        Long answerNextQid = answerDTO.getNextQid();
        answer.setAnswer(answerDetail);
        answer.setNextQid(answerNextQid);
        answer.setSurveyQuestion(surveyService.findById(qid));
        answer.setSurvey(surveyService.getSurveyById(id));
        surveyService.createAnswer(answer,principal.getName());
        return "redirect:/system/{id}/questions/{qid}/answers";
    }

    @GetMapping("/{id}/questions/{qid}/answers")
    public String queryAnswers(@PathVariable Long id,
                               @PathVariable Long qid,
                               Model model,Principal principal) {
        Survey Survey = surveyService.getSurveyById(id);
        model.addAttribute("Survey", Survey);
        SurveyQuestion surveyQuestion = surveyService.findById(qid);
        model.addAttribute("surveyQuestion", surveyQuestion);
        List<Answer> answers = surveyService.findAnswersByQuestionId(qid);
        model.addAttribute("answers", answers);
        Boolean query_condition = surveyService.getAllResponseDataBySurveyId(Survey.getId()).size()==0;
        model.addAttribute("query_condition",query_condition);
        model.addAttribute("byNqid", Comparator.comparing(Answer::getNextQid));
        model.addAttribute("loginMember", principal.getName());

        return "answers";
    }

    @GetMapping("/{id}/questions/delete/{qid}/answers/{aid}")
    public String deleteAnswer(
            @PathVariable Long aid) {
        surveyService.deleteAnswer(aid);
        return "redirect:/system/{id}/questions/{qid}/answers";
    }

    @GetMapping("/{id}/questions/modify/{qid}/answers/{aid}")
    public String modifyAnswers(@PathVariable Long id,
                                @PathVariable Long qid,
                                @PathVariable Long aid,
                                Model model) {
        Survey Survey = surveyService.getSurveyById(id);
        model.addAttribute("survey", Survey);
        SurveyQuestion surveyQuestion = surveyService.findById(qid);
        model.addAttribute("surveyQuestion", surveyQuestion);
        Answer answer = surveyService.findAnswerByAnswerId(aid);
        model.addAttribute("answer", answer);

        return "modifyAnswer";
    }

    @PostMapping("/{id}/questions/modify/{qid}/answers/{aid}")
    public String modifyAnswers(@PathVariable Long id,
                                @PathVariable Long qid,
                                @PathVariable Long aid,
                                @Valid @ModelAttribute() AnswerDTO answerDTO) {
        surveyService.modifyAnswer(aid, answerDTO);
        return "redirect:/system/{id}/questions/{qid}/answers";
    }

    @GetMapping("/results/{id}")
    public String surveyResults(@PathVariable Long id,
                                Model model,Principal principal) {
        Survey survey = surveyService.getSurveyById(id);
        List<SurveyResponse> surveyResponses = surveyService.getAllSurveyResponseBySurvey(survey);
        List<ResponseData> responseData = surveyService.getAllResponseDataBySurveyId(survey.getId());
        List<Answer> answerList = surveyService.getAllAnswers();
        HashSet<AnswerResultDTO> answerResultDTOS = new HashSet<>();
        for (int k = 0; k < answerList.size(); k++) {
            if(answerList.get(k).getSurvey().equals(survey)){
            AnswerResultDTO answerResultDTO = new AnswerResultDTO();
            answerResultDTO.setAnswerNum(answerList.get(k).getId());
            answerResultDTO.setAnswerString(answerList.get(k).getAnswer());
            for (int j = 0; j < responseData.size(); j++) {
                if (responseData.get(j).getSurveyQuestionId().equals(answerList.get(k).getSurveyQuestion().getId())) {
                    if(responseData.get(j).getAnswerList().contains(answerList.get(k))){
                        answerResultDTO.setAnswerCount(answerResultDTO.getAnswerCount() + 1);
                    }
                        answerResultDTO.setQuestionID(responseData.get(j).getSurveyQuestionId());
                        answerResultDTO.setQuestionString(responseData.get(j).getSurveyQuestionString());
                        double original = answerResultDTO.getAnswerCount().doubleValue() / surveyResponses.size();
                        double rounded = Math.round(original * 100);
                        answerResultDTO.setPercentage(rounded);
                    }
                }
            answerResultDTOS.add(answerResultDTO);
        }
            }



        model.addAttribute("answerResultDTOS", answerResultDTOS);
        model.addAttribute("loginMember", principal.getName());
        model.addAttribute("byNqid", Comparator.comparing(AnswerResultDTO::getQuestionID));

        return "surveyResults";
    }

}