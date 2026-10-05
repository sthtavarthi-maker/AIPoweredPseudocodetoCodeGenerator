package com.keerthana.pseudocodegenerator.dto;

public class CodeResponse {

    private String code;
    private String explanation;
    private String dryRun;
    private String timeComplexity;
    private String spaceComplexity;
    private String bruteForceSolution;
    private String optimalSolution;
    private String edgeCases;

    public CodeResponse() {
    }

    public CodeResponse(
            String code,
            String explanation,
            String dryRun,
            String timeComplexity,
            String spaceComplexity,
            String bruteForceSolution,
            String optimalSolution,
            String edgeCases) {

        this.code = code;
        this.explanation = explanation;
        this.dryRun = dryRun;
        this.timeComplexity = timeComplexity;
        this.spaceComplexity = spaceComplexity;
        this.bruteForceSolution = bruteForceSolution;
        this.optimalSolution = optimalSolution;
        this.edgeCases = edgeCases;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getDryRun() {
        return dryRun;
    }

    public void setDryRun(String dryRun) {
        this.dryRun = dryRun;
    }

    public String getTimeComplexity() {
        return timeComplexity;
    }

    public void setTimeComplexity(String timeComplexity) {
        this.timeComplexity = timeComplexity;
    }

    public String getSpaceComplexity() {
        return spaceComplexity;
    }

    public void setSpaceComplexity(String spaceComplexity) {
        this.spaceComplexity = spaceComplexity;
    }

    public String getBruteForceSolution() {
        return bruteForceSolution;
    }

    public void setBruteForceSolution(String bruteForceSolution) {
        this.bruteForceSolution = bruteForceSolution;
    }

    public String getOptimalSolution() {
        return optimalSolution;
    }

    public void setOptimalSolution(String optimalSolution) {
        this.optimalSolution = optimalSolution;
    }

    public String getEdgeCases() {
        return edgeCases;
    }

    public void setEdgeCases(String edgeCases) {
        this.edgeCases = edgeCases;
    }
}