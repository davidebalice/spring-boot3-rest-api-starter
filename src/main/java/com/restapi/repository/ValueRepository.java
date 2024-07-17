package com.restapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restapi.model.Value;

public interface ValueRepository extends JpaRepository<Value, Integer> {
    List<Value> findByAttributeId(int attributeId);
}
