package com.traverse.storage.utils;


import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

public class PostRequestSender {

    private final String baseUrl = "http://localhost:8080"; // Replace with your actual base URL
    private final WebClient webClient = WebClient.create("http://localhost:8080"); // Replace with your actual base URL

    public void sendPostRequest(String body, String endpoint) {
        String endpointUrl = baseUrl + endpoint; // Replace with your actual endpoint URL

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        webClient.post()
                .uri(endpointUrl)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .body(BodyInserters.fromValue(body))
                .retrieve()
                .bodyToMono(String.class)
                .doOnSuccess(responseBody -> System.out.println("Response: " + responseBody))
                .doOnError(error -> System.err.println("Request failed with error: " + error.getMessage()))
                .block(); // block to wait for the response (in a reactive environment, you'd typically handle it asynchronously)
    }
}


