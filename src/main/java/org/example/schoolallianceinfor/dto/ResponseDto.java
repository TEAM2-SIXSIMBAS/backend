package org.example.schoolallianceinfor.dto;

public class ResponseDto {
    private String answer;

    public ResponseDto(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}