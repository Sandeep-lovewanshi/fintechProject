package fintech.project.entity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.print.attribute.standard.MediaSize;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "loan")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "agent_id", nullable = false)
    private Long agentId;

    @Column(name = "loan_provider_id", nullable = false)
    private Long loanProviderId;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "enquiry_id", nullable = false)
    private Long enquiryId;

    @Column(name = "loan_type", nullable = false, length = 50)
    private String loanType;

    @Column(name = "loan_amount", nullable = false)
    private Double loanAmount;

    @Column(name = "interest_rate", nullable = false)
    private Double interestRate;

    @Column(name = "term_months", nullable = false)
    private Integer termMonths;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "monthly_emi", nullable = false)
    private Double monthlyEmi;

    @Column(nullable = false, length = 50)
    private String status;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "updated_date", nullable = false)
    private LocalDate updatedDate;

    @Transient
    private String bankName;

    @Transient
    private String agentName;
    @Transient
    private String agentPhone;

    @Transient
    private String customerName;

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
