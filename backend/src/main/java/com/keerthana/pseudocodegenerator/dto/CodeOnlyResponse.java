package com.keerthana.pseudocodegenerator.dto;

public class CodeOnlyResponse {

    private String code;

    public CodeOnlyResponse() {
    }

    public CodeOnlyResponse(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}