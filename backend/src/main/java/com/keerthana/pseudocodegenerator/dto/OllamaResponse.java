package com.keerthana.pseudocodegenerator.dto;

public class OllamaResponse {

    private String response;

    private Long total_duration;
    private Long load_duration;
    private Long prompt_eval_duration;
    private Long eval_duration;

    private Integer prompt_eval_count;
    private Integer eval_count;

    public OllamaResponse() {
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Long getTotal_duration() {
        return total_duration;
    }

    public void setTotal_duration(Long total_duration) {
        this.total_duration = total_duration;
    }

    public Long getLoad_duration() {
        return load_duration;
    }

    public void setLoad_duration(Long load_duration) {
        this.load_duration = load_duration;
    }

    public Long getPrompt_eval_duration() {
        return prompt_eval_duration;
    }

    public void setPrompt_eval_duration(Long prompt_eval_duration) {
        this.prompt_eval_duration = prompt_eval_duration;
    }

    public Long getEval_duration() {
        return eval_duration;
    }

    public void setEval_duration(Long eval_duration) {
        this.eval_duration = eval_duration;
    }

    public Integer getPrompt_eval_count() {
        return prompt_eval_count;
    }

    public void setPrompt_eval_count(Integer prompt_eval_count) {
        this.prompt_eval_count = prompt_eval_count;
    }

    public Integer getEval_count() {
        return eval_count;
    }

    public void setEval_count(Integer eval_count) {
        this.eval_count = eval_count;
    }
}