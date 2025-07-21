package fintech.project.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "customer")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(unique = true, nullable = false, length = 150)
    private String email;

    @Column(nullable = false, length = 100)
    private String phoneNumber;

    @Column(length = 100)
    private String address;

    @Column(length = 100)
    private String city;

    @Column(length = 100)
    private String state;

    @Column(nullable = false, length = 100)
    private String employment;

    @Column(name = "annual_income")
    private Double annualIncome;

    @Column(name = "credit_score", nullable = false)
    private Integer creditScore;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "updated_date", nullable = false)
    private LocalDate updatedDate;

    @Column(name = "otp", length = 6)
    private String otp;

    @Column(name = "otp_generated_time")
    private LocalDateTime otpGeneratedTime;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdDate = now.toLocalDate();
        this.updatedDate = now.toLocalDate();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedDate = LocalDate.now();
    }
}
