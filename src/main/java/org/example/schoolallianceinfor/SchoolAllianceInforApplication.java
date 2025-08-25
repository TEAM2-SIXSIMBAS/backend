package org.example.schoolallianceinfor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;  // ✅ 추가

@SpringBootApplication
@EnableScheduling   // ✅ 스케줄링 활성화
public class SchoolAllianceInforApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchoolAllianceInforApplication.class, args);
    }

}
