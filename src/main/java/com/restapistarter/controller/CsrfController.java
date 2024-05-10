package com.restapistarter.controller;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CsrfController {

    @GetMapping("/csrf")
    public String showCsrfPage(CsrfToken token, Model model) {
        model.addAttribute("_csrf", token);
        return "csrf";
    }
}
