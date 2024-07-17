package com.restapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "api_attribute_value")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Value {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String value;

    @Column(name = "attribute_id", insertable = false, updatable = false)
    private int idAttribute;
    
    @ManyToOne
    @JoinColumn(name = "attribute_id")
    private Attribute attribute;
}
