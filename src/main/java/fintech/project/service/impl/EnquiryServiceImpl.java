package fintech.project.service.impl;

import fintech.project.entity.Customer;
import fintech.project.entity.CustomerEnquiry;
import fintech.project.entity.Enquiry;
import fintech.project.repository.CustomerRepository;
import fintech.project.repository.EnquiryRepository;
import fintech.project.repository.LoanRepository;
import fintech.project.service.EnquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EnquiryServiceImpl implements EnquiryService {

    @Autowired
    private  EnquiryRepository enquiryRepository;

    @Autowired
    private  LoanRepository loanRepository;

    @Autowired
    private CustomerRepository customerRepository;

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
           // existingEnquiry.setEnquiryDate(enquiryDetails.getEnquiryDate());
            existingEnquiry.setLoanType(enquiryDetails.getLoanType());
            existingEnquiry.setLoanAmount(enquiryDetails.getLoanAmount());
            existingEnquiry.setCustomerId(enquiryDetails.getCustomerId());

            return enquiryRepository.save(existingEnquiry);
        } else {
            throw new RuntimeException("Enquiry with ID " + id + " not found");
        }
    }

    public List<Enquiry> searchByField(Long customerId, String loanType, LocalDate from, LocalDate to) {
        List<Enquiry> all = enquiryRepository.findAll();
        List<Enquiry> result = new ArrayList<>();
        for (Enquiry e : all) {
           if (customerId != null && !customerId.equals(e.getCustomerId())) continue;
           if (loanType != null && !loanType.equalsIgnoreCase(e.getLoanType())) continue;
           LocalDate created = e.getCreatedDate();
            if (from != null && created.isBefore(from)) continue;
            if (to   != null && created.isAfter(to))   continue;
            result.add(e);
        }

        return result;
    }
  //not use
    public List<String> getStatusByCustomerId(Long customerId) {
        List<Enquiry> enquiries = enquiryRepository.findByCustomerId(customerId);
        List<String> statusList = new ArrayList<>();
        for (Enquiry e : enquiries) {
            statusList.add(e.getStatus());
        }
        return statusList;
    }
    public List<Enquiry> getCustomerEnquiry(Long customerId) {
        List<Enquiry> enquiryList = enquiryRepository.findByCustomerId(customerId);

        if (enquiryList.isEmpty()) {
            throw new RuntimeException("enquiry not found for customerId: " + customerId);
        }
        List<Enquiry> result = new ArrayList<>();
       for(Enquiry e:enquiryList){
           Enquiry enquiry=new Enquiry();
           enquiry.setId(e.getId());
           enquiry.setLoanType    (e.getLoanType());
           enquiry.setLoanAmount  (e.getLoanAmount());
           enquiry.setStatus(e.getStatus());
           enquiry.setCreatedDate(e.getCreatedDate());
          /* customerRepository.findById(e.getCustomerId()).ifPresent(customer ->
                   enquiry.setCustomerName(customer.getName())
           );*/

           result.add(enquiry);
       }
        return result;
    }
    @Override
    public CustomerEnquiry createEnquiryWithCustomer(CustomerEnquiry customerEnquiry) {
        Optional<Customer> existingCustomer = customerRepository.findByEmail(customerEnquiry.getEmail());

        Customer savedCustomer = null;
        if (existingCustomer.isPresent()) {
            savedCustomer = existingCustomer.get();
            savedCustomer.setName(customerEnquiry.getCustomerName());
            savedCustomer.setCity(customerEnquiry.getCity());
            savedCustomer.setAddress(customerEnquiry.getAddress());
            savedCustomer.setEmployment(customerEnquiry.getEmployment());
            savedCustomer.setPhoneNumber(customerEnquiry.getPhoneNumber());
            savedCustomer.setState(customerEnquiry.getState());
            savedCustomer.setAnnualIncome(customerEnquiry.getAnnualIncome());
            savedCustomer = customerRepository.save(savedCustomer);
        } else {
            Customer customer = new Customer();
            customer.setEmail(customerEnquiry.getEmail());
            customer.setName(customerEnquiry.getCustomerName());
            customer.setCity(customerEnquiry.getCity());
            customer.setAddress(customerEnquiry.getAddress());
            customer.setEmployment(customerEnquiry.getEmployment());
            customer.setPhoneNumber(customerEnquiry.getPhoneNumber());
            customer.setState(customerEnquiry.getState());
            customer.setAnnualIncome(customerEnquiry.getAnnualIncome());
            savedCustomer = customerRepository.save(customer);
        }
        Enquiry enquiry = new Enquiry();
        enquiry.setCustomerId(savedCustomer.getId());
        enquiry.setLoanType(customerEnquiry.getLoanType());
        enquiry.setLoanAmount(customerEnquiry.getLoanAmount());
        enquiry.setStatus("New");
        Enquiry createdEnquiry = enquiryRepository.save(enquiry);
        customerEnquiry.setCustomerId(savedCustomer.getId());
        customerEnquiry.setEnquiryId(createdEnquiry.getId());
        return customerEnquiry;
    }


}


