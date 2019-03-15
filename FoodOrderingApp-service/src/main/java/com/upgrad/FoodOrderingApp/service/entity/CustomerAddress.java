package com.upgrad.FoodOrderingApp.service.entity;

//import org.apache.commons.lang3.builder.ToStringExclude;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * CustomerAddress class contains all the attributes to be mapped to all the fields in 'customer_address' table in the database
 */


public class CustomerAddress implements  Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    //@OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "customer_id")
    @NotNull
    private CustomerEntity customer;

    @ManyToOne
    //@OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "address_id")
    @NotNull
    private AddressEntity address;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }
}
