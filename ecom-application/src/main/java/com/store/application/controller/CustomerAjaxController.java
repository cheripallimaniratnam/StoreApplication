package com.store.application.controller;


import com.store.application.model.Customer;
import com.store.application.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerAjaxController {

    @Autowired
    private  CustomerRepository customerRepo;


    @GetMapping("/customers/findByMobile")
    public Customer findByMobile(@RequestParam String mobile) {
        return customerRepo.findByMobileNumber(mobile).orElse(new Customer());
    }
}
