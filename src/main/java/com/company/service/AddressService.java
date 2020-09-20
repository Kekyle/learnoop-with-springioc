package com.company.service;

import com.company.entity.Address;

public interface AddressService {
    boolean addAddress(Address address);

    String updateStreetNameById(String street, int id);

    Address[] findAllAddresses();
    Address findAddressById(int id);
    Address findAddressByStreetName(String street);

    void removeAddressById(int id);
    void removeAddressByStreet(String street);
    void removeByAddress(Address address);

    boolean existAddressById(int id);
    boolean existAddressByStreetName(String street);
    boolean existAddress(Address address);
}
