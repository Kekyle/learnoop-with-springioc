package com.company.repository;

import com.company.entity.Address;
import com.company.entity.Store;

public interface StoreRepository {
    boolean saveStore(Store store);

    String updateStoreNameById(String name, int id);

    Address updateStoreAddressById(Address address, int id);

    void deleteStoreById(int id);

    void deleteStoreByName(String name);

    void deleteByStore(Store store);

    void deleteStoreByAddress(Address address);

    Store[] getAllStores();

    Store getStoreById(int id);

    Store getStoreByName(String name);

    Store getStoreByAddress(Address address);

    boolean containsStoreById(int id);

    boolean containsByStoreName(String name);

    boolean containsByStoreAddress(Address address);

    boolean containsByStore(Store store);
}
