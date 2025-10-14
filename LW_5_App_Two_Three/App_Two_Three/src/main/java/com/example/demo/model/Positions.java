package com.example.demo.model;

import lombok.Getter;

@Getter

public enum Positions {

    DEV(2.2,false),
    HR(1.2,false),
    TL(2.6,true),
    PM(3.0,true),
    QA(1.8,false),
    ANALYST(1.9,false);

    private final double positionCoefficient;
    private final boolean isManager;

    Positions(double positionCoefficient, boolean isManager){
        this.positionCoefficient=positionCoefficient;
        this.isManager=isManager();
    }

}
