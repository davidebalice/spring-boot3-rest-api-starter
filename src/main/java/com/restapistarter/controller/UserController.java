package com.restapistarter.controller;

import java.util.Optional;

import org.springdoc.core.annotations.RouterOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restapistarter.model.User;
import com.restapistarter.repository.UserRepository;
import com.restapistarter.service.AuthRequest;
import com.restapistarter.service.JwtService;
import com.restapistarter.service.UserService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/users/")
public class UserController {

    @Autowired
    UserRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @GetMapping("/")
    @Operation(summary = "Users api", description = "This API extracts all users")
    public Iterable<User> list() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    @RouterOperation(operation = @io.swagger.v3.oas.annotations.Operation(summary = "User Api", description = "This API extracts a single user"))
    public User getById(@PathVariable Integer id) {
        return repo.findById(id).get();
    }

    @PostMapping("/add")
    public String createCustomer(@RequestBody User user) {

        System.out.println("customer.getPassword():" + user.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println("customer.getPassword() dopo:" + user.getPassword());
        repo.save(user);
        return "redirect:/api/users/";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("datiUser") User p) {
        repo.save(p);
        return "redirect:/";
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable int id, @RequestBody User updateUser) {
        return userService.updateUser(id, updateUser);
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer idUtente) {
        if (idUtente != null) {
            Optional<User> pOptional = repo.findById(idUtente);
            if (pOptional.isPresent()) {
                User c = pOptional.get();
                repo.delete(c);
            } else {

            }
        }
        return "redirect:/api/users";
    }
/* 
    @PostMapping("/generateToken")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        System.out.println(authRequest);
        System.out.println(authRequest.getUsername());
        System.out.println(authRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }
*/

    @PostMapping("/generateToken")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        return jwtService.authenticateAndGetToken(authRequest);
    }
}
