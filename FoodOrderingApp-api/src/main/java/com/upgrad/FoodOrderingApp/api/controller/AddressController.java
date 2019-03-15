package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.service.businness.AddressService;
import com.upgrad.FoodOrderingApp.service.businness.CustomerService;
import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.exception.AddressNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.SaveAddressException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/address")

public class AddressController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private AddressService addressService;

    /**
     * This api endpoint is used to save address for a customer
     *
     * @param authorization customer login access token in 'Bearer <access-token>' format
     *
     * @return ResponseEntity<SaveAddressResponse> type object along with HttpStatus Ok
     *
     * @throws AuthorizationFailedException if validation on customer access token fails
     */

    @RequestMapping(method = RequestMethod.POST, path = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SaveAddressResponse> saveAddress(@RequestBody(required = false) final SaveAddressRequest saveAddressRequest, @RequestHeader("authorization") final String authorization) throws AuthorizationFailedException, SaveAddressException, AddressNotFoundException {

        String accessToken = authorization.split("Bearer ")[1];
        CustomerEntity customerEntity = customerService.getCustomer(accessToken);

        /** Validation for required fields */

        if (
                saveAddressRequest.getFlatNumber().equals("") ||
                saveAddressRequest.getLocality().equals("") ||
                saveAddressRequest.getCity().equals("") ||
                saveAddressRequest.getPincode().equals("") ||
                saveAddressRequest.getStateid().equals("") ||
                saveAddressRequest.getActive().equals("")
                ) {
            throw new SaveAddressException("SGR-001", "No field can be empty");
        }

        final AddressEntity address = new AddressEntity();
        address.setUuid(UUID.randomUUID().toString());
        address.setFlatNumber(saveAddressRequest.getFlatNumber());
        address.setLocality(saveAddressRequest.getLocality());
        address.setCity(saveAddressRequest.getCity());
        address.setPincode(saveAddressRequest.getPincode());
        address.setStateid(saveAddressRequest.getStateid());
        address.setActive(saveAddressRequest.getActive());

        final AddressEntity saveaddress = addressService.saveAddress(address);
        SaveAddressResponse addressResponse = new SaveAddressResponse().id(saveaddress.getUuid()).status("ADDRESS SUCCESSFULLY REGISTERED");
        return new ResponseEntity<SaveAddressResponse>(addressResponse, HttpStatus.CREATED);
    }

    /**
     * This api endpoint is used retrieve all the saved addresses in the database, for a customer
     *
     * @return ResponseEntity<AllAddressesResponse> type object along with HttpStatus OK
     */
    @RequestMapping(method = RequestMethod.GET, path = "/customer", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AllAddressesResponse> getAllSavedAddress(@RequestBody(required = false) final AllAddressesRequest allAddressesRequest, @RequestHeader("authorization") final String authorization) throws AuthorizationFailedException{
        String accessToken = authorization.split("Bearer ")[1];
        CustomerEntity customerEntity = customerService.getCustomer(accessToken);

        /** Get all saved Addresses */

        List<AddressEntity> addressesList = addressService.getAllAddresses();
    }

    /**
     * This api endpoint is used retrieve all the states in the database, for a customer
     *
     * @return ResponseEntity<AllStatesResponse> type object along with HttpStatus OK
     */
    @RequestMapping(method = RequestMethod.GET, path = "/states", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AllStatesResponse> getAllStates() {

        /** Get all states */

        List<StateEntity> statesList = addressService.getAllStates();
    }
}