package com.restapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restapi.dto.UserDto;
import com.restapi.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    void deleteById(Optional<User> p);

    Optional<User> findById(int id);

    UserDto findByEmail(String email);

    Optional<User> findByName(String name);

    Optional<User> findByUsername(String username);
}
