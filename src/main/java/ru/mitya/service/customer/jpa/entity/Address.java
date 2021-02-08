package ru.mitya.service.customer.jpa.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;


@Entity
@EqualsAndHashCode
@Getter
@Setter
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "country")
    private String country;
    @Column(name = "region")
    private String region;
    @Column(name = "city")
    private String city;
    @Column(name = "street")
    private String street;
    @Column(name = "house")
    private String house;
    @Column(name = "flat")
    private String flat;
    @Column(name = "created")
    private LocalDateTime created;
    @Column(name = "modified")
    private LocalDateTime modified;

    public Address() {
    }


    public static class Builder {
        private Address address;
        public Builder(){
            address = new Address();
        }
        public Builder withId(Long id){
            address.setId(id);
            return this;
        }
        public Builder withCountry(String country){
            address.setCountry(country);
            return this;
        }
        public Builder withRegion(String region){
            address.setRegion(region);
            return this;
        }
        public Builder withCity(String city){
            address.setCity(city);
            return this;
        }
        public Builder withStreet(String street){
            address.setStreet(street);
            return this;
        }
        public Builder withHouse(String house){
            address.setHouse(house);
            return this;
        }
        public Builder withFlat(String flat){
            address.setFlat(flat);
            return this;
        }
        public Builder withCreated(LocalDateTime created){
            address.setCreated(created);
            return this;
        }
        public Builder withModifier(LocalDateTime modifier){
            address.setModified(modifier);
            return this;
        }
        public Address build(){
            return address;
        }
    }
}
