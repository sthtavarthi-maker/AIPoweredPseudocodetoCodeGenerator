package com.keerthana.pseudocodegenerator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.keerthana.pseudocodegenerator.dto.AnalysisRequest;
import com.keerthana.pseudocodegenerator.dto.AnalysisResponse;
import com.keerthana.pseudocodegenerator.dto.CodeOnlyResponse;
import com.keerthana.pseudocodegenerator.dto.CodeRequest;
import com.keerthana.pseudocodegenerator.dto.CodeResponse;
import com.keerthana.pseudocodegenerator.service.CodeGeneratorService;

@RestController
@RequestMapping("/api/generator")
@CrossOrigin(origins = "http://localhost:5173")
public class GeneratorController {

@Autowired
private CodeGeneratorService codeGeneratorService;

// =========================================================
// EXISTING COMBINED ENDPOINT
// Kept so existing functionality does not break.
// =========================================================

@PostMapping("/generate")
public CodeResponse generate(
        @RequestBody CodeRequest request) {

    return codeGeneratorService.generateCode(request);
}

// =========================================================
// CODE ONLY
// OLLAMA CALL #1
// =========================================================

@PostMapping("/code")
public CodeOnlyResponse generateCode(
        @RequestBody CodeRequest request) {

    return codeGeneratorService.generateCodeOnly(request);
}

// =========================================================
// ANALYSIS ONLY
// OLLAMA CALL #2
// =========================================================

@PostMapping("/analysis")
public AnalysisResponse generateAnalysis(
        @RequestBody AnalysisRequest request) {

    CodeRequest codeRequest = new CodeRequest();

    codeRequest.setPrompt(request.getPrompt());
    codeRequest.setLanguage(request.getLanguage());
    codeRequest.setInputType(request.getInputType());
    codeRequest.setApproach(request.getApproach());

    return codeGeneratorService.generateAnalysis(
            codeRequest,
            request.getGeneratedCode()
    );
}

}
