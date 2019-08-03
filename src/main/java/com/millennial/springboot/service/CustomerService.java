package com.millennial.springboot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.millennial.springboot.model.Customer;

@Service
public class CustomerService {

    private static ConcurrentHashMap<String, Customer> customerData = new ConcurrentHashMap<>();
    private final AtomicLong counter = new AtomicLong();

    public List<Customer> getAllCustomers() {
        return new ArrayList<Customer>(customerData.values());
    }
    
    public Customer find(String id) {
        return customerData.get(id);
    }

    public void deleteCustomer(String id) {
        customerData.remove(id);
    }

    public Customer updateCustomer(Customer customerToUpdate, Long id) {        
        Customer internalCustomer = customerData.get(customerToUpdate.getId());
        if (internalCustomer != null) {
            customerData.put(customerToUpdate.getId(), customerToUpdate);
            return customerData.get(customerToUpdate.getId());
        }
        return null;
    }

    public Customer createCustomer(Customer newCustomer) {
        String id = String.format("%s%s", counter.incrementAndGet(), "");
        newCustomer.setId(id);
        customerData.put(id, newCustomer);
        return customerData.get(id);
    }

}
