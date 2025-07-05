package fintech.project.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "offer")
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "agent_id", nullable = false)
    private Long agentId;

    @Column(name = "provider_id", nullable = false)
    private Long providerId;

    @Column(name = "enquiry_id", nullable = false)
    private Long enquiryId;

    @Column(name = "loan_type", nullable = false, length = 100)
    private String loanType;

    @Column(name = "loan_amount", nullable = false)
    private Double loanAmount;

    @Column(name = "interest_rate", nullable = false)
    private Double interestRate;

    @Column(name = "term_months", nullable = false)
    private Integer termMonths;

    @Column(name = "valid_from", nullable = false)
    private LocalDate validFrom;

    @Column(name = "valid_to", nullable = false)
    private LocalDate validTo;

    @Column(name = "status", nullable = false, length = 50)
    private String status;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "updated_date")
    private LocalDate updatedDate;

    @PrePersist
    protected void onCreate() {
        LocalDate now = LocalDate.now();
        this.createdDate = now;
        this.updatedDate = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedDate = LocalDate.now();
    }
}
