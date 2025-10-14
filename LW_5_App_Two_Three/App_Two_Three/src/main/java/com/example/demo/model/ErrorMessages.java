package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ErrorMessages {

    EMPTY(""),
    VALIDATION("Ошибка валидации"),
    UNSUPPORTED("Прошла непредвиденная ошибка"),
    UNKNOWN("Неполддерживаемая ошибка");

    private final String description;

    ErrorMessages(String description){
        this.description=description;
    }

    @JsonValue
    public String getName(){return description;}

}
