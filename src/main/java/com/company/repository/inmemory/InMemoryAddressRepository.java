package com.company.repository.inmemory;

import com.company.entity.Address;
import com.company.repository.AddressRepository;

import java.util.Arrays;

public class InMemoryAddressRepository implements AddressRepository {
    private static Address[] addresses = new Address[10];

    public static void main(String[] args) {
        AddressRepository addressRepository = new InMemoryAddressRepository();
        addressRepository.saveAddress(new Address("qwe"));
        addressRepository.saveAddress(new Address("asd"));
        Address zxc = new Address("zxc");
        addressRepository.saveAddress(zxc);

        System.out.println(Arrays.toString(addressRepository.getAllAddresses()));
        System.out.println(addressRepository.getAddressById(1));
        System.out.println(addressRepository.getAddressByStreet("zxc"));

       addressRepository.updateStreetNameById("new", 1);
        System.out.println(Arrays.toString(addressRepository.getAllAddresses()));

//        addressRepository.delete(2);
//        addressRepository.delete("new");
        addressRepository.deleteAddress(zxc);
        System.out.println(Arrays.toString(addressRepository.getAllAddresses()));
    }

    @Override
    public boolean saveAddress(Address address) {
        for (int i = 0; i < addresses.length; i++) {
            if (addresses[i] == null) {
                addresses[i] = address;
                return true;
            }
        }
        return false;
    }

    @Override
    public String updateStreetNameById(String street, int id) {
        String oldAddress = null;
        for (int i = 0; i < addresses.length; i++) {
            if (addresses[i] == null) break;
            if (addresses[i].getId() == id){
                oldAddress = addresses[i].getStreet();
                addresses[i].setStreet(street);
                break;
            }
        }
        return oldAddress;
    }

    @Override
    public Address[] getAllAddresses() {
        int count = 0;
        for (int i = 0; i < addresses.length; i++) {
            if (addresses[i]!=null){
                count++;
            }
        }
        Address[] newAddresses = new Address[count];
        for (int i = 0; i < newAddresses.length; i++) {
            newAddresses[i]= addresses[i];
        }
        return newAddresses;
    }

    @Override
    public Address getAddressById(int id) {
        for (int i = 0; i < addresses.length; i++) {
            if (addresses[i]==null) break;
            if (addresses[i].getId()== id){
                return addresses[i];
            }
        }
        return null;
    }

    @Override
    public Address getAddressByStreet(String street) {
        for (int i = 0; i < addresses.length; i++) {
            if (addresses[i] == null) break;
            if (addresses[i].getStreet().equals(street)){
                return  addresses[i];
            }
        }
        return null;
    }

    @Override
    public void deleteAddressById(int id) {
        for (int i = 0; i < addresses.length; i++) {
            if (addresses[i] == null) break;
            if (addresses[i].getId() == id){
                for (int j = i; j < addresses.length - 1; j++) {
                    addresses[j] = addresses[j+1];
                }
                break;
            }
        }
    }

    @Override
    public void deleteAddressByStreetName(String street) {
        for (int i = 0; i < addresses.length; i++) {
            if (addresses[i] == null) break;
            if (addresses[i].getStreet().equals(street)){
                for (int j = i; j < addresses.length - 1; j++) {
                    addresses[j] = addresses[j+1];
                }
                break;
            }
        }
    }

    @Override
    public void deleteAddress(Address address) {
        for (int i = 0; i < addresses.length; i++) {
            if (addresses[i] == null) break;
            if (addresses[i].equals(address)){
                for (int j = i; j < addresses.length - 1; j++) {
                    addresses[j] = addresses[j+1];
                }
                break;
            }
        }
    }

    @Override
    public boolean containsAddressById(int id) {
        for (int i = 0; i < addresses.length; i++) {
            if (addresses[i] == null) break;
            if (addresses[i].getId() == id){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAddressByStreetName(String street) {
        for (int i = 0; i < addresses.length; i++) {
            if (addresses[i] == null) break;
            if (addresses[i].getStreet().equals(street)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAddress(Address address) {
        for (int i = 0; i < addresses.length; i++) {
            if (addresses[i] == null) break;
            if (addresses[i].equals(address)){
                return true;
            }
        }
        return false;
    }
}
