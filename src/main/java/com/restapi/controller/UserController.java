package com.restapi.controller;

import java.util.List;
import java.util.Optional;

import org.springdoc.core.annotations.RouterOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restapi.config.DemoMode;
import com.restapi.dto.UserDto;
import com.restapi.exception.DemoModeException;
import com.restapi.model.User;
import com.restapi.repository.UserRepository;
import com.restapi.service.UserService;
import com.restapi.utility.FormatResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(
        name = "CRUD REST APIs for User Resource",
        description = "USERS CRUD REST APIs - Create User, Update User, Get User, Get All Users, Delete User"
)
@RestController
@RequestMapping("/api/v1/users/")
public class UserController {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Autowired
    private DemoMode demoMode;

    public UserController(UserRepository repository, PasswordEncoder passwordEncoder, UserService userService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }


    //Get all Users Rest Api
    //http://localhost:8081/api/v1/users
    @Operation(summary = "Get all Users", description = "Retrieve a list of all users")
    @ApiResponse(
        responseCode = "200",
        description = "HTTP Status 200 SUCCESS"
    )
    @GetMapping("/")
    public List<UserDto> list() {
        return userService.getAllUsers();
    }
    //


    //Get all Users Rest Api
    //http://localhost:8081/api/v1/users/1
    @Operation(
        summary = "Get User By ID REST API",
        description = "Get User By ID REST API is used to get a single User from the database, get id by url"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    @GetMapping("/{id}")
    @RouterOperation(operation = @io.swagger.v3.oas.annotations.Operation(summary = "User Api", description = "This API extracts a single user"))
    public UserDto getById(@PathVariable Integer id) {
        return userService.getUser(id);
    }
    //


    //Add new User Rest Api
    //http://localhost:8081/api/v1/users/add
    @Operation(
        summary = "Crate new User REST API",
        description = "Save new User on database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status 201 Created"
    )
    @PostMapping("/add")
    public ResponseEntity<FormatResponse> createUser(@RequestBody User user) {
         if (demoMode.isEnabled()) {
            throw new DemoModeException();
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);
        return new ResponseEntity<FormatResponse>(new FormatResponse("User created successfully!"), HttpStatus.OK);
    }
    //

    
    //Update User Rest Api
    //http://localhost:8081/api/v1/users/1
    @Operation(
        summary = "Update User REST API",
        description = "Update User on database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    @PatchMapping("/{id}")
    public ResponseEntity<FormatResponse> updateUser(@PathVariable int id, @RequestBody User updateUser) {
        if (demoMode.isEnabled()) {
            throw new DemoModeException();
        }
        return userService.updateUser(id, updateUser);
    }
    //


    //Delete User Rest Api
    //http://localhost:8081/api/v1/users/1
    @Operation(
        summary = "Delete User REST API",
        description = "Delete User on database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<FormatResponse> delete(@PathVariable("id") Integer idUtente) {
        if (demoMode.isEnabled()) {
            throw new DemoModeException();
        }
        if (idUtente != null) {
            Optional<User> pOptional = repository.findById(idUtente);
            if (pOptional.isPresent()) {
                User c = pOptional.get();
                repository.delete(c);
            } else {

            }
        }
        return new ResponseEntity<FormatResponse>(new FormatResponse("User deleted successfully!"), HttpStatus.OK);
    }
    //
}
