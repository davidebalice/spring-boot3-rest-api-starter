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
import com.restapi.repository.AttributeRepository;
import com.restapi.service.AttributeService;
import com.restapi.utility.FormatResponse;

@Service
public class AttributeServiceImpl implements AttributeService {

    private final AttributeRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public AttributeServiceImpl(AttributeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Attribute getAttributeById(int attributeId) {
        Attribute attribute = repository.findById(attributeId).orElseThrow(
                () -> new ResourceNotFoundException("Attribute", "id"));
        return modelMapper.map(attribute, Attribute.class);
    }

    @Override
    public ResponseEntity<FormatResponse> updateAttribute(int attributeId, Attribute updateAttribute) {
        try {
            if (!repository.existsById(attributeId)) {
                throw new ResourceNotFoundException("Attribute", "id");
            }

            Attribute existingAttribute = repository.findById(attributeId).orElse(null);

            if (updateAttribute.getName() != null) {
                existingAttribute.setName(updateAttribute.getName());
            }
          

            repository.save(existingAttribute);

            return new ResponseEntity<FormatResponse>(new FormatResponse("Attribute updated successfully!"),
                    HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<FormatResponse>(new FormatResponse("Error updating attribute"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<FormatResponse> deleteAttribute(Integer attributeId) {
        Optional<Attribute> pOptional = repository.findById(attributeId);
        if (pOptional.isPresent()) {
            Attribute c = pOptional.get();
            repository.delete(c);
            return new ResponseEntity<FormatResponse>(new FormatResponse("Attribute deleted successfully"),
                    HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Attribute", "id");
        }
    }

    
}
