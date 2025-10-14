package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ErrorCodes {

    EMPTY(""),
    VALIDATION_EXCEPTION("ValidationException"),
    UNSUPPORTED_EXCEPTION("UnsupportedCodeException"),
    UNKNOWN_EXCEPTION("UnsupportedException");

    private final String name;

    ErrorCodes(String name){this.name=name;}

    @JsonValue
    public String getName(){return name;}

    @Override
    public String toString(){return name;}

}
