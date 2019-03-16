package com.upgrad.FoodOrderingApp.api.controller;


import com.upgrad.FoodOrderingApp.service.businness.RestaurantService;
import com.upgrad.FoodOrderingApp.service.entity.*;
import com.upgrad.FoodOrderingApp.service.businness.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    /**
     * This api endpoint is used to retrieve list of all restaurants
     *
     * @return ResponseEntity<AllRestaurantResponse> type object along with HttpStatus Ok
     */

    @RequestMapping(method = RequestMethod.GET, path = " ", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AllRestaurantResponse> getAllRestaurant() {


        /** Get all Restaurants */

        List<RestaurantEntity> restaurantList = restaurantService.getAllRestaurants();


        /** Create Response for List of Restaurants*/

        AllRestaurantResponse restaurantListResponse = new AllRestaurantResponse();

        for (RestaurantEntity restaurantEntity : restaurantList) {

            RestaurantCategory restaurantCategory = new RestaurantCategory();
            AddressService addressService = new AddressService();
            AddressEntity address = addressService.getAddressById(restaurantEntity.getAddressid().toString());
            StateEntity stateEntity = new StateEntity();
            StateEntity stateData = addressService.getstateEntity(address.getStateid());
            AllAddressesResponse addressesResponse = new AllAddressesResponse()
                    .id(UUID.fromString(addressService.getUuid()))
                    .flat_building_name(addressService.getFlatNumber())
                    .locality(addressService.getLocality())
                    .city(addressService.getCity())
                    .pincode(addressService.getPincode())
                    .state.id(stateData.getId())
                    .state_name(stateData.getStatename());
            addressListResponse.addAddressesList(addressesResponse);


            AllRestaurantResponse restaurantResponse = new AllRestaurantResponse()
                    .id(UUID.fromString(restaurantEntity.getUuid()))
                    .restaurant_name(restaurantEntity.getRestaurantname())
                    .photo_url(restaurantEntity.getPhotourl())
                    .customer_rating(restaurantEntity.getCustomerrating())
                    .average_price(restaurantEntity.getAvgpricefortwo())
                    .number_customer_rated(restaurantEntity.getCustomerrating());

            // get all items for category
            CategoryEntity categoryEntity = categoryService.getCategoryById(categoryId);

            // create response
            CategoryDetailsResponse categoryDetailsResponse = new CategoryDetailsResponse().id(UUID.fromString(categoryEntity.getUuid())).categoryName(categoryEntity.getCategoryName());

            for (ItemEntity itemEntity : categoryEntity.getItems()) {
                ItemList itemList = new ItemList()
                        .id(UUID.fromString(itemEntity.getUuid()))
                        .itemName(itemEntity.getItemName())
                        .price(itemEntity.getPrice())
                        .itemType(ItemList.ItemTypeEnum.fromValue(itemEntity.getType().getValue()));
                categoryDetailsResponse.addItemListItem(itemList);
            }
            restaurantListResponse.addRestaurantResponse(restaurantResponse)
            restaurantListResponse.addAddress(addressesResponse);
            restaurantListResponse.addCategoryDetails(categoryDetailsResponse);
        }

        return new ResponseEntity<AllRestaurantResponse>(restaurantListResponse, HttpStatus.OK);
    }
}