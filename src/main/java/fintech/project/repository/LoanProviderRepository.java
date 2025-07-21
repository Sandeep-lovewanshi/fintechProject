package fintech.project.repository;
import fintech.project.entity.LoanProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanProviderRepository extends JpaRepository<LoanProvider, Long> {

  Optional <LoanProvider> findByBankName(String name);
    Optional<LoanProvider>findById(long id);
}
