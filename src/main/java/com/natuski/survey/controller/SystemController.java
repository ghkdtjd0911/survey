package com.natuski.survey.controller;

import com.natuski.survey.dto.AnswerDTO;
import com.natuski.survey.dto.QuestionDTO;
import com.natuski.survey.dto.RegisteredUserDTO;
import com.natuski.survey.dto.SurveyDTO;
import com.natuski.survey.model.*;
import com.natuski.survey.repository.SurveyRepository;
import com.natuski.survey.service.RegisteredUserService;
import com.natuski.survey.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
    public String queryQuestions(@PathVariable Long id, Model model) {
        Survey survey = surveyService.getSurveyById(id);
        List<SurveyQuestion> surveyQuestions = surveyService.getSurveyQuestionsBySurvey(survey);
        model.addAttribute("surveyQuestions", surveyQuestions);
        model.addAttribute("Survey", survey);
        model.addAttribute("bySqid", Comparator.comparing(SurveyQuestion::getSurveyQuestionId));
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
    public String createQuestion(@PathVariable Long id, @Valid @ModelAttribute() QuestionDTO questionDTO) {
        SurveyQuestion surveyQuestion = new SurveyQuestion();
        String question = questionDTO.getQuestion();
        surveyQuestion.setQuestion(question);
        surveyQuestion.setSurveyQuestionId(questionDTO.getSurveyQuestionId());
        surveyQuestion.setSurvey(surveyService.getSurveyById(id));
        surveyQuestion.setMultiAnswers(questionDTO.getMultiAnswers());
        surveyService.createQuestion(id, surveyQuestion);
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
                                @Valid @ModelAttribute() AnswerDTO answerDTO) {
        Answer answer = new Answer();
        String answerDetail = answerDTO.getAnswer();
        Long answerNextQid = answerDTO.getNextQid();
        answer.setAnswer(answerDetail);
        answer.setNextQid(answerNextQid);
        answer.setSurveyQuestion(surveyService.findById(qid));
        answer.setSurvey(surveyService.getSurveyById(id));
        surveyService.createAnswer(answer);
        return "redirect:/system/{id}/questions/{qid}/answers";
    }

    @GetMapping("/{id}/questions/{qid}/answers")
    public String queryAnswers(@PathVariable Long id,
                               @PathVariable Long qid,
                               Model model) {
        Survey Survey = surveyService.getSurveyById(id);
        model.addAttribute("Survey", Survey);
        SurveyQuestion surveyQuestion = surveyService.findById(qid);
        model.addAttribute("surveyQuestion", surveyQuestion);
        List<Answer> answers = surveyService.findAnswersByQuestionId(qid);
        model.addAttribute("answers", answers);
        model.addAttribute("byNqid", Comparator.comparing(Answer::getNextQid));

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

}