package com.example.demo.controller;

import com.example.demo.exception.ValidationFailedException;
import com.example.demo.exception.UnsupportedCodeException;
import com.example.demo.model.*;
import com.example.demo.service.ModifyRequestService;
import com.example.demo.service.ModifyResponseService;
import com.example.demo.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import com.example.demo.util.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;

import java.rmi.server.UID;
import java.util.Date;

@Slf4j
@RestController
public class MyController {

    private final ValidationService validationService;
    private final ModifyResponseService modifyResponseService;
    private final ModifyRequestService modifySystemNameRequestService;
    private final ModifyRequestService modifySourceRequestService;

    @Autowired
    public MyController(ValidationService validationService,
                        @Qualifier("ModifyOperationUidResponseService")
                        ModifyResponseService modifyResponseService,
                        @Qualifier("ModifySystemNameRequestService")
                            ModifyRequestService modifySystemNameRequestService,
                        @Qualifier("ModifySourceRequestService")
                            ModifyRequestService modifySourceRequestService) {
        this.validationService = validationService;
        this.modifyResponseService = modifyResponseService;
        this.modifySystemNameRequestService = modifySystemNameRequestService;
        this.modifySourceRequestService= modifySourceRequestService;
    }

    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request,
                                             BindingResult bindingResult) {

        request.setRequestTime(System.currentTimeMillis());

        log.info("Начало обработки запроса...");
        log.info("Запрос: {}",request);
        log.info("BindingResult имеет ошибки: {}, в количестве: {}",
            bindingResult.hasErrors(), bindingResult.getErrorCount());

        if (bindingResult.hasErrors()){
            log.error("Ошибка валидации!");
            for (var error:bindingResult.getFieldErrors()){
                log.error(" - Поле: {},  - Ошибка: {},  - Значение: {}, ",
                        error.getField(), error.getDefaultMessage(), error.getRejectedValue());
            }
        }

        Response response = Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(DateTimeUtil.getCustomFormat().format(new Date()))
                .code(Codes.SUCCESS)
                .errorCode(ErrorCodes.EMPTY)
                .errorMessage(ErrorMessages.EMPTY)
                .build();

        log.info("Создан Response: {}", response);

        try {
            log.info("Вызываем validationService...");
            validationService.isValid(bindingResult);
            log.info("Валидация прошла успешно");

            // Проверка для UID = "123"
            if ("123".equals(request.getUid())) {
                log.info("Обнаружен запрещенный UID: 123");
                throw new UnsupportedCodeException("UID не может быть '123'");
            }

        } catch (ValidationFailedException e) {
            log.error("Ошибка ValidationFailedException: {}",e.getMessage());
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.VALIDATION_EXCEPTION);
            response.setErrorMessage(ErrorMessages.VALIDATION);
            log.info("Обновление Response после ошибки валидации... {}", response);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (UnsupportedCodeException e) {
            log.error("Ошибка UnsupportedCodeException: {}",e.getMessage());
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.UNSUPPORTED_EXCEPTION);
            response.setErrorMessage(ErrorMessages.UNSUPPORTED);
            log.info("Обновление Response после неподдерживаемого UID... {}", response);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Ошибка Exception: {}",e.getMessage(), e);
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.UNKNOWN_EXCEPTION);
            response.setErrorMessage(ErrorMessages.UNKNOWN);
            log.info("Обновление Response после неожиданной ошибки... {}", response);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        log.info("Вызывается модификация Response...");
        modifyResponseService.modify(response);

        log.info("Вызывается модификация RequestSource...");
        modifySourceRequestService.modify(request);

        log.info("Вызывается модификация RequestSystemName и отправка через Сервис 2...");
        modifySystemNameRequestService.modify(request);

        log.info("Response после модификации: {}", response);

        log.info("Завершение обработки запроса...");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}