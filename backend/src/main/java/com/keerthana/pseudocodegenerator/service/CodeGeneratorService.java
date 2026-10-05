package com.keerthana.pseudocodegenerator.service;

import com.keerthana.pseudocodegenerator.dto.AnalysisResponse;
import com.keerthana.pseudocodegenerator.dto.CodeOnlyResponse;
import com.keerthana.pseudocodegenerator.dto.CodeRequest;
import com.keerthana.pseudocodegenerator.dto.CodeResponse;

public interface CodeGeneratorService {

    // =========================================================
    // EXISTING COMBINED GENERATION
    // =========================================================

    CodeResponse generateCode(CodeRequest request);

    // =========================================================
    // CODE ONLY
    // OLLAMA CALL #1
    // =========================================================

    CodeOnlyResponse generateCodeOnly(CodeRequest request);

    // =========================================================
    // ANALYSIS ONLY
    // OLLAMA CALL #2
    // Receives the actual generated code
    // =========================================================

    AnalysisResponse generateAnalysis(
            CodeRequest request,
            String generatedCode
    );
}
