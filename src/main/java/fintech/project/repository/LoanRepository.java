package fintech.project.repository;
import fintech.project.entity.Enquiry;
import fintech.project.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByAgentId(Long agentId);
    List<Loan> findByCustomerId(Long customerId);

}
