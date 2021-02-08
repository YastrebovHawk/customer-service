package ru.mitya.service.customer.jpa.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ru.mitya.service.customer.exceptions.BadRequestException;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne(targetEntity = Address.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "registred_address_id", nullable = false)
    private Address registeredAddress;
    @ManyToOne(targetEntity = Address.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "actual_address_id", nullable = false)
    private Address actualAddress;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "middle_name", nullable = false)
    private String middleName;
    @Column(name = "sex", nullable = false)
    private String sex;

    public Customer() {
    }

    public void setSex(String sex){
        if (sex == null){
            throw new BadRequestException();
        }
        if (sex.equals("male")){
            this.sex = sex;
        }
        else if (sex.equals("female")){
            this.sex = sex;
        }
        else {
            throw new BadRequestException();
        }
    }

    public static class Builder {
        private Customer customer;
        public Builder(){
            customer = new Customer();
        }
        public Builder withId(Long id){
            customer.setId(id);
            return this;
        }
        public Builder withRegisteredAddress(Address registeredAddress){
            customer.setRegisteredAddress(registeredAddress);
            return this;
        }
        public Builder withActualAddress(Address actualAddress){
            customer.setActualAddress(actualAddress);
            return this;
        }
        public Builder withFirstName(String firstName){
            customer.setFirstName(firstName);
            return this;
        }
        public Builder withLastName(String lastName){
            customer.setLastName(lastName);
            return this;
        }
        public Builder withMiddleName(String middleName){
            customer.setMiddleName(middleName);
            return this;
        }
        public Builder withSex(String sex){
            customer.setSex(sex);
            return this;
        }
        public Customer build(){
            return customer;
        }
    }
}
