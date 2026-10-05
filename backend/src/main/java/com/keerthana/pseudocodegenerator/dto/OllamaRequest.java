package com.keerthana.pseudocodegenerator.dto;

import java.util.Map;

public class OllamaRequest {

    private String model;
    private String prompt;
    private boolean stream;
    private String keep_alive;
    private Map<String, Object> options;

    public OllamaRequest() {
    }

    public OllamaRequest(
            String model,
            String prompt,
            boolean stream,
            String keep_alive,
            Map<String, Object> options) {

        this.model = model;
        this.prompt = prompt;
        this.stream = stream;
        this.keep_alive = keep_alive;
        this.options = options;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public boolean isStream() {
        return stream;
    }

    public void setStream(boolean stream) {
        this.stream = stream;
    }

    public String getKeep_alive() {
        return keep_alive;
    }

    public void setKeep_alive(String keep_alive) {
        this.keep_alive = keep_alive;
    }

    public Map<String, Object> getOptions() {
        return options;
    }

    public void setOptions(Map<String, Object> options) {
        this.options = options;
    }
}