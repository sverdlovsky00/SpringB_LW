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

    /** Время запроса */
    private Long requestTime;

    /** Наименование позиции (enum) */
    private Positions positions;

    /** Уникальный идентификатор сообщения */
    @NotBlank(message = "'UID' не должен быть пуст!")
    private String uid;

    /** Уникальный идентификатор операции */
    @NotBlank(message = "'operationUID' не должен быть пуст!")
    private String operationUid;

    /** Имя системы отправителя */
    @NotNull(message = "'systemName' не должен быть пуст!")
    private Systems systemName;

    /** Время создания сообщения */
    @NotBlank(message = "'systemTime' не должен быть пуст!")
    private String systemTime;

    /** Должность */
    @NotBlank(message = "'position' не должен быть пуст!")
    private String position;

    /** Источник запроса */
    private String source;

    /** Идентификатор коммуникации */
    @NotNull(message = "'communicationId' не должен быть пуст!")
    @Min(value = 1, message = "'communicationId' должен быть больше 0!") // ИЗМЕНИТЬ СООБЩЕНИЕ
    private Integer communicationId;

    /** Идентификатор шаблона */
    @NotNull(message = "'templateId' не должен быть пуст!")
    @Min(value = 1, message = "'templateId' должен быть больше 0!") // ИЗМЕНИТЬ СООБЩЕНИЕ
    private Integer templateId;

    /** Код продукта */
    @NotNull(message = "'productCode' не должен быть пуст!")
    @Min(value = 1, message = "'productCode' должен быть больше 0!") // ИЗМЕНИТЬ СООБЩЕНИЕ
    private Integer productCode;

    /** Код СМС */
    @NotNull(message = "'smsCode' не должен быть пуст!")
    @Min(value = 1, message = "'smsCode' должен быть больше 0!")
    private Integer smsCode;

    /** Заработная плата */
    @NotNull(message = "'salary' не должен быть пуст!")
    @Min(value = 1, message = "'salary' должен быть больше 0!")
    private Integer salary;

    /** Коэффициент бонуса */
    private Double bonus;

    /** Количество рабочих дней */
    @NotNull(message = "'workDays' не должен быть пуст!")
    private Integer workDays;


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

                ", position='" + position + '\'' +
                ", salary='" + salary + '\'' +
                ", bonus='" + bonus + '\'' +
                ", workDays='" + workDays + '\'' +

                "}";

    }
}

