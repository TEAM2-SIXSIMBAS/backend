package org.example.schoolallianceinfor.dto;

import java.util.List;

public class ReviewSimpleDto {
    private List<String> photoUrl;
    private String text;

    public ReviewSimpleDto(List<String> photoUrl, String text) {
        this.photoUrl = photoUrl;
        this.text = text;
    }
    public List<String> getPhotoUrl() { return photoUrl; }
    public String getText() { return text; }
}
