package com.restapistarter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//import com.springboot.catalogue.CustomException;

@Controller
public class WelcomeController {
    @ResponseBody
    @GetMapping("/test")
    public String test() {
        return "test controller";
    }

    @ResponseBody
    @GetMapping("/testlogin")
    public String testlogin() {
        return "test controller";
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/error")
    public String error() {
        // throw new CustomException(500, "Si è verificato un errore personalizzato.");
        return "error";
    }

    @GetMapping("/error2")
    public String error2() {
        // throw new CustomException(500, "Si è verificato un errore personalizzato.");
        return "error";
    }
}
