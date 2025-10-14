package com.example.demo.service;

import  org.springframework.stereotype.Service;
import com.example.demo.model.Positions;

import java.time.Year;

@Service

public class AnnualBonusServiceImpl implements  AnnualBonusService {

    @Override

    public double calculate (Positions positions, double salary,
                             double bonus, int workDays){
        int commonDays = Year.now().length();
        return salary*bonus*commonDays* positions.getPositionCoefficient()
                                /workDays;
    }

    @Override
    public double calculateQuarterlyBonus(Positions positions,
                    double salary, double quarterPerformance){
        if(!positions.isManager()){
            throw new IllegalArgumentException(
                    "Позиция '"+positions.name()+"' не является управленческой, премия недоступна");
        }

        if (quarterPerformance<0.5 || quarterPerformance>2.0){
            throw new IllegalArgumentException("Коэффициент эеффективности должен быть 0.5 ... 2.0");
        }
        return salary*positions.getPositionCoefficient()*quarterPerformance*0.25;
    }
}

