package com.restapi.service;


import com.restapi.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;
@Service
public interface UserService extends UserDetailsService {
    String addUser(User user);
    ResponseEntity<String> updateUser(int id, User updateUser);
}
