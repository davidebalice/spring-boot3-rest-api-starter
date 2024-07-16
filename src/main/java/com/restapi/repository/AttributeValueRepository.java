package com.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restapi.model.AttributeValue;

public interface AttributeValueRepository extends JpaRepository<AttributeValue, Integer> {

}
