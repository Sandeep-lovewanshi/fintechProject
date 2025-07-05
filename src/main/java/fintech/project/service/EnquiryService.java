package fintech.project.service;

import fintech.project.entity.Enquiry;
import fintech.project.entity.Loan;

import java.util.List;
import java.util.Optional;

public interface EnquiryService {

    Enquiry saveEnquiry(Enquiry enquiry);

    List<Enquiry> getAllEnquiries();

    Optional<Enquiry> getEnquiryById(Long id);

    Enquiry updateEnquiry(Long id, Enquiry enquiryDetails);

    Loan convertEnquiryToLoan(Long enquiryId, Loan loan);

}
