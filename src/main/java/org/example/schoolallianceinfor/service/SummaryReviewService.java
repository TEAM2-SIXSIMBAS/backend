package org.example.schoolallianceinfor.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.schoolallianceinfor.dto.ChatRequest;
import org.example.schoolallianceinfor.dto.ChatResponse;
import org.example.schoolallianceinfor.dto.Message;
import org.example.schoolallianceinfor.entity.TemporaryReview;
import org.example.schoolallianceinfor.repository.TemporaryReviewRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SummaryReviewService {

    //저장 기능
    public void saveReview(String text) {
        TemporaryReview review = new TemporaryReview();
        review.setText(text);
        reviewRepository.save(review);
    }


    private final TemporaryReviewRepository reviewRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${openai.api.key}")
    private String API_KEY;

    @Value("${openai.api.url}")
    private String API_URL;

    public String summarizeFiveReviews() {
        List<TemporaryReview> allReviews = reviewRepository.findAll();

        if (allReviews.size() < 5) {
            throw new IllegalStateException("리뷰가 5개 이상 필요합니다.");
        }

        // 리뷰 랜덤으로 섞고 5개 선택
        Collections.shuffle(allReviews, new Random());
        List<String> selectedReviews = allReviews.stream()
                .limit(5)
                .map(TemporaryReview::getText)
                .collect(Collectors.toList());

        // 프롬프트 생성
        String prompt = buildPrompt(selectedReviews);

        // ChatGPT 요청 메시지 구성
        List<Message> messages = List.of(
                new Message("system", "당신은 여러 사용자 리뷰를 요약하는 전문가입니다. 공통된 특징과 핵심 내용을 간결하게 정리해 주세요."),
                new Message("user", prompt)
        );

        ChatRequest chatRequest = new ChatRequest("gpt-3.5-turbo", messages);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(API_KEY);

        HttpEntity<ChatRequest> request = new HttpEntity<>(chatRequest, headers);
        ResponseEntity<ChatResponse> response = restTemplate.exchange(API_URL, HttpMethod.POST, request, ChatResponse.class);

        return response.getBody().getChoices().get(0).getMessage().getContent().strip();
    }

    private String buildPrompt(List<String> reviews) {
        StringBuilder sb = new StringBuilder();
        sb.append("다음은 사용자 리뷰 5개입니다. 리뷰들을 기반으로 공통된 핵심 내용을 요약해 주세요.\n\n");

        for (int i = 0; i < reviews.size(); i++) {
            sb.append("리뷰 ").append(i + 1).append(": ").append(reviews.get(i)).append("\n\n");
        }

        sb.append("요약:");
        return sb.toString();
    }
}
