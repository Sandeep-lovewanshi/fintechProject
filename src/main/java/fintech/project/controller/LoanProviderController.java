package fintech.project.controller;

import org.springframework.http.HttpStatus;
import fintech.project.entity.LoanProvider;
import fintech.project.service.LoanProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/loanprovider")
public class LoanProviderController {

    @Autowired
    private LoanProviderService loanProviderService;

    @PostMapping("/add")
    public ResponseEntity<?> addLoanProvider(@RequestBody LoanProvider loanProvider) {
        LoanProvider registeredLoanProvider = loanProviderService.addLoanProvider(loanProvider);
        return ResponseEntity.ok(registeredLoanProvider);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllLoanProviders() {
        List<LoanProvider> loanProviders = loanProviderService.getAllLoanProviders();
        return ResponseEntity.ok(loanProviders);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<?> getLoanProviderById(@PathVariable Long id) {
        Optional<LoanProvider> loanProvider = loanProviderService.getLoanProviderById(id);
        return ResponseEntity.ok(loanProvider);
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateLoanProvider(@PathVariable Long id, @RequestBody LoanProvider updatedLoanProvider) {
        try {
            LoanProvider updated = loanProviderService.updateLoanProvider(id, updatedLoanProvider);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.ok("not update");

        }
    }
    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteLoanProvider(@PathVariable Long id) {
        try {
            loanProviderService.deleteLoanProvider(id); // void method
            return ResponseEntity.ok("Deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Deletion failed: " + e.getMessage());
        }
    }
}

