package com.udacity.jdnd.course3.critter.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    public Long save(Customer customer){
        return customerRepository.save(customer).getId();
    }

    public Customer findCustomerById(Long id){
        return customerRepository.findById(id).orElse(null);
    }

    public List<Customer> findAllCustomers(){
        return customerRepository.findAll();
    }
}
