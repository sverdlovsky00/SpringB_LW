package com.example.demo.service;

import org.springframework.validation.BindingResult;
import com.example.demo.exception.ValidationFailedException;

public interface ValidationService {
    void isValid(BindingResult bindingResult) throws ValidationFailedException;
}