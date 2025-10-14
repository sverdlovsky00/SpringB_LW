package com.example.demo.controller;

import com.example.demo.exception.ValidationFailedException;
import com.example.demo.exception.UnsupportedCodeException; // ДОБАВИТЬ ЭТОТ ИМПОРТ
import com.example.demo.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import com.example.demo.model.Request;
import com.example.demo.model.Response;

import java.text.SimpleDateFormat;

@RestController
public class MyController {

    private final ValidationService validationService;

    @Autowired
    public MyController(ValidationService validationService) {
        this.validationService = validationService;
    }

    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request,
                                             BindingResult bindingResult) {

        // ЛОГИРОВАНИЕ
        System.out.println("=== НАЧАЛО ОБРАБОТКИ ЗАПРОСА ===");
        System.out.println("Получен Request: " + request);
        System.out.println("BindingResult: " + bindingResult);
        System.out.println("Has Errors: " + bindingResult.hasErrors());
        System.out.println("Error Count: " + bindingResult.getErrorCount());

        if (bindingResult.hasErrors()) {
            System.out.println("Список ошибок:");
            for (var error : bindingResult.getFieldErrors()) {
                System.out.println(" - Поле: " + error.getField() +
                        ", Ошибка: " + error.getDefaultMessage() +
                        ", Отклоненное значение: " + error.getRejectedValue());
            }
        } else {
            System.out.println("Ошибок валидации НЕТ!");
        }
        System.out.println("==============================");

        Response response = Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(request.getSystemTime())
                .code("success")
                .errorCode("")
                .errorMessage("")
                .build();

        try {
            System.out.println("Вызываем validationService.isValid()...");
            validationService.isValid(bindingResult);
            System.out.println("Валидация прошла успешно");

            // Проверка для UID = "123"
            if ("123".equals(request.getUid())) {
                System.out.println("Обнаружен запрещенный UID: 123");
                throw new UnsupportedCodeException("UID не может быть '123'");
            }

        } catch (ValidationFailedException e) {
            System.out.println("Поймана ValidationFailedException: " + e.getMessage());
            response.setCode("failed");
            response.setErrorCode("ValidationException");
            response.setErrorMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (UnsupportedCodeException e) {
            System.out.println("Поймана UnsupportedCodeException: " + e.getMessage());
            response.setCode("failed");
            response.setErrorCode("UnsupportedCodeException");
            response.setErrorMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.out.println("Поймана общая Exception: " + e.getMessage());
            e.printStackTrace();
            response.setCode("failed");
            response.setErrorCode("UnknownException");
            response.setErrorMessage("Произошла непредвиденная ошибка");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        System.out.println("Возвращаем УСПЕШНЫЙ ответ");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}