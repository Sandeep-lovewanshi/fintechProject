package fintech.project.controller;
import fintech.project.entity.Loan;
import fintech.project.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/loan")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @PostMapping("/add")
    public ResponseEntity<Loan> createLoan(@RequestBody Loan loan) {
        Loan savedLoan = loanService.addLoan(loan);
        return ResponseEntity.ok(savedLoan);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Loan>> getAllLoans() {
        return ResponseEntity.ok(loanService.getAllLoans());
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Loan> getLoanById(@PathVariable Long id) {
        Loan loan = loanService.getLoanById(id).orElse(null);
        if (loan != null) {
            return ResponseEntity.ok(loan);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Loan> updateLoan(@PathVariable Long id, @RequestBody Loan loan) {
        try {
            Loan updatedLoan = loanService.updateLoan(id, loan);
            return ResponseEntity.ok(updatedLoan);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
   // convert to loan
    @GetMapping("/convertEnquiryToLoan/{enquiryId}")
    public ResponseEntity<Loan> convert(@PathVariable Long enquiryId) {
        Loan loan = loanService.convertEnquiryToLoan(enquiryId);
        return ResponseEntity.ok(loan);
    }
       @GetMapping ("/getLoansByAgentId/{agentId}")
    public  ResponseEntity<List<Loan>>getAllLoanList(@PathVariable long agentId){
        List<Loan> loanList = loanService.getLoansByAgentId(agentId);
        return ResponseEntity.ok(loanList);
    }
    @GetMapping("/SearchBy")
    public ResponseEntity<List<Loan>> searchLoan(
            @RequestParam(required = false) Long agentId,
            @RequestParam(required = false) Long customerId,
            @RequestParam(required = false) String loanType,
            @RequestParam(required = false) Long providerId,
            @RequestParam( required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to


    ) {
        List<Loan> filtered = loanService.searchLoanByFields
                (agentId, customerId, loanType, providerId,from,to);
        return ResponseEntity.ok(filtered);
    }

    @GetMapping("/customerLoanDetail/{customerId}")
    public ResponseEntity<List<Loan>> getLoanDetail(@PathVariable Long customerId) {
        List<Loan> loans = loanService.loanDetail(customerId);
        return ResponseEntity.ok(loans);
    }

}
