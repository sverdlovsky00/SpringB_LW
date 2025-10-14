package com.example.demo.model;

import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Data
@Builder
@Setter

public class Response {

    private String uid;
    private String operationUid;
    private String systemTime;
    private Codes code;
    private ErrorCodes errorCode;
    private ErrorMessages errorMessage;
}