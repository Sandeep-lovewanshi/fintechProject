package fintech.project.service.impl;
import fintech.project.entity.Loan;
import fintech.project.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class LoanService implements fintech.project.service.LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Override
    public Loan addLoan(Loan loan) {
        return loanRepository.save(loan);
    }

    @Override
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    @Override
    public Optional<Loan> getLoanById(Long id) {
        return loanRepository.findById(id);
    }

    @Override
    public Loan updateLoan(Long id, Loan updatedLoan) {
        Optional<Loan> optionalLoan = loanRepository.findById(id);
        if (optionalLoan.isPresent()) {
            Loan existingLoan = optionalLoan.get();
            existingLoan.setAgentId(updatedLoan.getAgentId());
            existingLoan.setProviderId(updatedLoan.getProviderId());
            existingLoan.setCustomerId(updatedLoan.getCustomerId());
            existingLoan.setLoanType(updatedLoan.getLoanType());
            existingLoan.setLoanAmount(updatedLoan.getLoanAmount());
            existingLoan.setInterestRate(updatedLoan.getInterestRate());
            existingLoan.setTermMonths(updatedLoan.getTermMonths());
            existingLoan.setStartDate(updatedLoan.getStartDate());
            existingLoan.setEndDate(updatedLoan.getEndDate());
            existingLoan.setMonthlyEmi(updatedLoan.getMonthlyEmi());
            existingLoan.setStatus(updatedLoan.getStatus());
            // createdDate usually not updated manually
            return loanRepository.save(existingLoan);
        } else {
            throw new RuntimeException("Loan not found with id: " + id);
        }
    }
}
