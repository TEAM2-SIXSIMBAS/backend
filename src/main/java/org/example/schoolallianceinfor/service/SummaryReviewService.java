package org.example.schoolallianceinfor.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.schoolallianceinfor.dto.summary.ChatRequest;
import org.example.schoolallianceinfor.dto.summary.ChatResponse;
import org.example.schoolallianceinfor.dto.summary.Message;
import org.example.schoolallianceinfor.entity.Partnership;
import org.example.schoolallianceinfor.entity.Review;
import org.example.schoolallianceinfor.repository.PartnershipRepository;
import org.example.schoolallianceinfor.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SummaryReviewService {

    private final PartnershipRepository partnershipRepository;

    public String getCachedSummary(Integer partnershipId) {
        Partnership p = partnershipRepository.findById(partnershipId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 제휴입니다."));
        return (p.getReviewSummary() != null)
                ? p.getReviewSummary()
                : "아직 요약이 생성되지 않았습니다.";
    }

    /** 매일 자정 실행 */
    @Scheduled(cron = "0 0 0 * * *")
    public void summarizeAllPartnerships() {
        List<Partnership> partnerships = partnershipRepository.findAll();

        for (Partnership p : partnerships) {
            updatePartnershipSummary(p.getPartnershipId());
        }
    }

    public void updatePartnershipSummary(Integer partnershipId) {
        Partnership p = partnershipRepository.findById(partnershipId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 제휴입니다."));

        String summary = summarizeFiveReviewsByPartnership(partnershipId);
        p.setReviewSummary(summary);
        p.setLastSummarizedDate(LocalDate.now());

        partnershipRepository.save(p);
    }

    private static final int MIN_REVIEWS = 5;
    private static final String NO_SUMMARY_MSG = "리뷰 요약이 존재하지 않습니다 (리뷰가 5개 미만).";

    private final ReviewRepository reviewRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${openai.api.key}")
    private String API_KEY;

    @Value("${openai.api.url}")
    private String API_URL;

    /** 특정 제휴ID의 리뷰 중 최대 5개로 요약. 5개 미만이면 안내 문구 반환. */
    public String summarizeFiveReviewsByPartnership(Integer partnershipId) {
        // 1) 해당 제휴의 리뷰 텍스트만 조회 (불필요한 전체 조회 제거)
        List<String> texts = reviewRepository
                .findAllByPartnership_PartnershipIdOrderByReviewIdDesc(partnershipId)
                .stream()
                .map(Review::getText)
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toCollection(ArrayList::new));

        // 2) 5개 미만이면 예외 대신 안내 문구 반환
        if (texts.size() < MIN_REVIEWS) {
            return NO_SUMMARY_MSG;
        }

        // 3) 최신순 5개 선택 (reviewId 내림차순 정렬된 상태)
        List<String> selectedReviews = texts.subList(0, MIN_REVIEWS);

        // 4) 프롬프트 생성 (실제 개수 표기)
        String prompt = buildPrompt(selectedReviews);

        // 5) OpenAI 호출 (에러는 모두 안내 문구로 폴백)
        try {
            List<Message> messages = List.of(
                    new Message("system", "당신은 여러 사용자 리뷰를 요약하는 전문가입니다. 공통된 특징과 핵심 내용을 간결하게 정리해 주세요."),
                    new Message("user", prompt)
            );

            ChatRequest chatRequest = new ChatRequest("gpt-3.5-turbo", messages);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(API_KEY);

            HttpEntity<ChatRequest> request = new HttpEntity<>(chatRequest, headers);
            ResponseEntity<ChatResponse> response =
                    restTemplate.exchange(API_URL, HttpMethod.POST, request, ChatResponse.class);

            ChatResponse body = response.getBody();
            if (body == null || body.getChoices() == null || body.getChoices().isEmpty()
                    || body.getChoices().get(0).getMessage() == null
                    || body.getChoices().get(0).getMessage().getContent() == null) {
                return "리뷰 요약 생성 실패: 응답이 비어 있습니다.";
            }

            return body.getChoices().get(0).getMessage().getContent().strip();

        } catch (Exception e) {
            // 네트워크/인증 등 모든 오류에서 서비스 전체가 깨지지 않도록 폴백
            return "리뷰 요약 생성 실패: " + e.getMessage();
        }
    }

    private String buildPrompt(List<String> reviews) {
        StringBuilder sb = new StringBuilder();
        sb.append("다음은 사용자 리뷰 ").append(reviews.size()).append("개입니다. ")
                .append("리뷰들을 기반으로 공통된 핵심 내용을 3~5문장으로 간결하게 요약해 주세요.\n\n");
        for (int i = 0; i < reviews.size(); i++) {
            sb.append("리뷰 ").append(i + 1).append(": ").append(reviews.get(i)).append("\n\n");
        }
        sb.append("요약:");
        return sb.toString();
    }
}
