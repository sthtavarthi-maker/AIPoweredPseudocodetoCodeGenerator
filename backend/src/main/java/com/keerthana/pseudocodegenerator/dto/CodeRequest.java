package com.keerthana.pseudocodegenerator.dto;

import lombok.Data;

@Data
public class CodeRequest {

    private String prompt;
    private String language;
    private String inputType;
    private String approach;

}