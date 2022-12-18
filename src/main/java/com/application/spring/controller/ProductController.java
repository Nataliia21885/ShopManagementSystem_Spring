package com.application.spring.controller;


import com.application.spring.exception.ValidationException;
import com.application.spring.model.dto.ProductDto;
import com.application.spring.service.ProducerService;
import com.application.spring.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/product")
@RestController
@Slf4j
public class ProductController {

    private final ProductService productService;
    private final ProducerService producerService;

    @Secured(value = "ROLE_ADMIN")
    @GetMapping("/save")
    public ModelAndView saveForm() {
        ModelAndView model = new ModelAndView("product/save");
        model.addObject("products", productService.listAll());
        model.addObject("producers", producerService.listAll());
        return model;
    }

    @Secured(value = "ROLE_ADMIN")
    @PostMapping("/save")
    public RedirectView saveProducts(@Validated @ModelAttribute(name = "productDto") ProductDto productDto) throws ValidationException {
        log.info("Handling save products: " + productDto);
        productService.save(productDto);
        RedirectView redirectView = new RedirectView("/product/findAll");
        return redirectView;
    }

    @GetMapping("/findAll")
    public ModelAndView findAllProducts() {
        log.info("Handling find all products request");
        ModelAndView model = new ModelAndView("product/findAll");
        model.getModelMap().addAttribute("products", productService.listAll());
        return model;
    }

    @GetMapping("/findById")
    public ModelAndView findById(@RequestParam(value = "id", required = false, defaultValue = "") String id) {
        log.info("Handling find by id request: " + id);
        ModelAndView model = new ModelAndView("product/findById");
        if (!(id.isEmpty() || id.equals(""))) {
            model.getModelMap().addAttribute("products", productService.getById(UUID.fromString(id)));
        }
        return model;
    }

    @Secured(value = "ROLE_ADMIN")
    @GetMapping("/update/{id}")
    public ModelAndView updateProductById(@PathVariable("id") UUID id) {
        ModelAndView model = new ModelAndView("product/editForm");
        ProductDto productDto = productService.getById(id);
        model.addObject("producers", producerService.listAll());
        model.addObject("product", productDto);
        return model;
    }

    @Secured(value = "ROLE_ADMIN")
    @GetMapping("/delete/{id}")
    public RedirectView deleteProductByIdForm(@PathVariable("id") UUID id) {
        productService.deleteById(id);
        return new RedirectView("/product/findAll");
    }
}
