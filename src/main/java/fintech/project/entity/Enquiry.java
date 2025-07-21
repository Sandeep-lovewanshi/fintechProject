package fintech.project.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "enquiry")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Enquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "loan_type", nullable = false, length = 100)
    private String loanType;

    @Column(name = "status", nullable = false)
    private String status = "new";

    @Column(name = "loan_amount", nullable = false)
    private Double loanAmount;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "updated_date")
    private LocalDate updatedDate;

    @PrePersist
    protected void onCreate() {
        LocalDate now = LocalDate.now();
        this.createdDate = now;
        this.updatedDate = now;
       // this.enquiryDate=now;
    }


    @PreUpdate
    protected void onUpdate() {
        this.updatedDate = LocalDate.now();
    }
}

