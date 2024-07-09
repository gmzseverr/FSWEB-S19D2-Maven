package com.workintech.s18d4.service;


import com.workintech.s18d4.entity.Customer;
import com.workintech.s18d4.exception.CustomerException;
import com.workintech.s18d4.repository.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService{

    private CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer find(Long id) {
        Optional<Customer> optionalCustomer= customerRepository.findById(id);
        if(optionalCustomer.isPresent()){
           return optionalCustomer.get();
        }
        throw new CustomerException("Customer with given id is not exist: "+id, HttpStatus.NOT_FOUND);
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer delete(Long id) {
        Customer customer=find(id);
        customerRepository.delete(customer);
        return customer;
    }



}
