package com.example.demo.service;

import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Test;
import com.example.demo.model.Positions;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class AnnualBonusServiceImplTest{

    private final AnnualBonusServiceImpl bonusService=new AnnualBonusServiceImpl();

    @Test
    void testAllBonus(){//Премия за год
        double annualBonus=bonusService.calculate(Positions.HR,100000.00,2.0,243);
        assertThat(annualBonus).isEqualTo(360493.8271684938);

        //Премия за квартал
        double quarterlyBonus=bonusService.calculateQuarterlyBonus(Positions.PM,100000.00,1.5);
        assertThat(quarterlyBonus).isEqualTo(112500.0);

        //Ошибки рассчета
        assertThrows(IllegalArgumentException.class,
                () -> bonusService.calculateQuarterlyBonus(Positions.DEV,100000,1.5));

        assertThrows(IllegalArgumentException.class,
                () -> bonusService.calculateQuarterlyBonus(Positions.PM,100000,0.4));

        assertThrows(IllegalArgumentException.class,
                () -> bonusService.calculateQuarterlyBonus(Positions.PM,0,1.5));

    }



}