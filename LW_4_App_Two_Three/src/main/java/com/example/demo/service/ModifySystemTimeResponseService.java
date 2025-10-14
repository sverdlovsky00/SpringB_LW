package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Qualifier;
import com.example.demo.model.Response;
import com.example.demo.util.DateTimeUtil;

import java.util.Date;

@Service
@Qualifier("ModifySystemTimeResponseService")

public class ModifySystemTimeResponseService
    implements  ModifyResponseService {

    @Override
    public Response modify(Response response){

        response.setSystemTime(DateTimeUtil.getCustomFormat().format(new Date()));
        return response;
    }
}
