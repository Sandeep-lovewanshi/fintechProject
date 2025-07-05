// File: CustomerController.java
package fintech.project.controller;

import fintech.project.entity.Customer;
import fintech.project.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;


    @PostMapping("/register")
    public ResponseEntity<?> registerCustomer(@RequestBody Customer customer) {
        Customer registeredCustomer = customerService.registerCustomer(customer);
        return ResponseEntity.ok(registeredCustomer);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginCustomer(@RequestParam String email,
                                           @RequestParam String password) {
        Customer loggedInCustomer = customerService.loginCustomer(email, password);
        return ResponseEntity.ok(loggedInCustomer);
    }
}
