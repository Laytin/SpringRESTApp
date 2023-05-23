package com.laytin.SpringRESTApp.services;

import com.laytin.SpringRESTApp.models.Customer;
import com.laytin.SpringRESTApp.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public CustomerService(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }



    @Transactional
    public void register(Customer c) {
        c.setPassword(passwordEncoder.encode(c.getPassword()));
        customerRepository.save(c);
    }

    public void edit(int id,Customer c) {
        Customer edited = customerRepository.findById(id).get();
    }

    public Customer get(int id) {
        return customerRepository.findById(id).get();
    }
    public Customer get(String username) {
        return customerRepository.findByUsername(username).get();
    }
}
