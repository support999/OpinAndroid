package com.amit.opinverse;

public class AssignmentModel {
    private String assgnName, assgnSubName, assgnText;
    private int progress, assgnCertificate;

    AssignmentModel(){

    }

    AssignmentModel(String assgnName, String assgnSubName, String assgnText, int progress, int assgnCertificate){
        this.assgnName = assgnName;
        this.assgnSubName = assgnSubName;
        this.assgnText = assgnText;
        this.progress = progress;
        this.assgnCertificate = assgnCertificate;
    }

    public int getAssgnCertificate() {
        return assgnCertificate;
    }

    public int getProgress() {
        return progress;
    }

    public String getAssgnName() {
        return assgnName;
    }

    public String getAssgnSubName() {
        return assgnSubName;
    }

    public String getAssgnText() {
        return assgnText;
    }
}
