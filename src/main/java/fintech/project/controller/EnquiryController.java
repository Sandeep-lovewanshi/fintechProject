package fintech.project.controller;
import fintech.project.entity.Enquiry;
import fintech.project.entity.Loan;
import fintech.project.service.EnquiryService;
import fintech.project.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/enquiry")
public class EnquiryController {

    @Autowired
    private EnquiryService enquiryService;

    @Autowired
    private LoanService loanService;

    @PostMapping("/add")
    public ResponseEntity<Enquiry> createEnquiry(@RequestBody Enquiry enquiry) {
        Enquiry savedEnquiry = enquiryService.saveEnquiry(enquiry);
        return ResponseEntity.ok(savedEnquiry);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Enquiry>> getAllEnquiries() {
        return ResponseEntity.ok(enquiryService.getAllEnquiries());
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

    @PostMapping("/convertToLoan/{enquiryId}")
    public ResponseEntity<Loan> convertEnquiryToLoan
            (@PathVariable Long enquiryId, @RequestBody Loan requestedLoan) {
        try {
            Loan loan = enquiryService.convertEnquiryToLoan(enquiryId, requestedLoan);
            return ResponseEntity.ok(loan);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}

