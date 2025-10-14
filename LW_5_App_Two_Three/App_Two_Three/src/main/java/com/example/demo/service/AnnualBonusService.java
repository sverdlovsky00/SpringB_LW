package com.example.demo.service;

import com.example.demo.model.Positions;

public interface AnnualBonusService {
    double calculate(Positions positions, double salary,
                     double bonus, int workDays);

    double calculateQuarterlyBonus(Positions positions, double salary, double quarterPerformance);
}