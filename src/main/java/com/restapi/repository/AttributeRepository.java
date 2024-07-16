package com.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restapi.model.Attribute;

public interface AttributeRepository extends JpaRepository<Attribute, Integer> {

}
