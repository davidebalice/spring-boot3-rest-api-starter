package com.restapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.restapi.model.Attribute;

public interface AttributeRepository extends JpaRepository<Attribute, Integer> {
    @Query("SELECT a FROM Attribute a LEFT JOIN FETCH a.values")
    List<Attribute> findAllWithValues();
}
