package com.sysco.cdm.client.mediator;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author : ralw0871
 */
@Component
public class BaseRestOutboundMediator {

    private final RestTemplate restTemplate;

    public BaseRestOutboundMediator(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public <T extends Object> ResponseEntity<T> post(String apiUrl, Object request, Class<T> responseClazz,
                                                     Map<String, String> headers)  {
        ResponseEntity<T> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, populateHttpEntity(headers),
                responseClazz);
        return responseEntity;
    }

    public <T extends Object> ResponseEntity<T> get(String apiUrl, Object request, Class<T> responseClazz,
                                                    Map<String, String> headers) {
        ResponseEntity<T> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.GET, populateHttpEntity(headers),
                responseClazz);
        return responseEntity;
    }

    public <T extends Object> ResponseEntity<T> put(String apiUrl, Object request, Class<T> responseClazz,
                                                    Map<String, String> headers) {
        ResponseEntity<T> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.PUT, populateHttpEntity(headers),
                responseClazz);
        return responseEntity;
    }

    public <T extends Object> ResponseEntity<T> delete(String apiUrl, Object request, Class<T> responseClazz,
                                                       Map<String, String> headers) {
        ResponseEntity<T> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.DELETE, populateHttpEntity(headers),
                responseClazz);
        return responseEntity;
    }

    private HttpEntity<Object> populateHttpEntity(Map<String, String> headers) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", MediaType.APPLICATION_JSON_UTF8.getType());
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        if (headers != null && !headers.isEmpty()) {
            headers.forEach((key, value) ->
                    httpHeaders.set(key, value));
        }
        return new HttpEntity<>(httpHeaders);
    }
}
