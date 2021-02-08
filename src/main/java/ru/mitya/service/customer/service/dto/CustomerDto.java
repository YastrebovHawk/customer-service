package ru.mitya.service.customer.service.dto;

import lombok.Getter;
import lombok.Setter;
import ru.mitya.service.customer.jpa.entity.Address;

@Getter
@Setter
public class CustomerDto {

    private Long id;
    private Address registeredAddress;
    private Address actualAddress;
    private String firstName;
    private String lastName;
    private String middleName;
    private String sex;
}
