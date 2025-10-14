package com.example.demo.service;

import org.springframework.stereotype.Service;
import com.example.demo.model.Response;

@Service
public interface ModifyResponseService {

    Response modify (Response response);
}
