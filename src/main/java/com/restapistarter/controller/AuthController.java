package com.restapistarter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restapistarter.security.AuthRequest;
import com.restapistarter.security.JwtAuthFilter;
import com.restapistarter.security.JwtService;

@RestController
@RequestMapping("/auth/")
public class AuthController {

    private final JwtService jwtService;

    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/generateToken")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        return jwtService.authenticateAndGetToken(authRequest);
    }
}
