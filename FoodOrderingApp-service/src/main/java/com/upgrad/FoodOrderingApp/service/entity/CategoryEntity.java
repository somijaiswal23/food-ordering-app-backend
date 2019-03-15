package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * CategoryEntity class contains all the attributes to be mapped to all the fields in 'category' table in the database
 */
@Entity
@Table(name = "category")
@NamedQueries({
        @NamedQuery(name = "allCategories", query = "select q from CategoryEntity q"),
})
public class CategoryEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "uuid")
    @NotNull
    @Size(max = 200)
    private String uuid;

    @Column(name = "category_name")
    @NotNull
    @Size(max = 255)
    private String categoryName;

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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
