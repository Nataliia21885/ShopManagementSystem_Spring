package com.application.spring.controller;

import com.application.spring.exception.ValidationException;
import com.application.spring.model.dto.UsersDto;
import com.application.spring.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
@Slf4j
public class UserController {

    private final UserService userService;

    @Secured(value = "ROLE_ADMIN")
    @GetMapping("/save")
    public ModelAndView saveForm() {
        ModelAndView model = new ModelAndView("user/save");
        model.addObject("users", userService.listAll());
        return model;
    }

    @Secured(value = "ROLE_ADMIN")
    @PostMapping("/save")
    public RedirectView saveUsers(@Validated @ModelAttribute(name = "userDto") UsersDto usersDto) throws ValidationException {
        log.info("Handling save users: " + usersDto);
        userService.save(usersDto);
        RedirectView redirectView = new RedirectView("/user/findAll");
        return redirectView;
    }

    @Secured(value = "ROLE_ADMIN")
    @GetMapping("/findAll")
    public ModelAndView findAllUsers() {
        log.info("Handling find all users request");
        ModelAndView model = new ModelAndView("user/findAll");
        model.getModelMap().addAttribute("users", userService.listAll());
        return model;
    }

    @Secured(value = "ROLE_ADMIN")
    @GetMapping("/findById")
    public ModelAndView findById(@RequestParam(value = "id", required = false, defaultValue = "") String id) {
        log.info("Handling find by id request: " + id);
        ModelAndView model = new ModelAndView("user/findById");
        if (!(id.isEmpty() || id.equals(""))) {
            model.getModelMap().addAttribute("users", userService.getById(UUID.fromString(id)));
        }
        return model;
    }

    @Secured(value = "ROLE_ADMIN")
    @GetMapping("/update/{id}")
    public ModelAndView updateUserById(@PathVariable("id") UUID id) {
        ModelAndView model = new ModelAndView("user/editForm");
        UsersDto usersDto = userService.getById(id);
        model.addObject("user", usersDto);
        return model;
    }

    @Secured(value = "ROLE_ADMIN")
    @GetMapping("/delete/{id}")
    public RedirectView deleteUserByIdForm(@PathVariable("id") UUID id) {
        userService.deleteById(id);
        return new RedirectView("/user/findAll");
    }
}
