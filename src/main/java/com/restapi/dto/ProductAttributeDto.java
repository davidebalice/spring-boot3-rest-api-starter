package com.restapi.dto;

import com.restapi.model.Attribute;
import com.restapi.model.Value;

public class ProductAttributeDto {

    private Attribute attribute;
    private Value attributeValue;

    public ProductAttributeDto() {
    }

    public ProductAttributeDto(Attribute attribute, Value attributeValue) {
        this.attribute = attribute;
        this.attributeValue = attributeValue;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public Value getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(Value attributeValue) {
        this.attributeValue = attributeValue;
    }
}
