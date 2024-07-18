package com.restapi.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttributeDto {
    private int id;
    private String name;
    // private List<Value> values;
    private List<ValueDto> values;
}
