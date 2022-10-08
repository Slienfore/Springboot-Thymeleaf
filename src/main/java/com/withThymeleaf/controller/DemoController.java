package com.withThymeleaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DemoController {

    @RequestMapping("/demo")
    public String fun(Model model) {
        model.addAttribute("msg", "Accept");

        return "demo";
    }

}