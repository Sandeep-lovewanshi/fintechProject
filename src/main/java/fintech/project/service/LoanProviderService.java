package fintech.project.service;
import fintech.project.entity.LoanProvider;
import java.util.List;
import java.util.Optional;

public interface LoanProviderService {

    LoanProvider addLoanProvider(LoanProvider loanProvider);

    List<LoanProvider> getAllLoanProviders();

    Optional<LoanProvider> getLoanProviderById(Long id);

    LoanProvider updateLoanProvider(Long id, LoanProvider loanProvider);

    void deleteLoanProvider(Long id);
}
