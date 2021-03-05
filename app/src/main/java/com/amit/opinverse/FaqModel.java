package com.amit.opinverse;

import java.util.List;

public class FaqModel {
    private String faqHead;
    private List<FaqQuestionModel> faqQuestionModel;
    FaqModel(String faqHead, List<FaqQuestionModel> faqQuestionModel){
        this.faqHead = faqHead;
        this.faqQuestionModel = faqQuestionModel;
    }
    public String getFaqHead() {
        return faqHead;
    }

    public List<FaqQuestionModel> getFaqQuestionModel() {
        return faqQuestionModel;
    }
}
