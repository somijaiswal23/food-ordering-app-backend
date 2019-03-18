package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CategoryDao;
import com.upgrad.FoodOrderingApp.service.dao.RestaurantDao;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantCategory;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantDao restaurantDao;

    @Autowired
    private CategoryDao categoryDao;

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
        RestaurantEntity restaurantEntityById = restaurantDao.getRestaurantByUUID(restaurantId);

        if(restaurantEntityById==null){
            throw new RestaurantNotFoundException("RNF-001","No restaurant by this id");
        }
        return restaurantDao.getRestaurantByUUID(restaurantId);
    }

    public List<RestaurantEntity> restaurantsByName(final String restaurantName) throws RestaurantNotFoundException {
        if(restaurantName.isEmpty()){
            throw new RestaurantNotFoundException("RNF-003", "Restaurant name field should not be empty");
        }

        List<RestaurantEntity> restaurantEntityList = restaurantDao.restaurantsByRating();
        List<RestaurantEntity> matchingRestaurantEntityList = new ArrayList<RestaurantEntity>();
        for (RestaurantEntity restaurantEntity : restaurantEntityList) {
            if (restaurantEntity.getRestaurantName().toLowerCase().contains(restaurantName.toLowerCase())) {
                matchingRestaurantEntityList.add(restaurantEntity);
            }
        }

        return matchingRestaurantEntityList;
    }

    public List<RestaurantEntity> restaurantByCategory(final String categoryId) throws CategoryNotFoundException {

        if (categoryId.equals("")) {
            throw new CategoryNotFoundException("CNF-001", "Category id field should not be empty");
        }

        CategoryEntity categoryEntity = categoryDao.getCategoryByUuid(categoryId);

        if(categoryEntity == null) {
            throw new CategoryNotFoundException("CNF-002", "No category by this id");
        }

        List<RestaurantEntity> restaurantEntityList = categoryEntity.getRestaurants();
        restaurantEntityList.sort(Comparator.comparing(RestaurantEntity::getRestaurantName));
        return restaurantEntityList;
    }

    public RestaurantEntity restaurantByUUID(String uuid) throws RestaurantNotFoundException {
        RestaurantEntity restaurantEntity = restaurantDao.getRestaurantByUUID(uuid);
        if (restaurantEntity == null) {
            throw new RestaurantNotFoundException("RNF-001", "No restaurant by this id");
        }
        return restaurantEntity;

    }
}
