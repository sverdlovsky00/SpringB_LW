package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Qualifier;
import com.example.demo.model.Response;

import java.util.UUID;

@Service
@Qualifier("ModifyOperationUidResponseService")

public class ModifyOperationUidResponseService implements ModifyResponseService{

        @Override
                public Response modify(Response response){

            UUID uuid = UUID.randomUUID();
            response.setOperationUid(uuid.toString());
            return response;

        }
    }

