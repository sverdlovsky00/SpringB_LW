package com.example.demo.model;

import lombok.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Request {

    private Long requestTime;

    @NotBlank(message = "'UID' не должен быть пуст!")
    private String uid;

    @NotBlank(message = "'operationUID' не должен быть пуст!")
    private String operationUid;

    @NotNull(message = "'systemName' не должен быть пуст!")
    private Systems systemName;

    @NotBlank(message = "'systemTime' не должен быть пуст!")
    private String systemTime;

    private String source;

    @NotNull(message = "'communicationId' не должен быть пуст!")
    @Min(value = 1, message = "'communicationId' должен быть больше 0!") // ИЗМЕНИТЬ СООБЩЕНИЕ
    private Integer communicationId;

    @NotNull(message = "'templateId' не должен быть пуст!")
    @Min(value = 1, message = "'templateId' должен быть больше 0!") // ИЗМЕНИТЬ СООБЩЕНИЕ
    private Integer templateId;

    @NotNull(message = "'productCode' не должен быть пуст!")
    @Min(value = 1, message = "'productCode' должен быть больше 0!") // ИЗМЕНИТЬ СООБЩЕНИЕ
    private Integer productCode;

    @NotNull(message = "'smsCode' не должен быть пуст!")
    @Min(value = 1, message = "'smsCode' должен быть больше 0!") // ИЗМЕНИТЬ СООБЩЕНИЕ
    private Integer smsCode;

    public String toString(){
        return "{" +
                "uid='" + uid + '\'' +
                ", operationUid='" + operationUid + '\'' +
                ", SystemName='" + systemName + '\'' +
                ", source='" + source + '\'' +
                ", communicationId='" + communicationId + '\'' +
                ", templateId='" + templateId + '\'' +
                ", productCode='" + productCode + '\'' +
                ", smsCode='" + smsCode + '\'' +
                ", requestTime='"+requestTime+'\''+
                "}";

    }
}

