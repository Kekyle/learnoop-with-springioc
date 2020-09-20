package com.company.repository;

import com.company.entity.Address;

import java.sql.SQLException;

public interface AddressRepository {

    boolean saveAddress(Address address);

    String updateStreetNameById(String street, int id);

    Address[] getAllAddresses();

    Address getAddressById(int id);

    Address getAddressByStreet(String street);

    void deleteAddressById(int id);

    void deleteAddressByStreetName(String street);

    void deleteAddress(Address address);

    boolean containsAddressById(int id);

    boolean containsAddressByStreetName(String street);

    boolean containsAddress(Address address);

}
