package com.example.demo.service;
import com.example.demo.model.Request;
import com.example.demo.model.Response;
import org.springframework.stereotype.Service;

@Service
public interface ModifyRequestService {

    void modify(Request request);

}