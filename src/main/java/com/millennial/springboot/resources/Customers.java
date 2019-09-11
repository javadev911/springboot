package com.millennial.springboot.resources;

import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.millennial.springboot.model.Customer;
import com.millennial.springboot.service.CustomerService;

@RestController
@RequestMapping("/customers")
public class Customers {

    @Autowired
    private CustomerService customerService;
    
    @GetMapping("/who-am-i")
    public ResponseEntity<String> getPing() {
        return ResponseEntity.ok(String.format("I am Customers Microservie, current time is: %s", (new Date()).toGMTString()));
    }

    @GetMapping("")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> find(@PathVariable String id) {
        Customer customer = customerService.find(id);

        if (customer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customer);
    }

    @PostMapping("/")
    public ResponseEntity<Customer> create(final @RequestBody Customer customer) throws URISyntaxException {
        customer.setFirstName("");
        Customer newCustomer = customerService.createCustomer(customer);

        if (newCustomer == null) {
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.ok(newCustomer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> update(@RequestBody Customer customerToUpdate, @PathVariable Long id) {

        if(!customerToUpdate.getId().equalsIgnoreCase(String.valueOf(id))) {
            return ResponseEntity.badRequest().build();
        }

        Customer updatedCustomer = customerService.updateCustomer(customerToUpdate, id);

        if (updatedCustomer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteStudent(@PathVariable Long id) {
        customerService.deleteCustomer(String.valueOf(id));
        return ResponseEntity.noContent().build();
    }
}