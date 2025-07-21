package fintech.project.service.impl;
import fintech.project.entity.*;
import fintech.project.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LoanServiceImpl implements fintech.project.service.LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private EnquiryRepository enquiryRepository;

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private LoanProviderRepository loanProviderRepository;

   @Autowired
   private AgentRepository agentRepository;

   @Autowired
   private CustomerRepository customerRepository;

    @Override
    public Loan addLoan(Loan loan) {
        return loanRepository.save(loan);
    }

    @Override
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    @Override
    public Optional<Loan> getLoanById(Long id) {
        return loanRepository.findById(id);
    }

    @Override
    public Loan updateLoan(Long id, Loan updatedLoan) {
        Optional<Loan> optionalLoan = loanRepository.findById(id);
        if (optionalLoan.isPresent()) {
            Loan existingLoan = optionalLoan.get();
            existingLoan.setAgentId(updatedLoan.getAgentId());
            existingLoan.setLoanProviderId(updatedLoan.getLoanProviderId());
            existingLoan.setCustomerId(updatedLoan.getCustomerId());
            existingLoan.setLoanType(updatedLoan.getLoanType());
            existingLoan.setLoanAmount(updatedLoan.getLoanAmount());
            existingLoan.setInterestRate(updatedLoan.getInterestRate());
            existingLoan.setTermMonths(updatedLoan.getTermMonths());
            existingLoan.setStartDate(updatedLoan.getStartDate());
            existingLoan.setEndDate(updatedLoan.getEndDate());
            existingLoan.setMonthlyEmi(updatedLoan.getMonthlyEmi());
            existingLoan.setStatus(updatedLoan.getStatus());
            // createdDate usually not updated manually
            return loanRepository.save(existingLoan);
        } else {
            throw new RuntimeException("Loan not found with id: " + id);
        }
    }

    @Override
    public List<Loan> getLoansByAgentId(Long agentId) {
        return loanRepository.findByAgentId(agentId);
    }

    @Transactional
    public Loan convertEnquiryToLoan(Long enquiryId) {
        Optional<Enquiry> optionalEnquiry = enquiryRepository.findById(enquiryId);
        Enquiry enquiry;
        if (optionalEnquiry.isPresent()) {
            enquiry = optionalEnquiry.get();
        } else {
            throw new RuntimeException("Enquiry not found");
        }
        Optional<Offer> optionalOffer = offerRepository.findByEnquiryIdAndStatus(enquiryId, "SELECTED");
        Offer offer;
        if (optionalOffer.isPresent()) {
            offer = optionalOffer.get();
        } else {
            throw new RuntimeException("No SELECTED offer");
        }
        Long providerId = offer.getLoanProviderId();

        if (providerId == null) {
            throw new RuntimeException("Loan Provider ID is missing in offer");
        }
        LoanProvider loanProvider = loanProviderRepository.findById(offer.getLoanProviderId())
                .orElseThrow(() -> new RuntimeException("Loan Provider not found with ID: " + providerId));
        Loan loan = new Loan();
        loan.setAgentId(loanProvider.getAgentId());
        loan.setLoanProviderId(loanProvider.getId());
        loan.setCustomerId(enquiry.getCustomerId());
        loan.setLoanType(enquiry.getLoanType());
        loan.setLoanAmount(enquiry.getLoanAmount());
        loan.setInterestRate(offer.getInterestRate());
        loan.setTermMonths(offer.getTermMonths());
        loan.setStartDate(LocalDate.now());
        loan.setEndDate(LocalDate.now().plusMonths(offer.getTermMonths()));
        loan.setMonthlyEmi(calculateEmi(loan.getLoanAmount(), loan.getInterestRate(), loan.getTermMonths()));
        loan.setStatus("ACTIVE");
        loan.setEnquiryId(offer.getEnquiryId());
        loan.setCreatedDate(LocalDate.now());
        loan.setUpdatedDate(LocalDate.now());
        loanRepository.save(loan);
        enquiry.setStatus("ConvertedToLoan");
        enquiryRepository.save(enquiry);
        offer.setStatus("APPROVED");
        offerRepository.save(offer);
        return loan;
    }
    private double calculateEmi(double principal, double annualRate, int months) {
        double totalInterest = (principal * annualRate * months) / (12 * 100);
        double totalAmount = principal + totalInterest;
        return totalAmount / months;

    }

    public List<Loan> searchLoanByFields
            (Long agentId, Long customerId, String loanType, Long loanProviderId,
             LocalDate from,LocalDate to ) {
        List<Loan> all = loanRepository.findAll();
        List<Loan> result = new ArrayList<>();
        for (Loan l : all) {
            if (agentId != null && !agentId.equals(l.getAgentId())) continue;
            if (customerId != null && !customerId.equals(l.getCustomerId())) continue;
            if (loanType != null && !loanType.equalsIgnoreCase(l.getLoanType())) continue;
            if (loanProviderId != null && !loanProviderId.equals(l.getLoanProviderId())) continue;
           LocalDate created=l.getCreatedDate();
           if(from!=null && created.isBefore(from))continue;
           if(to!=null && created.isAfter(to)) continue;
           result.add(l);
        }
        return result;
    }

    public List<Loan> loanDetail(Long customerId) {
        List<Loan> loans = loanRepository.findByCustomerId(customerId);

        if (loans.isEmpty()) {
            throw new RuntimeException("Loan not found for customerId: " + customerId);
        }

        List<Loan> result = new ArrayList<>();

        for (Loan loan : loans) {
            Loan view = new Loan();
            view.setLoanType    (loan.getLoanType());
            view.setLoanAmount  (loan.getLoanAmount());
            view.setInterestRate(loan.getInterestRate());
            view.setTermMonths  (loan.getTermMonths());
            view.setMonthlyEmi  (loan.getMonthlyEmi());
            view.setStatus      (loan.getStatus());
            view.setStartDate   (loan.getStartDate());
            view.setEndDate     (loan.getEndDate());

            loanProviderRepository.findById(loan.getLoanProviderId()).ifPresent(loanProvider ->
                    view.setBankName(loanProvider.getBankName())
            );

            agentRepository.findById(loan.getAgentId()).ifPresent(agent -> {
                view.setAgentName(agent.getName());
                view.setAgentPhone(agent.getPhoneNumber());
            });

            customerRepository.findById(loan.getCustomerId()).ifPresent(customer ->
                    view.setCustomerName(customer.getName())
            );

            result.add(view);
        }

        return result;
    }

}

