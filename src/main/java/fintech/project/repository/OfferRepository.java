package fintech.project.repository;

import fintech.project.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
    List<Offer> findByEnquiryId(Long enquiryId);

    List<Offer> findTop3ByLoanTypeIgnoreCaseAndStatus(String loanType, String status);

    List<Offer> findByStatus(String status);
    Optional<Offer> findByEnquiryIdAndStatus(Long enquiryId, String status);
}
