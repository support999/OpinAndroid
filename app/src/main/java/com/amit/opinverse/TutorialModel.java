package com.amit.opinverse;

public class TutorialModel {
    private String tutorialName, tutorialDescription, tutorialDuration;
    private int tutorialProgress, totalProgress;

    TutorialModel(String tutorialName, String tutorialDescription, String tutorialDuration, int tutorialProgress, int totalProgress){
        this.totalProgress = totalProgress;
        this.tutorialDescription = tutorialDescription;
        this.tutorialDuration = tutorialDuration;
        this.tutorialName = tutorialName;
        this.tutorialProgress = tutorialProgress;
    }

    TutorialModel(){

    }

    public int getTotalProgress() {
        return totalProgress;
    }

    public int getTutorialProgress() {
        return tutorialProgress;
    }

    public String getTutorialDescription() {
        return tutorialDescription;
    }

    public String getTutorialDuration() {
        return tutorialDuration;
    }

    public String getTutorialName() {
        return tutorialName;
    }
}
