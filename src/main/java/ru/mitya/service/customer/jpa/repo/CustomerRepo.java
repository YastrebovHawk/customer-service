package ru.mitya.service.customer.jpa.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mitya.service.customer.jpa.entity.Customer;

import java.util.List;

public interface CustomerRepo extends JpaRepository<Customer, Long> {
    List<Customer> findByFirstNameAndLastName(String firstName, String lastName);
}
