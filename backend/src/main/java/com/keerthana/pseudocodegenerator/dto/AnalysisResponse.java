package com.keerthana.pseudocodegenerator.dto;

public class AnalysisResponse {

    private String explanation;
    private String dryRun;
    private String timeComplexity;
    private String spaceComplexity;
    private String edgeCases;

    public AnalysisResponse() {
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


    public String getEdgeCases() {
        return edgeCases;
    }

    public void setEdgeCases(String edgeCases) {
        this.edgeCases = edgeCases;
    }
}