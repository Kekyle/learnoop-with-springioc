package com.company.service;

import com.company.entity.Address;
import com.company.repository.AddressRepository;
import com.company.repository.file.FileAddressRepository;
import com.company.repository.inmemory.InMemoryAddressRepository;
import org.springframework.stereotype.Component;

import java.util.Arrays;
@Component
public class AddressServiceImpl implements AddressService {
    private AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public boolean addAddress(Address address) {
        if (addressRepository.containsAddress(address)){
            return false;
        }
        return addressRepository.saveAddress(address);
    }

    @Override
    public String updateStreetNameById(String street, int id) {
        if (addressRepository.containsAddressById(id)){
            return addressRepository.updateStreetNameById(street, id);
        }
        return null;
    }

    @Override
    public Address[] findAllAddresses() {
        return addressRepository.getAllAddresses();
    }

    @Override
    public Address findAddressById(int id) {
        if (addressRepository.containsAddressById(id)){
            return addressRepository.getAddressById(id);
        }
        return null;
    }

    @Override
    public Address findAddressByStreetName(String street) {
        if (addressRepository.containsAddressByStreetName(street)){
            return addressRepository.getAddressByStreet(street);
        }
        return null;
    }

    @Override
    public void removeAddressById(int id) {
        if (addressRepository.containsAddressById(id)){
            addressRepository.deleteAddressById(id);
        }
    }

    @Override
    public void removeAddressByStreet(String street) {
        if (addressRepository.containsAddressByStreetName(street)){
            addressRepository.deleteAddressByStreetName(street);
        }
    }

    @Override
    public void removeByAddress(Address address) {
        if (addressRepository.containsAddress(address)){
            addressRepository.deleteAddress(address);
        }
    }

    @Override
    public boolean existAddressById(int id) {
        return addressRepository.containsAddressById(id);
    }

    @Override
    public boolean existAddressByStreetName(String street) {
        return addressRepository.containsAddressByStreetName(street);
    }

    @Override
    public boolean existAddress(Address address) {
        return addressRepository.containsAddress(address);
    }
}
