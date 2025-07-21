package fintech.project.controller;
import fintech.project.entity.Customer;
import fintech.project.entity.CustomerEnquiry;
import fintech.project.entity.Enquiry;
import fintech.project.entity.Loan;
import fintech.project.service.CustomerService;
import fintech.project.service.EnquiryService;
import fintech.project.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/enquiry")
public class EnquiryController {

    @Autowired
    private EnquiryService enquiryService;

    @Autowired
    private LoanService loanService;

    @PostMapping("/createEnquiryWithCustomer")
    public ResponseEntity<?> createEnquiryWithCustomer(@RequestBody CustomerEnquiry customerEnqyuiry) {
        CustomerEnquiry customerEnquiryResponse = enquiryService.createEnquiryWithCustomer(customerEnqyuiry);

        return ResponseEntity.ok(customerEnquiryResponse);
    }

    @GetMapping("/getAllEnquiry")
    public ResponseEntity<List<Enquiry>> getAllEnquiries() {
        return ResponseEntity.ok(enquiryService.getAllEnquiries());
    }

    @GetMapping("/SearchBy")
    public ResponseEntity<List<Enquiry>> searchByField(
            @RequestParam(required = false) Long customerId,
            @RequestParam(required = false) String loanType,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to
    ) {
        List<Enquiry> result = enquiryService.searchByField(customerId, loanType,from,to);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/getById/{id}")
    public ResponseEntity<Enquiry> getEnquiryById(@PathVariable Long id) {
        Enquiry enquiry = enquiryService.getEnquiryById(id).orElse(null);
        if (enquiry != null) {
            return ResponseEntity.ok(enquiry);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Enquiry> updateEnquiry(@PathVariable Long id, @RequestBody Enquiry enquiry) {
        try {
            Enquiry updatedEnquiry = enquiryService.updateEnquiry(id, enquiry);
            return ResponseEntity.ok(updatedEnquiry);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    // not use
    @GetMapping("/customerStatus/{customerId}")
    public ResponseEntity<List<String>> getStatusByCustomer(@PathVariable Long customerId) {
        List<String> statusList = enquiryService.getStatusByCustomerId(customerId);
        return ResponseEntity.ok(statusList);
    }
//
    @GetMapping("/customer/getMyEnquiry/{customerId}")
    public ResponseEntity<List<Enquiry>> getCustomerEnquiries(@PathVariable Long customerId) {
        List<Enquiry> enquiries = enquiryService.getCustomerEnquiry(customerId);
        return ResponseEntity.ok(enquiries);
    }
}

