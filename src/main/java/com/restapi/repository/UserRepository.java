package com.restapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restapi.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    void deleteById(Optional<User> p);

    List<User> findByEmail(String email);

    Optional<User> findByName(String name);
    Optional<User> findByUsername(String username);
}
