package com.keerthana.pseudocodegenerator.service.impl;

import java.util.Map;

import com.keerthana.pseudocodegenerator.dto.OllamaRequest;
import com.keerthana.pseudocodegenerator.dto.OllamaResponse;
import com.keerthana.pseudocodegenerator.service.OllamaService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class OllamaServiceImpl implements OllamaService {

    private final RestClient restClient;

    @Value("${ollama.base-url}")
    private String baseUrl;

    @Value("${ollama.model}")
    private String model;

    @Value("${ollama.api.generate}")
    private String generateApi;

    public OllamaServiceImpl(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public String generateResponse(String prompt, int maxTokens) {

        OllamaRequest request = new OllamaRequest();

        request.setModel(model);
        request.setPrompt(prompt);
        request.setStream(false);

        // Keep qwen2.5-coder:3b loaded for subsequent requests
        request.setKeep_alive("10m");

        // Control generation behavior
        request.setOptions(Map.of(
                "temperature", 0,
                "num_predict", maxTokens             
        ));

        System.out.println("========== OLLAMA REQUEST ==========");
        System.out.println("Model: " + model);
        System.out.println("Prompt length: " + prompt.length());
        System.out.println("Keep alive: 10m");
        System.out.println("Temperature: 0");
        System.out.println("Max generated tokens: " + maxTokens);
        System.out.println("====================================");

        long start = System.currentTimeMillis();

        OllamaResponse response = restClient.post()
                .uri(baseUrl + generateApi)
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .body(OllamaResponse.class);

        long end = System.currentTimeMillis();

System.out.println("Ollama generation time: "
        + ((end - start) / 1000.0) + " seconds");

if (response != null) {
    System.out.println("========== OLLAMA INTERNAL TIMING ==========");
    System.out.println("Total duration: "
            + response.getTotal_duration() / 1_000_000.0 + " ms");
    System.out.println("Load duration: "
            + response.getLoad_duration() / 1_000_000.0 + " ms");
    System.out.println("Prompt evaluation duration: "
            + response.getPrompt_eval_duration() / 1_000_000.0 + " ms");
    System.out.println("Generation evaluation duration: "
            + response.getEval_duration() / 1_000_000.0 + " ms");
    System.out.println("Prompt tokens: "
            + response.getPrompt_eval_count());
    System.out.println("Generated tokens: "
            + response.getEval_count());
    System.out.println("============================================");
}

        if (response == null || response.getResponse() == null) {
            return "No response received from Ollama.";
        }

        return response.getResponse();
    }
}