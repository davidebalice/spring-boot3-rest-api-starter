package com.restapistarter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.restapistarter.config.CustomException;

@Controller
public class WelcomeController {

 

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @ResponseBody
    @GetMapping("/testlogin")
    public String testlogin() {
        return "test protected page";
    }

    @GetMapping("/error")
    public String error() {
        throw new CustomException(500, "Personalized error");
    }
}
