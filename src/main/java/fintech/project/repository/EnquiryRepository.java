package fintech.project.repository;

import fintech.project.entity.Enquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EnquiryRepository extends JpaRepository<Enquiry, Long> {
    List<Enquiry> findByCreatedDateBetween(LocalDate from, LocalDate to);
    List<Enquiry> findByCustomerId(Long customerId);

}
