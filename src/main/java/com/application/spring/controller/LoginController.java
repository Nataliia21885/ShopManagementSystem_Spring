package com.application.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public ModelAndView login(@RequestParam(name = "message", required = false, defaultValue = "")
                                      String message, String logout, String error) {
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("message", message);
        if (logout != null) {
            modelAndView.addObject("message", "You have been logged out successfully!");
        }
        if (error != null) {
            modelAndView.addObject("message", "You have invalid username or password!");
        }
        return modelAndView;
    }
}
