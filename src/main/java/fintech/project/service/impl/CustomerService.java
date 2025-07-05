package fintech.project.service.impl;

import fintech.project.entity.Customer;
import fintech.project.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService implements fintech.project.service.CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer registerCustomer(Customer customer) {

        Optional<Customer> existingCustomer = customerRepository.findByEmail(customer.getEmail());
        if (existingCustomer.isPresent()) {
            throw new RuntimeException("Customer with this email already exists.");
        }

        return customerRepository.save(customer);
    }
    @Override
    public Customer loginCustomer(String email, String password) {

        Optional<Customer> customerOpt = customerRepository.findByEmail(email);

        if (customerOpt.isEmpty()) {
            throw new RuntimeException("Customer not found.");
        }

        Customer customer = customerOpt.get();

        if (!customer.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password.");
        }

        return customer;
    }

}
