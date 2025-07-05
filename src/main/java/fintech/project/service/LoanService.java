package fintech.project.service;
import fintech.project.entity.Loan;
import java.util.List;
import java.util.Optional;

public interface LoanService {

    Loan addLoan(Loan loan);

    List<Loan> getAllLoans();

    Optional<Loan> getLoanById(Long id);

    Loan updateLoan(Long id, Loan loan);

}
