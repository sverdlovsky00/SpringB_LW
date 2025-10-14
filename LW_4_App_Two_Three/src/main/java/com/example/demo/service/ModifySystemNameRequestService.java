package com.example.demo.service;

import com.example.demo.model.Request;
import com.example.demo.model.Response;
import com.example.demo.model.Systems;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Qualifier("ModifySystemNameRequestService")
public class ModifySystemNameRequestService implements ModifyResponseService, ModifyRequestService {

    @Override
    public Response modify(Response response) {
        // Этот метод используется только для модификации Response
        // Отправка в Сервис 2 происходит в методе modify(Request request)
        return response;
    }

    @Override
    public void modify(Request request) {
        // Модифицируем оригинальный request
        request.setSystemName(Systems.ERP);

        if (request.getSource() == null) {
            request.setSource("MODIFIED_BY_SERVICE");
        }

        log.info("Модифицированный request для отправки в Сервис 2: {}", request);

        // Отправляем модифицированный request в Сервис 2
        try {
            HttpEntity<Request> httpEntity = new HttpEntity<>(request);

            ResponseEntity<String> exchangeResponse = new RestTemplate().exchange(
                    "http://localhost:8084/feedback",
                    HttpMethod.POST,
                    httpEntity,
                    new ParameterizedTypeReference<String>() {}
            );

            log.info("Успешно отправлен request в Сервис 2. Статус ответа: {}",
                    exchangeResponse.getStatusCode());

        } catch (Exception e) {
            log.error("Ошибка при отправке request в Сервис 2: {}", e.getMessage(), e);
        }
    }
}