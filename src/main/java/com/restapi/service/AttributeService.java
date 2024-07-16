package com.restapi.service;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.restapi.model.Attribute;
import com.restapi.utility.FormatResponse;
@Service
public interface AttributeService {
    Attribute getAttributeById(int attributeId);
    ResponseEntity<FormatResponse> updateAttribute(int id, Attribute updateAttribute);
    ResponseEntity<FormatResponse> deleteAttribute(Integer idAttribute);
}
