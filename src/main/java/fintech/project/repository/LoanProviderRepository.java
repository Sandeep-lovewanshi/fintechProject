package fintech.project.repository;
import fintech.project.entity.LoanProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface LoanProviderRepository extends JpaRepository<LoanProvider, Long> {

}
