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
        this.modifySourceRequestService = modifySourceRequestService;
    }

    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request,
                                             BindingResult bindingResult) {

        request.setRequestTime(System.currentTimeMillis());
        logRequestInfo(request, bindingResult);

        Response response = createInitialResponse(request);

        try {
            validateRequest(request, bindingResult);
        } catch (Exception e) {
            return handleException(response, e);
        }

        processModifications(request, response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void logRequestInfo(Request request, BindingResult bindingResult) {
        log.info("Начало обработки запроса: {}", request);
        log.info("Ошибки валидации: {}, количество: {}",
                bindingResult.hasErrors(), bindingResult.getErrorCount());

        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(error ->
                    log.error("Ошибка поля: {} - {} - {}",
                            error.getField(), error.getDefaultMessage(), error.getRejectedValue())
            );
        }
    }

    private Response createInitialResponse(Request request) {
        return Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(DateTimeUtil.getCustomFormat().format(new Date()))
                .code(Codes.SUCCESS)
                .errorCode(ErrorCodes.EMPTY)
                .errorMessage(ErrorMessages.EMPTY)
                .build();
    }

    private void validateRequest(Request request, BindingResult bindingResult)
            throws ValidationFailedException, UnsupportedCodeException {
        validationService.isValid(bindingResult);

        if ("123".equals(request.getUid())) {
            throw new UnsupportedCodeException("UID не может быть '123'");
        }
    }

    private ResponseEntity<Response> handleException(Response response, Exception e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        if (e instanceof ValidationFailedException) {
            updateErrorResponse(response, ErrorCodes.VALIDATION_EXCEPTION, ErrorMessages.VALIDATION);
        } else if (e instanceof UnsupportedCodeException) {
            updateErrorResponse(response, ErrorCodes.UNSUPPORTED_EXCEPTION, ErrorMessages.UNSUPPORTED);
        } else {
            updateErrorResponse(response, ErrorCodes.UNKNOWN_EXCEPTION, ErrorMessages.UNKNOWN);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        log.error("Ошибка обработки: {}", e.getMessage());
        return new ResponseEntity<>(response, status);
    }

    private void updateErrorResponse(Response response, ErrorCodes errorCode, ErrorMessages errorMessage) {
        response.setCode(Codes.FAILED);
        response.setErrorCode(errorCode);
        response.setErrorMessage(errorMessage);
    }

    private void processModifications(Request request, Response response) {
        modifyResponseService.modify(response);
        modifySourceRequestService.modify(request);
        modifySystemNameRequestService.modify(request);
        log.info("Завершена модификация: {}", response);
    }
}