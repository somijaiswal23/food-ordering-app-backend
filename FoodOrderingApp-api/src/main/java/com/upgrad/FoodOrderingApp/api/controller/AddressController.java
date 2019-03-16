package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.businness.AddressService;
import com.upgrad.FoodOrderingApp.service.businness.CustomerService;
import com.upgrad.FoodOrderingApp.service.dao.AddressDao;
import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.StateEntity;
import com.upgrad.FoodOrderingApp.service.exception.AddressNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.SaveAddressException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
//import com.upgrad.FoodOrderingApp.service.exception.UpdateAddressException;
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
    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST, path = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SaveAddressResponse> saveAddress(@RequestBody(required = false) final SaveAddressRequest saveAddressRequest, @RequestHeader("authorization") final String authorization) throws AuthorizationFailedException, SaveAddressException, AddressNotFoundException
    {
        String accessToken = authorization.split("Bearer ")[1];
        CustomerEntity customerEntity = customerService.getCustomer(accessToken);

        final AddressEntity address = new AddressEntity();
        address.setUuid(UUID.randomUUID().toString());
        address.setFlatBuilNo(saveAddressRequest.getFlatBuildingName());
        address.setLocality(saveAddressRequest.getLocality());
        address.setCity(saveAddressRequest.getCity());
        address.setPincode(saveAddressRequest.getPincode());
        address.setState(addressService.getStateByUUID(saveAddressRequest.getStateUuid()));
        address.setActive(1);

        final AddressEntity savedAddress = addressService.saveAddress(address, customerEntity);

        SaveAddressResponse addressResponse = new SaveAddressResponse().id(savedAddress.getUuid()).status("ADDRESS SUCCESSFULLY REGISTERED");
        return new ResponseEntity<SaveAddressResponse>(addressResponse, HttpStatus.CREATED);
    }

    /**
     * This api endpoint is used retrieve all the saved addresses in the database, for a customer
     *
     *@param authorization customer login access token in 'Bearer <access-token>' format
     *
     * @return ResponseEntity<AllAddressesResponse> type object along with HttpStatus OK
     */
    @RequestMapping(method = RequestMethod.GET, path = "/customer", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AddressListResponse> getAllSavedAddress(@RequestHeader("authorization") final String authorization) throws AuthorizationFailedException{

        String accessToken = authorization.split("Bearer ")[1];
        CustomerEntity customerEntity = customerService.getCustomer(accessToken);

        //Get all saved addresses
        List<AddressEntity> addressesList = addressService.getAllAddress(customerEntity);

        //Create Response for saved addresses
        AddressListResponse addressListResponse = new AddressListResponse();

        for (AddressEntity addressEntity : addressesList) {
            AddressList addressesResponse = new AddressList()
                    .id(UUID.fromString(addressEntity.getUuid()))
                    .flatBuildingName(addressEntity.getFlatBuilNo())
                    .locality(addressEntity.getLocality())
                    .city(addressEntity.getCity())
                    .pincode(addressEntity.getPincode())
                    .state(new AddressListState().id(UUID.fromString(addressEntity.getState().getUuid()))
                           .stateName(addressEntity.getState().getStatename()));
            addressListResponse.addAddressesItem(addressesResponse);
        }

        return new ResponseEntity<AddressListResponse>(addressListResponse, HttpStatus.OK);
    }
//
//    /**
//     * This api endpoint is used retrieve all the states in the database, for a customer
//     *
//     * @return ResponseEntity<AllStatesResponse> type object along with HttpStatus OK
//     */
//    @RequestMapping(method = RequestMethod.GET, path = "/states", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public ResponseEntity<AllStatesResponse> getAllStates()
//    {
//
//        /** Get all states */
//
//        List<StateEntity> statesList = addressService.getAllStates();
//
//        /** Response for Get All States */
//        AllStatesResponse allStatesResponse = new AllStatesResponse();
//
//        for (StateEntity stateEntity : statesList) {
//            AllStatesResponse listState = new AllStatesResponse()
//                    .id(UUID.fromString(stateEntity.getUuid()))
//                    .stateName(stateEntity.getStatename());
//            allStatesResponse.addStatesMethod(listState);
//        }
//
//        return new ResponseEntity<AllStatesResponse>(allStatesResponse, HttpStatus.OK);
//
//    }
//
//    /**
//     * This api endpoint is used to delete an address
//     * @param uuid Address uuid is used to fetch the correct address
//     *        authorization customer login access token in 'Bearer <access-token>' format
//     * @return ResponseEntity<DeleteAddResponse> with HTTP status ok
//     */
//    @RequestMapping(method = RequestMethod.DELETE, path = "/{address_UUID}")
//    public ResponseEntity<DeleteAddResponse> deleteAddResponse (@PathVariable("address_UUID") final String uuid, @RequestHeader("authorization") final String authorization) throws AuthorizationFailedException, UpdateAddressException, AddressNotFoundException {
//
//        String accessToken = authorization.split("Bearer ")[1];
//        CustomerEntity customerEntity = customerService.getCustomer(accessToken);
//
//        if(uuid == null){
//            throw new UpdateAddressException("UAR-005", "Address id can not be empty");
//        }
//
//       AddressEntity addressEntity = addressService.getAddressById(uuid);
//
//       if(addressEntity.getId() == null){
//        throw new AddressNotFoundException("ANF-003","No Address by this id");
//       }
//
//        DeleteAddResponse addDeleteResponse = new DeleteAddResponse().id(uuid).status("ADDRESS DELETED SUCCESSFULLY");
//        return new ResponseEntity<DeleteAddResponse>(addDeleteResponse, HttpStatus.OK);
//    }
}
