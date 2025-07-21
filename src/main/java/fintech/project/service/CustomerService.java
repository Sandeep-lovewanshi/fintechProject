package fintech.project.service;

import fintech.project.entity.Customer;

public interface CustomerService  {

        Customer loginCustomer(String email);
        public boolean submitVerifyOtp(String email, String otp);
    }

