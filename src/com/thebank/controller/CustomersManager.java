package com.thebank.controller;

import com.thebank.util.NotAllowedAgeException;
import com.thebank.model.Customer;

import java.util.List;

public class CustomersManager {

    private final List<Customer> CUSTOMERS;

    public CustomersManager(List<Customer> customers) {
        this.CUSTOMERS = customers;
    }

    public int countCustomers() {
        return CUSTOMERS.size();
    }

    public Customer selectCustomerById(int id) {
        for (var customer : CUSTOMERS) {
            if (customer.getId() == id)
                return customer;
        }
        return null;
    }

    public void insertCustomer(Customer customer) {
        CUSTOMERS.add(customer);
    }

    public boolean deleteCustomerById(int id) {
        for (var customer : CUSTOMERS) {
            if (customer.getId() == id) {
                CUSTOMERS.remove(customer);
                return true;
            }
        }
        return false;
    }

    public boolean customerIsActive(int id) {
        for (var customer : CUSTOMERS) {
            if (customer.getId() == id)
                if (customer.isActive())
                    return true;
        }
        return false;
    }

    public void dump() {
        this.CUSTOMERS.clear();
    }

    public boolean validateAge(int age) throws NotAllowedAgeException {
        if (age < 18 || age > 65)
            throw new NotAllowedAgeException(NotAllowedAgeException.INVALID_AGE_MESSAGE);
        return true;
    }

}
