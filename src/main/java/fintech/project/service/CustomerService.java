package fintech.project.service;

import fintech.project.entity.Customer;

public interface CustomerService  {

        Customer registerCustomer(Customer customer);
        Customer loginCustomer(String email, String password);
    }

