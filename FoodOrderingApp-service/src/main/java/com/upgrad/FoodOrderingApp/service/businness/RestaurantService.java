package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.RestaurantDao;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantCategory;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantDao restaurantDao;

    /**
     * This method implements the business logic for 'Get All Restaurant' endpoint
     *
     */

    public List<RestaurantEntity> getAllRestaurants() {

        return restaurantDao.getAllRestaurants();
    }

    public List<RestaurantCategory> getCategories(String id){
        return restaurantDao.getCategories(id);
    }

}
