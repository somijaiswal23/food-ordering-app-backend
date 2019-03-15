package com.upgrad.FoodOrderingApp.service.entity;

//import org.apache.commons.lang3.builder.ToStringExclude;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * AddressEntity class contains all the attributes to be mapped to all the fields in 'address' table in the database
 */
@Entity
@Table(name = "address")
public class AddressEntity implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "uuid")
    @NotNull
    @Size(max = 200)
    private String uuid;

    @Column(name = "flat_buil_number")
    @NotNull
    @Size(max = 255)
    private String flatNumber;

    @Column(name = "locality")
    @NotNull
    @Size(max = 255)
    private String locality;

    @Column(name = "city")
    @NotNull
    @Size(max = 30)
    private String city;

    @Column(name = "pincode")
    @NotNull
    @Size(max = 30)
    private String pincode;

    @ManyToOne
    //@OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "state_id")
    @NotNull
    private StateEntity stateid;

    @Column(name = "active")
    @NotNull
    @Size(max = 1)
    private int active;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFlatNumber() {
        return flatNumber;
    }

    public void setFlatNumber(String flatNumber) {
        this.flatNumber = flatNumber;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public StateEntity getStateid() {
        return stateid;
    }

    public void setStateid(StateEntity stateid) {
        this.stateid = stateid;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
}
