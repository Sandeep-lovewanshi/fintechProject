package fintech.project.service;

import fintech.project.entity.Customer;
import fintech.project.entity.CustomerEnquiry;
import fintech.project.entity.Enquiry;
import fintech.project.entity.Loan;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EnquiryService {

    Enquiry saveEnquiry(Enquiry enquiry);

    List<Enquiry> getAllEnquiries();

    Optional<Enquiry> getEnquiryById(Long id);

    Enquiry updateEnquiry(Long id, Enquiry enquiryDetails);

    List<Enquiry> searchByField
            (Long customerId, String loanType, LocalDate from, LocalDate to);

    List<String> getStatusByCustomerId(Long customerId);

    List<Enquiry> getCustomerEnquiry(Long customerId);

    CustomerEnquiry createEnquiryWithCustomer(CustomerEnquiry customer);
}
