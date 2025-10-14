package com.example.demo.service;

import com.example.demo.model.Request;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Qualifier("ModifySourceRequestService")

public class ModifySourceRequestService implements ModifyRequestService{

    @Override
    public void modify(Request request){
        log.info("Модифицируем поле source...");

        String originalSource=request.getSource();

        if (originalSource==null||originalSource.isEmpty()){
            request.setSource("Source_Modified_By_Service_1");}
        else{request.setSource(originalSource+"_Modified_By_Service_1");}

        log.info("Поле было изменено: '{}' > '{}'",originalSource,request.getSource());
    }
}