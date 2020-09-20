package com.company.service;

import com.company.entity.Address;
import com.company.entity.Store;

public interface StoreService {
    boolean addStore(Store store);

    String updateStoreNameById(String name, int id);
    Address updateStoreAddressById(Address address, int id);

    void removeStoreById(int id);
    void removeStoreByName(String name);
    void removeByStore(Store store);
    void removeStoreByAddress(Address address);

    Store[] findAllStores();
    Store findStoreById(int id);
    Store findStoreByName(String name);
    Store findStoreByAddress(Address address);

    boolean existStoreById(int id);
    boolean existStoreByName(String name);
    boolean existStoreByAddress(Address address);
    boolean existStore(Store store);
}
