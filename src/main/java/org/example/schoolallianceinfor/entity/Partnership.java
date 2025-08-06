package org.example.schoolallianceinfor.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Partnership {
    @Id
    @GeneratedValue
    private Integer partnershipId;

    @ManyToOne
    @JoinColumn(name="store_id")
    private Store store;

    private String content;
    private String type;
    private Integer discountRate;
    private LocalDate saleStartDate;
    private LocalDate saleEndDate;
    private LocalDate useStartDate;
    private LocalDate useEndDate;
    private String note;
    private Integer views;
}
