package fintech.project.service.impl;

import fintech.project.entity.Customer;
import fintech.project.repository.CustomerRepository;
import fintech.project.repository.EnquiryRepository;
import fintech.project.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.beans.factory.annotation.Autowired;


import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EnquiryRepository enquiryRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public Customer loginCustomer(String email) {

        Optional<Customer> customerOpt = customerRepository.findByEmail(email);

        if (customerOpt.isEmpty()) {
            throw new RuntimeException("Customer not found.");
        }
        Customer customer = customerOpt.get();
        String otp = String.valueOf(new Random().nextInt(900000) + 100000);
        customer.setOtp(otp);
        customer.setOtpGeneratedTime(LocalDateTime.now());
        customerRepository.save(customer);
        sendOtpEmail(customer.getEmail(), otp);
        return customer;
    }
    private void sendOtpEmail(String toEmail, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Your OTP for Login");
        message.setText("Your OTP is: "+ otp + "\nIt is valid for 2 minutes.");
        mailSender.send(message);
    }


    public boolean submitVerifyOtp(String email, String otp) {
        Optional<Customer> customerOpt = customerRepository.findByEmail(email);

        if (customerOpt.isEmpty()) {
            throw new RuntimeException("Customer not found.");
        }
        Customer customer = customerOpt.get();
        if (!otp.equals(customer.getOtp())) {
            return false;
        }
        LocalDateTime generatedTime = customer.getOtpGeneratedTime();
        if (generatedTime == null || generatedTime.plusMinutes(2).isBefore(LocalDateTime.now())) {
            return false;
        }
        customer.setOtp(null);
        customer.setOtpGeneratedTime(null);
        customerRepository.save(customer);
        return true;
    }

}

