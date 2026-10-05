package com.keerthana.pseudocodegenerator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HistoryResponse {

    private Long id;
    private String language;
    private String inputType;
    private String approach;
    private String generatedCode;
}

