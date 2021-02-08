package ru.mitya.service.customer.jpa.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mitya.service.customer.jpa.entity.Address;

public interface AddressRepo extends JpaRepository<Address, Long> {
    Address findByCountryAndRegionAndCityAndStreetAndHouseAndFlat(
            String country, String region, String city,
            String street, String house, String flat
    );
}
