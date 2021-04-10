package com.github.hikelin.testng.common.provider.rest;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.lang.Nullable;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

public class RestClient {

    private static Logger log = LoggerFactory.getLogger(RestClient.class);

    private final static int TIMEOUT = 5000;

    private final RestTemplate restTemplate;

    public RestClient(){
        this.restTemplate = new RestTemplate(getClientHttpRequestFactory());
    }

    public String get(String url) throws RestException {
        ResponseEntity<String> response
                = restTemplate.getForEntity(url, String.class);
        if(response.getStatusCode() != HttpStatus.OK) {
            log.error("Response: {}", response.getBody());
            throw new RestException(String.format("Request %s failed.", url), response.getStatusCode().value());
        }
        return response.getBody();
    }

    public <T> T get(String url, Class<T> clazz) throws RestException {
        ResponseEntity<T> response
                = restTemplate.getForEntity(url, clazz);
        if(response.getStatusCode() != HttpStatus.OK) {
            log.error("Response: {}", response.getBody());
            throw new RestException(String.format("Request %s failed.", url), response.getStatusCode().value());
        }
        return response.getBody();
    }

    public String get(String url, Map<String, ?> uriVariables) throws RestException {
        ResponseEntity<String> response
                = restTemplate.getForEntity(url, String.class, uriVariables);
        if(response.getStatusCode() != HttpStatus.OK) {
            log.error("Response: {}", response.getBody());
            throw new RestException(String.format("Request %s failed.", url), response.getStatusCode().value());
        }
        return response.getBody();
    }

    public String get(String url, Map<String, String> headerVariables, Map<String, ?> uriVariables) throws RestException {
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        for (Map.Entry<String, String> h: headerVariables.entrySet()) {
            headers.set(h.getKey(), h.getValue());
        }
        HttpEntity<HttpHeaders> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                httpEntity,
                String.class,
                uriVariables
        );

        if(response.getStatusCode() != HttpStatus.OK) {
            log.error("Response: {}", response.getBody());
            throw new RestException(String.format("Request %s failed.", url), response.getStatusCode().value());
        }
        return response.getBody();
    }

    public <T> T get(String url, Map<String, String> headerVariables, Class<T> clazz) throws RestException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        for (Map.Entry<String, String> h: headerVariables.entrySet()) {
            headers.set(h.getKey(), h.getValue());
        }

        HttpEntity<HttpHeaders> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<T> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                httpEntity,
                clazz
        );

        if(response.getStatusCode() != HttpStatus.OK) {
            log.error("Response: {}", response.getBody());
            throw new RestException(String.format("Request %s failed.", url), response.getStatusCode().value());
        }
        return response.getBody();
    }

    public <T, B> T post(String url, Map<String, String> headerVariables, @Nullable B body, Class<T> clazz) throws RestException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        for (Map.Entry<String, String> h: headerVariables.entrySet()) {
            headers.set(h.getKey(), h.getValue());
        }

        HttpEntity<B> httpEntity = new HttpEntity<>(body, headers);

        ResponseEntity<T> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                httpEntity,
                clazz
        );

        if(response.getStatusCode() != HttpStatus.OK) {
            log.error("Response: {}", response.getBody());
            throw new RestException(String.format("Request %s failed.", url), response.getStatusCode().value());
        }
        return response.getBody();
    }

    private ClientHttpRequestFactory getClientHttpRequestFactory() {
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(TIMEOUT)
                .setConnectionRequestTimeout(TIMEOUT)
                .setSocketTimeout(TIMEOUT)
                .build();
        CloseableHttpClient client = HttpClientBuilder
                .create()
                .setDefaultRequestConfig(config)
                .build();
        return new HttpComponentsClientHttpRequestFactory(client);
    }

}
