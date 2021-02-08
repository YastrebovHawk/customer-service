package ru.mitya.service.customer.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mitya.service.customer.exceptions.NotFoundExceptions;
import ru.mitya.service.customer.jpa.entity.Address;
import ru.mitya.service.customer.jpa.entity.Customer;
import ru.mitya.service.customer.jpa.repo.AddressRepo;
import ru.mitya.service.customer.jpa.repo.CustomerRepo;
import ru.mitya.service.customer.service.dto.AddressDto;
import ru.mitya.service.customer.service.dto.CustomerDto;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class CustomerService implements ICustomerService {

    private final AddressRepo addressRepo;
    private final CustomerRepo customerRepo;

    /*
    Метод сохраняет сущность Сustomer в бд. Если адрес регистрации и адрес проживания равны,
    то сохраняется только один адрес в бд. Адреса не сохраняются, если они существуют в бд.
     */
    @Transactional
    @Override
    public Customer save(CustomerDto customerDto) {
        Address actualAddress = customerDto.getActualAddress();
        Address registeredAddress = customerDto.getRegisteredAddress();
        LocalDateTime newTime = LocalDateTime.now();
        /*
        Создаю сущность из dto.
         */
        Customer customer = new Customer.Builder()
                .withFirstName(customerDto.getFirstName())
                .withLastName(customerDto.getLastName())
                .withMiddleName(customerDto.getMiddleName())
                .withSex(customerDto.getSex())
                .build();
        /*
        Проверяю равенство регистрации и актуального адреса.
         */
        if (actualAddress.equals(registeredAddress)) {
            Address addressBD = addressRepo.findByCountryAndRegionAndCityAndStreetAndHouseAndFlat(
                    actualAddress.getCountry(), actualAddress.getRegion(), actualAddress.getCity(),
                    actualAddress.getStreet(), actualAddress.getHouse(), actualAddress.getFlat());
            /*
            Поиск адреса в бд, если существует, то записывает в сущность, иначе записывает dto в сущность.
             */
            if (addressBD != null) {
                addressBD.setCreated(newTime);
                customer.setActualAddress(addressBD);
                customer.setRegisteredAddress(addressBD);
            } else {
                actualAddress.setCreated(newTime);
                customer.setRegisteredAddress(actualAddress);
                customer.setActualAddress(actualAddress);
            }
        } else {
            /*
            Поиск адреса в бд, если существует, то записываю в сущность, иначе записываю dto в сущность.
            Реализация для двух разных адресов.
             */
            Address actualAddressBD = addressRepo.findByCountryAndRegionAndCityAndStreetAndHouseAndFlat(
                    actualAddress.getCountry(), actualAddress.getRegion(), actualAddress.getCity(),
                    actualAddress.getStreet(), actualAddress.getHouse(), actualAddress.getFlat());
            Address registeredAddressBD = addressRepo.findByCountryAndRegionAndCityAndStreetAndHouseAndFlat(
                    registeredAddress.getCountry(), registeredAddress.getRegion(), registeredAddress.getCity(),
                    registeredAddress.getStreet(), registeredAddress.getHouse(), registeredAddress.getFlat());
            if (actualAddressBD != null) {
                actualAddressBD.setCreated(newTime);
                customer.setActualAddress(actualAddressBD);
            } else {
                actualAddress.setCreated(newTime);
                customer.setActualAddress(actualAddress);
            }
            if (registeredAddressBD != null) {
                registeredAddressBD.setCreated(newTime);
                customer.setRegisteredAddress(registeredAddressBD);
            } else {
                registeredAddress.setCreated(newTime);
                customer.setRegisteredAddress(registeredAddress);
            }
        }
        return customerRepo.save(customer);
    }

    /*
    Метод изменяет адрес проживания Customer, выбранного по id. Если id не существует, то выбрасывается исключение
    NotFoundExceptions со статусом 404.Если dto адреса существует в бд, то в сущность записывается адрес и бд.
     */
    @Transactional
    @Override
    public Customer editAddress(Long customerId, AddressDto addressDto) {
        /*
        Поиск Customer
         */
        Customer customer = customerRepo.findById(customerId).orElseThrow(NotFoundExceptions::new);
        Address addressBD = addressRepo.findByCountryAndRegionAndCityAndStreetAndHouseAndFlat(
                addressDto.getCountry(), addressDto.getRegion(), addressDto.getCity(),
                addressDto.getStreet(), addressDto.getHouse(), addressDto.getFlat());
        /*
        Поиск адреса в бд, если существует, то записывает в сущность, иначе записывает dto в сущность.
         */
        if (addressBD != null) {
            customer.setActualAddress(addressBD);
        } else {
            Address actualAddress = new Address.Builder()
                    .withCountry(addressDto.getCountry())
                    .withRegion(addressDto.getRegion())
                    .withCity(addressDto.getCity())
                    .withStreet(addressDto.getStreet())
                    .withHouse(addressDto.getHouse())
                    .withFlat(addressDto.getFlat())
                    .withCreated(customer.getActualAddress().getCreated())
                    .withModifier(LocalDateTime.now())
                    .build();
             customer.setActualAddress(actualAddress);
        }
        return customerRepo.save(customer);
    }

    /*
    Поиск по параметрам: firstName, lastName.
     */
    public List<Customer> searchByFirstNameAndLastName(String firstName, String lastName) {
        return customerRepo.findByFirstNameAndLastName(firstName, lastName);
    }

    /*
    Поиск всех Customer.
     */
    public List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }
}
