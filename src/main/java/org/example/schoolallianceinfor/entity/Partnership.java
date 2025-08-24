package org.example.schoolallianceinfor.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Partnership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer partnershipId;

    @Column(name = "content", length = 500)
    private String content;

    @Column(name = "target", length = 500)
    private String target;

    @Column(name = "type", length = 500)
    private String type;

    @Column(name = "discountRate")  // 정수 length 제거
    private Integer discountRate;

    @Column(name = "saleStartDate")
    private LocalDate saleStartDate;

    @Column(name = "saleEndDate")
    private LocalDate saleEndDate;

    @Column(name = "useStartDate")
    private LocalDate useStartDate;

    @Column(name = "useEndDate")
    private LocalDate useEndDate;

    @Column(name = "note", length = 500)
    private String note;

    @Builder.Default
    @Column(name = "views", nullable = false)
    private Integer views = 0;

    /** 단일 이미지 URL */
    @Column(name = "partnershipImageUrl", length = 1000)
    private String partnershipImageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")  // FK
    private Store store;

    @OneToMany(mappedBy = "partnership", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Review> reviews = new ArrayList<>();
}
