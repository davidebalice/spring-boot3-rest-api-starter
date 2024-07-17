package com.restapi.service;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.restapi.model.Value;
import com.restapi.utility.FormatResponse;
@Service
public interface ValueService {
    Value getValueById(int subcategoryId);
    ResponseEntity<FormatResponse> updateValue(int id, Value updateValue);
    ResponseEntity<FormatResponse> deleteValue(Integer idValue);
}
