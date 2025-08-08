package org.example.schoolallianceinfor.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Partnership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer partnershipId;

    private String storeName;

    @Column(length = 500)
    private String content;

    private String target;

    private String type;

    private Integer discountRate;

    private LocalDate saleStartDate;
    private LocalDate saleEndDate;
    private LocalDate useStartDate;
    private LocalDate useEndDate;


    @Column(length = 1000)
    private String note;

    private Integer views;
}
