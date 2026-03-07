package com.store.application.service;

import com.store.application.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepo;
    public long getTotalCustomers() {
        return customerRepo.count();
    }
}
