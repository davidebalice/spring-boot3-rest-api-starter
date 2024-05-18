package com.restapi.controller;

import java.util.Optional;

import org.springdoc.core.annotations.RouterOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restapi.model.User;
import com.restapi.repository.UserRepository;
import com.restapi.service.UserService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/v1/users/")
public class UserController {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public UserController(UserRepository repository, PasswordEncoder passwordEncoder, UserService userService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @GetMapping("/")
    @Operation(summary = "Users api", description = "This API extracts all users")
    public Iterable<User> list() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    @RouterOperation(operation = @io.swagger.v3.oas.annotations.Operation(summary = "User Api", description = "This API extracts a single user"))
    public User getById(@PathVariable Integer id) {
        return repository.findById(id).get();
    }
    @PostMapping("/add")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);
        return ResponseEntity.ok("User created successfully");
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("userData") User p) {
        repository.save(p);
        return "redirect:/";
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable int id, @RequestBody User updateUser) {
        return userService.updateUser(id, updateUser);
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer idUtente) {
        if (idUtente != null) {
            Optional<User> pOptional = repository.findById(idUtente);
            if (pOptional.isPresent()) {
                User c = pOptional.get();
                repository.delete(c);
            } else {

            }
        }
        return "redirect:/api/users";
    }
}
