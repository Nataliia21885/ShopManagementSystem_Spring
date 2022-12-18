package com.application.spring.controller;

import com.application.spring.exception.ValidationException;
import com.application.spring.model.dto.ProducerDto;
import com.application.spring.service.ProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/producer")
@RestController
@Slf4j
public class ProducerController {

    private final ProducerService producerService;

    @Secured(value = "ROLE_ADMIN")
    @GetMapping("/save")
    public ModelAndView saveForm() {
        ModelAndView model = new ModelAndView("producer/save");
        model.addObject("producers", producerService.listAll());
        return model;
    }

    @Secured(value = "ROLE_ADMIN")
    @PostMapping("/save")
    public RedirectView saveProducers(@Validated @ModelAttribute(name = "producerDto") ProducerDto producerDto) throws ValidationException {
        log.info("Handling save producers: " + producerDto);
        producerService.save(producerDto);
        RedirectView redirectView = new RedirectView("/producer/findAll");
        return redirectView;
    }

    @GetMapping("/findAll")
    public ModelAndView findAllProducers() {
        log.info("Handling find all producers request");
        ModelAndView model = new ModelAndView("producer/findAll");
        model.getModelMap().addAttribute("producers", producerService.listAll());
        return model;
    }

    @GetMapping("/findById")
    public ModelAndView findById(@RequestParam(value = "id", required = false, defaultValue = "") String id) {
        log.info("Handling find by id request: " + id);
        ModelAndView model = new ModelAndView("producer/findById");
        if (!(id.isEmpty() || id.equals(""))) {
            model.getModelMap().addAttribute("producers", producerService.getById(UUID.fromString(id)));
        }
        return model;
    }

    @Secured(value = "ROLE_ADMIN")
    @GetMapping("/update/{id}")
    public ModelAndView updateProducerById(@PathVariable("id") UUID id) {
        ModelAndView model = new ModelAndView("producer/editForm");
        ProducerDto producerDto = producerService.getById(id);
        model.addObject("producer", producerDto);
        return model;
    }

    @Secured(value = "ROLE_ADMIN")
    @GetMapping("/delete/{id}")
    public RedirectView deleteProducerByIdForm(@PathVariable("id") UUID id) {
        producerService.deleteById(id);
        return new RedirectView("/producer/findAll");
    }
}
