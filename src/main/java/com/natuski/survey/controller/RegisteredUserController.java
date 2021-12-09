package com.natuski.survey.controller;


import com.natuski.survey.dto.RegisteredUserDTO;
import com.natuski.survey.service.RegisteredUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
@Controller
public class RegisteredUserController {

    @Autowired
    private RegisteredUserService registeredUserService;

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new RegisteredUserDTO());

        return "registration";
    }

    @PostMapping("/registration")
    public String registerUserAccount(@ModelAttribute("user") RegisteredUserDTO registeredUserDTO) {
        registeredUserService.save(registeredUserDTO);

        return "redirect:/user/registration?success";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }


}
