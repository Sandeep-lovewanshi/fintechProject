package fintech.project.service.impl;
import fintech.project.entity.LoanProvider;
import fintech.project.repository.LoanProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class LoanProviderServiceImpl implements fintech.project.service.LoanProviderService {

    @Autowired
    private LoanProviderRepository loanProviderRepository;

    @Override
    public LoanProvider addLoanProvider(LoanProvider loanProvider) {
        return loanProviderRepository.save(loanProvider);
    }

    @Override
    public List<LoanProvider> getAllLoanProviders() {
        return loanProviderRepository.findAll();
    }

    @Override
    public Optional<LoanProvider> getLoanProviderById(Long id) {
        return loanProviderRepository.findById(id);
    }

    @Override
    public LoanProvider updateLoanProvider(Long id, LoanProvider updatedLoanProvider) {
        Optional<LoanProvider> opLoanProvider = loanProviderRepository.findById(id);
        if (opLoanProvider.isPresent()) {
            LoanProvider existing = opLoanProvider.get();
            existing.setBankName(updatedLoanProvider.getBankName());
            existing.setCity(updatedLoanProvider.getCity());
            existing.setState(updatedLoanProvider.getState());
            return loanProviderRepository.save(existing);
        } else
            {
            throw new RuntimeException("LoanProvider not found with id: " + id);
        }
    }

    @Override
    public void deleteLoanProvider(Long id) {
        if (!loanProviderRepository.existsById(id)) {
            throw new RuntimeException("Loan provider not found with ID: " + id);
        }
        loanProviderRepository.deleteById(id);
    }
}
