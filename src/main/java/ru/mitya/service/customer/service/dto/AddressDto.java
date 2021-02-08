package ru.mitya.service.customer.service.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class AddressDto {

    private Long id;
    private String country;
    private String region;
    private String city;
    private String street;
    private String house;
    private String flat;
    private LocalDateTime created;
    private LocalDateTime modified;
}
