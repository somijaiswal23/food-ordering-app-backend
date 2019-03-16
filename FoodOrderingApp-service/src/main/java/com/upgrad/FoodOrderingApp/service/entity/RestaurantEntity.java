package com.upgrad.FoodOrderingApp.service.entity;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * RestaurantEntity class contains all the attributes to be mapped to all the fields in 'restaurant' table in the database
 */

@Entity
@Table(name = "restaurant")

@NamedQueries(
        {
                @NamedQuery(name = "allRestaurantsMethods", query = "select q from RestaurantEntity q order by q.cutomerrating"),

        }
)

public class RestaurantEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "uuid")
    @NotNull
    @Size(max = 200)
    private String uuid;

    @Column(name = "restaurant_name")
    @NotNull
    @Size(max = 50)
    private String restaurantname;

    @Column(name = "photo_url")
    @NotNull
    @Size(max = 255)
    private String photourl;

    @Column(name = "customer_rating")
    @NotNull
    @Size(max = 2)
    private int customerrating;

    @Column(name = "avg_price_two")
    @NotNull
    @Size(max = 5)
    private int avgpricefortwo;

    @Column(name = "no_customer_rated")
    @NotNull
    @Size(max = 10)
    private int noofcustomerrated;

    @ManyToOne
    @JoinColumn(name = "address_id")
    @NotNull
    private AddressEntity addressid;

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

    public String getRestaurantname() {
        return restaurantname;
    }

    public void setRestaurantname(String restaurantname) {
        this.restaurantname = restaurantname;
    }

    public String getPhotourl() {
        return photourl;
    }

    public void setPhotourl(String photourl) {
        this.photourl = photourl;
    }

    public int getCustomerrating() {
        return customerrating;
    }

    public void setCustomerrating(int customerrating) {
        this.customerrating = customerrating;
    }

    public int getAvgpricefortwo() {
        return avgpricefortwo;
    }

    public void setAvgpricefortwo(int avgpricefortwo) {
        this.avgpricefortwo = avgpricefortwo;
    }

    public int getNoofcustomerrated() {
        return noofcustomerrated;
    }

    public void setNoofcustomerrated(int noofcustomerrated) {
        this.noofcustomerrated = noofcustomerrated;
    }

    public AddressEntity getAddressid() {
        return addressid;
    }

    public void setAddressid(AddressEntity addressid) {
        this.addressid = addressid;
    }
}
