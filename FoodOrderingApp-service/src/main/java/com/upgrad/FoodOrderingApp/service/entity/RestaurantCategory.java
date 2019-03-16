package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * RestaurantCategoryEntity class contains all the attributes to be mapped to all the fields in 'restaurant_category' table in the database
 */

@Entity
@Table(name = "restaurant_category")
@NamedQueries(
        {
            @NamedQuery(name = "categoriesMethod", query = "select q from RestaurantCategory q"),
        }
)

public class RestaurantCategory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    @NotNull
    private RestaurantEntity restaurantid;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @NotNull
    private CategoryEntity categoryid;

}
