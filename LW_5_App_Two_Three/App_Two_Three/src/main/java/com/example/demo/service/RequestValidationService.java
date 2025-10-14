package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import com.example.demo.exception.ValidationFailedException;

@Service
public class RequestValidationService implements ValidationService {
    @Override
    public void isValid(BindingResult bindingResult) throws ValidationFailedException {
        if (bindingResult.hasErrors()) {
            String errorMessage = "Ошибка валидации: "; // ДОБАВЛЕН ПРОБЕЛ И ДВОЕТОЧИЕ

            for (var error : bindingResult.getFieldErrors()) {
                errorMessage += error.getField() + " - " + error.getDefaultMessage() + "; ";
            }

            throw new ValidationFailedException(errorMessage);
        }
    }
}