package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.RestaurantDao;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantCategory;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    public List<RestaurantEntity> restaurantsByRating() {
        return restaurantDao.restaurantsByRating();
    }

    public List<RestaurantCategory> getCategories(String id){
        return restaurantDao.getCategories(id);
    }


    public RestaurantEntity getRestaurantById(final String restaurantId) throws RestaurantNotFoundException {
        RestaurantEntity restaurantEntityById = restaurantDao.getRestaurantByUuid(restaurantId);

        if(restaurantEntityById==null){
            throw new RestaurantNotFoundException("RNF-001","No restaurant by this id");
        }
        return restaurantDao.getRestaurantByUuid(restaurantId);
    }

    public List<RestaurantEntity> getRestaurantsByName(final String restaurantName){
        return restaurantDao.getRestaurantByName(restaurantName);
    }

    public List<RestaurantEntity> getRestaurantsByCategoryId(final String categoryId){
        return restaurantDao.getRestaurantsByCategoryId(categoryId);

    public RestaurantEntity restaurantByUUID(String uuid) throws RestaurantNotFoundException {
        RestaurantEntity restaurantEntity = restaurantDao.getRestaurantByUUID(uuid);
        if (restaurantEntity == null) {
            throw new RestaurantNotFoundException("RNF-001", "No restaurant by this id");
        }
        return restaurantEntity;

    }
}
