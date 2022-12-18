package com.application.spring.controller;

import com.application.spring.exception.UserAlreadyExistException;
import com.application.spring.exception.ValidationException;
import com.application.spring.model.entity.Users;
import com.application.spring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/registrationForm")
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;

    @GetMapping
    public ModelAndView registration(@RequestParam(name = "message", required = false, defaultValue = "") String message) {
        ModelAndView model = new ModelAndView("registrationForm");
        model.addObject("message", message);
        return model;
    }

    @PostMapping
    public RedirectView registration(@Validated @ModelAttribute("users") Users users) throws ValidationException {
        RedirectView redirectView = new RedirectView("/login");
        try {
            userService.registrationUser(users);
        } catch (UserAlreadyExistException e) {
            redirectView.setUrl("/registrationForm");
            redirectView.addStaticAttribute("message", "Email already in use!");
            return redirectView;
        }
        redirectView.addStaticAttribute("message", "You're successfully registered!");
        return redirectView;
    }
}
