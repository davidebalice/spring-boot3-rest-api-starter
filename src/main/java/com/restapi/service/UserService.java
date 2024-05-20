package com.restapi.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.restapi.dto.UserDto;
import com.restapi.model.User;

@Service
public interface UserService extends UserDetailsService {
    List<UserDto> getAllUsers();
    UserDto getUser(int id);
    ResponseEntity<String> addUser(User user);
    ResponseEntity<String> createUser(UserDto user);
    ResponseEntity<String> updateUser(int id, User updateUser);
}