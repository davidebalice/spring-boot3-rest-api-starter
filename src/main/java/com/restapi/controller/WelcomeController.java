package com.restapi.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.restapi.config.CustomException;

@RestController
@RequestMapping("/api/v1/")
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
