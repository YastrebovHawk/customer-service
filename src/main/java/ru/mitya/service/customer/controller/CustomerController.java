package ru.mitya.service.customer.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mitya.service.customer.jpa.entity.Customer;
import ru.mitya.service.customer.service.ICustomerService;
import ru.mitya.service.customer.service.dto.AddressDto;
import ru.mitya.service.customer.service.dto.CustomerDto;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("customers")
public class CustomerController {

    private final ICustomerService customerService;

    @GetMapping
    public List<Customer> getCustomers(){
        return customerService.getAllCustomers();
    }

    @PostMapping
    public Customer create(@RequestBody CustomerDto customerDto){
        return customerService.save(customerDto);
    }

    @GetMapping("/search")
    public List<Customer> searchByFirstNameAndLastName(
            @RequestParam(value = "firstname", required = false) String firstName,
            @RequestParam(value = "lastname", required = false) String lastName){
        return customerService.searchByFirstNameAndLastName(firstName, lastName);
    }

    @PutMapping("{id}")
    public Customer edit(@PathVariable Long id, @RequestBody AddressDto actualAddressDto){
        return customerService.editAddress(id, actualAddressDto);
    }
}
