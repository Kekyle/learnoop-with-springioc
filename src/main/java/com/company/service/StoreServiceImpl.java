package com.company.service;

import com.company.entity.Address;
import com.company.entity.Store;
import com.company.repository.StoreRepository;
import com.company.repository.inmemory.InMemoryStoreRepository;
import org.springframework.stereotype.Component;

import java.util.Arrays;
@Component
public class StoreServiceImpl implements StoreService {
    private StoreRepository storeRepository;

    public StoreServiceImpl(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Override
    public boolean addStore(Store store) {
        if (storeRepository.containsByStore(store)){
            return false;
        }
        return storeRepository.saveStore(store);
    }

    @Override
    public String updateStoreNameById(String name, int id) {
        if (storeRepository.containsStoreById(id)){
            return storeRepository.updateStoreNameById(name, id);
        }
        return null;
    }

    @Override
    public Address updateStoreAddressById(Address address, int id) {
        if (storeRepository.containsStoreById(id)){
            return storeRepository.updateStoreAddressById(address, id);
        }
        return null;
    }

    @Override
    public void removeStoreById(int id) {
        if (storeRepository.containsStoreById(id)){
            storeRepository.deleteStoreById(id);
        }
    }

    @Override
    public void removeStoreByName(String name) {
        if (storeRepository.containsByStoreName(name)){
            storeRepository.deleteStoreByName(name);
        }
    }

    @Override
    public void removeByStore(Store store) {
        if (storeRepository.containsByStore(store)){
            storeRepository.deleteByStore(store);
        }
    }

    @Override
    public void removeStoreByAddress(Address address) {
        if (storeRepository.containsByStoreAddress(address)){
            storeRepository.deleteStoreByAddress(address);
        }
    }

    @Override
    public Store[] findAllStores() {
        return storeRepository.getAllStores();
    }

    @Override
    public Store findStoreById(int id) {
        if (storeRepository.containsStoreById(id)){
            return storeRepository.getStoreById(id);
        }
        return null;
    }

    @Override
    public Store findStoreByName(String name) {
        if (storeRepository.containsByStoreName(name)){
            return storeRepository.getStoreByName(name);
        }
        return null;
    }

    @Override
    public Store findStoreByAddress(Address address) {
        if (storeRepository.containsByStoreAddress(address)){
            return storeRepository.getStoreByAddress(address);
        }
        return null;
    }

    @Override
    public boolean existStoreById(int id) {
        return storeRepository.containsStoreById(id);
    }

    @Override
    public boolean existStoreByName(String name) {
        return storeRepository.containsByStoreName(name);
    }

    @Override
    public boolean existStoreByAddress(Address address) {
        return storeRepository.containsByStoreAddress(address);
    }

    @Override
    public boolean existStore(Store store) {
        return storeRepository.containsByStore(store);
    }
}
