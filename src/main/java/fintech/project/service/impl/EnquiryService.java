package fintech.project.service.impl;

import fintech.project.entity.Enquiry;
import fintech.project.entity.Loan;
import fintech.project.repository.EnquiryRepository;
import fintech.project.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnquiryService implements fintech.project.service.EnquiryService {

    @Autowired
    private  EnquiryRepository enquiryRepository;

    @Autowired
    private  LoanRepository loanRepository;

    @Override
    public Enquiry saveEnquiry(Enquiry enquiry) {
        return enquiryRepository.save(enquiry);
    }

    @Override
    public List<Enquiry> getAllEnquiries() {
        return enquiryRepository.findAll();
    }

    @Override
    public Optional<Enquiry> getEnquiryById(Long id) {
        return enquiryRepository.findById(id);
    }

    @Override
    public Enquiry updateEnquiry(Long id, Enquiry enquiryDetails) {
        Optional<Enquiry> optionalEnquiry = enquiryRepository.findById(id);

        if (optionalEnquiry.isPresent()) {
            Enquiry existingEnquiry = optionalEnquiry.get();
            existingEnquiry.setEnquiryDate(enquiryDetails.getEnquiryDate());
            existingEnquiry.setLoanType(enquiryDetails.getLoanType());
            existingEnquiry.setLoanAmount(enquiryDetails.getLoanAmount());
            existingEnquiry.setCustomerId(enquiryDetails.getCustomerId());

            return enquiryRepository.save(existingEnquiry);
        } else {
            throw new RuntimeException("Enquiry with ID " + id + " not found");
        }
    }

     @Override
     public Loan convertEnquiryToLoan(Long enquiryId, Loan requestLoan) {
         Optional<Enquiry> optionalEnquiry = enquiryRepository.findById(enquiryId);

         if (optionalEnquiry.isPresent()) {
             Enquiry enquiry = optionalEnquiry.get();

             Loan loan = new Loan();
             loan.setCustomerId(enquiry.getCustomerId());
             loan.setLoanType(enquiry.getLoanType());
             loan.setLoanAmount(enquiry.getLoanAmount());
             loan.setInterestRate(8.5);
             loan.setTermMonths(12);
             loan.setStartDate(enquiry.getEnquiryDate());
             loan.setEndDate(enquiry.getEnquiryDate().plusMonths(12));
             loan.setMonthlyEmi(calculateEmi(enquiry.getLoanAmount(), 8.5, 12));
             loan.setStatus("Pending");
             loan.setAgentId(requestLoan.getAgentId());
             loan.setProviderId(requestLoan.getProviderId());

             return loanRepository.save(loan);
         } else {
             throw new RuntimeException("Enquiry not found with id: " + enquiryId);
         }
     }
    private double calculateEmi(double principal, double annualRate, int months) {
        double totalInterest = (principal * annualRate * months) / (12 * 100);
        double totalAmount = principal + totalInterest;
        return totalAmount / months;
    }
}
