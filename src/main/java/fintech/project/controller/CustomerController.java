// File: CustomerController.java
package fintech.project.controller;

import fintech.project.entity.Customer;
import fintech.project.service.CustomerService;
import fintech.project.service.EnquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private EnquiryService enquiryService;

    @GetMapping("/login")
    public ResponseEntity<?> loginCustomer(@RequestParam String email) {
        Customer loggedInCustomer = customerService.loginCustomer(email);

        return ResponseEntity.ok("OTP sent to your email. "+email);
    }
    @PostMapping("/submitVerifyOtp")
    public ResponseEntity<String> submitVerifyOtp(@RequestParam String email, @RequestParam String otp) {
        boolean isValid = customerService.submitVerifyOtp(email, otp);
        if (isValid) {
            return ResponseEntity.ok("OTP verified successfully.");
        } else {
            return ResponseEntity.status(401).body("Invalid OTP ");
        }
    }

}
