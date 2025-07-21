package fintech.project.service;
import fintech.project.entity.Enquiry;
import fintech.project.entity.Loan;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LoanService {

    Loan addLoan(Loan loan);

    List<Loan> getAllLoans();

    Optional<Loan> getLoanById(Long id);

    Loan updateLoan(Long id, Loan loan);

    List<Loan> getLoansByAgentId(Long agentId);

    Loan convertEnquiryToLoan(Long enquiryId);

    List<Loan> searchLoanByFields
            (Long agentId, Long customerId, String loanType, Long providerId, LocalDate createdDate,LocalDate updatedDate);

    List<Loan> loanDetail(Long customerId);
}
