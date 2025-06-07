package com.amina.jobnotifier.model;

public class EmailAnalysisResult {
    private boolean jobRelated;
    private boolean rejection;
    private String companyName;

    public boolean isJobRelated() {
        return jobRelated;
    }

    public void setJobRelated(boolean jobRelated) {
        this.jobRelated = jobRelated;
    }

    public boolean isRejection() {
        return rejection;
    }

    public void setRejection(boolean rejection) {
        this.rejection = rejection;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
