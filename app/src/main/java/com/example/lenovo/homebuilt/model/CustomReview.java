package com.example.lenovo.homebuilt.model;


public class CustomReview {
    private int image;
    private String feedback;

    public CustomReview(int image, String feedback) {
        this.image = image;
        this.feedback = feedback;
    }

    public int getImage() {
        return image;
    }

    public String getFeedback() {
        return feedback;
    }
}
