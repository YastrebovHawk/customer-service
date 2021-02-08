package ru.mitya.service.customer.service;

import ru.mitya.service.customer.jpa.entity.Customer;
import ru.mitya.service.customer.service.dto.AddressDto;
import ru.mitya.service.customer.service.dto.CustomerDto;

import java.util.List;

public interface ICustomerService {
    Customer save(CustomerDto customerDto);
    Customer editAddress(Long customerId, AddressDto param);
    List<Customer> getAllCustomers();
    List<Customer> searchByFirstNameAndLastName(String firstName, String lastName);
}
