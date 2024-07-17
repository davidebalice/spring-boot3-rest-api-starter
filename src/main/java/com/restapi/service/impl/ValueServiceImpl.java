package com.restapi.service.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.restapi.exception.ResourceNotFoundException;
import com.restapi.model.Attribute;
import com.restapi.model.Value;
import com.restapi.repository.AttributeRepository;
import com.restapi.repository.ValueRepository;
import com.restapi.service.ValueService;
import com.restapi.utility.FormatResponse;

@Service
public class ValueServiceImpl implements ValueService {

    private final ValueRepository repository;
    private final AttributeRepository attributeRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ValueServiceImpl(ValueRepository repository,AttributeRepository attributeRepository) {
        this.repository = repository;
        this.attributeRepository = attributeRepository;
    }

    @Override
    public Value getValueById(int valueId) {
        Value value = repository.findById(valueId).orElseThrow(
                () -> new ResourceNotFoundException("Value", "id"));
        return modelMapper.map(value, Value.class);
    }

    @Override
    public ResponseEntity<FormatResponse> updateValue(int valueId, Value updateValue) {
        try {
            if (!repository.existsById(valueId)) {
                throw new ResourceNotFoundException("Value", "id");
            }

            Value existingValue = repository.findById(valueId).orElse(null);

            if (updateValue.getValue() != null) {
                existingValue.setValue(updateValue.getValue());
            }
        
     
            if (updateValue.getIdAttribute() != 0) {
                Attribute attribute = attributeRepository.findById(updateValue.getIdAttribute())
                        .orElseThrow(() -> new ResourceNotFoundException("Attribute", "id"));
                existingValue.setAttribute(attribute);
            }

            repository.save(existingValue);

            return new ResponseEntity<FormatResponse>(new FormatResponse("Value updated successfully!"),
                    HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<FormatResponse>(new FormatResponse("Error updating value"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<FormatResponse> deleteValue(Integer valueId) {
        Optional<Value> pOptional = repository.findById(valueId);
        if (pOptional.isPresent()) {
            Value c = pOptional.get();
            repository.delete(c);
            return new ResponseEntity<FormatResponse>(new FormatResponse("Value deleted successfully"),
                    HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Value", "id");
        }
    }
  
}
