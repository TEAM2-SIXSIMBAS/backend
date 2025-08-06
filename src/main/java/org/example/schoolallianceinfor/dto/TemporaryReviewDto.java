package org.example.schoolallianceinfor.dto;

public class TemporaryReviewDto {
    private String text;

    public TemporaryReviewDto() {}

    public TemporaryReviewDto(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
