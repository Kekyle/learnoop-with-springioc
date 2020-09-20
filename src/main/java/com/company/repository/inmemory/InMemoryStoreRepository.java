package com.company.repository.inmemory;

import com.company.entity.Address;
import com.company.entity.Store;
import com.company.repository.AddressRepository;
import com.company.repository.StoreRepository;

import java.util.Arrays;

public class InMemoryStoreRepository implements StoreRepository {
    private static Store[] stores = new Store[10];

    @Override
    public boolean saveStore(Store store) {
        for (int i = 0; i < stores.length; i++) {
            if (stores[i] == null) {
                stores[i] = store;
                return true;
            }
        }
        return false;
    }

    @Override
    public String updateStoreNameById(String name, int id) {
        String oldStore;
        for (int i = 0; i < stores.length; i++) {
            if (stores[i] == null) break;
            if (stores[i].getId() == id) {
                oldStore = stores[i].getName();
                stores[i].setName(name);
                return oldStore;
            }
        }
        return null;
    }

    @Override
    public Address updateStoreAddressById(Address address, int id) {
        Address oldAddress;
        for (int i = 0; i < stores.length; i++) {
            if (stores[i] == null) break;
            if (stores[i].getId() == id) {
                oldAddress = stores[i].getAddress();
                stores[i].setAddress(address);
                return oldAddress;
            }
        }
        return null;
    }

    @Override
    public void deleteStoreById(int id) {
        deleteElementById(id);
    }

    private void deleteElementById(int id) {
        for (int i = 0; i < stores.length; i++) {
            if (stores[i] == null) break;
            if (stores[i].getId() == id) {
                arrayCopy(i);
                break;
            }
        }
    }

    private void arrayCopy(int i) {
        for (int j = i; j < stores.length - 1; j++) {
            stores[j] = stores[j + 1];
        }
    }

    @Override
    public void deleteStoreByName(String name) {
        deleteElementByName(name);
    }

    private void deleteElementByName(String name) {
        for (int i = 0; i < stores.length; i++) {
            if (stores[i] == null) break;
            if (stores[i].getName().equals(name)) {
                arrayCopy(i);
                break;
            }
        }
    }

    @Override
    public void deleteByStore(Store store) {
        deleteElementByStore(store);
    }

    private void deleteElementByStore(Store store) {
        for (int i = 0; i < stores.length; i++) {
            if (stores[i] == null) break;
            if (stores[i].equals(store)) {
                arrayCopy(i);
                break;
            }
        }
    }

    @Override
    public void deleteStoreByAddress(Address address) {
        deleteElementByAddress(address);
    }

    private void deleteElementByAddress(Address address) {
        for (int i = 0; i < stores.length; i++) {
            if (stores[i] == null) break;
            if (stores[i].getAddress().equals(address)) {
                arrayCopy(i);
                break;
            }
        }
    }

    @Override
    public Store[] getAllStores() {
        int count = 0;
        for (int i = 0; i < stores.length; i++) {
            if (stores[i] != null) {
                count++;
            }
        }
        Store[] newStores = new Store[count];
        for (int i = 0; i < newStores.length; i++) {
            newStores[i] = stores[i];
        }
        return newStores;
    }

    @Override
    public Store getStoreById(int id) {
        for (int i = 0; i < stores.length; i++) {
            if (stores[i] == null) break;
            if (stores[i].getId() == id) {
                return stores[i];
            }
        }
        return null;
    }

    @Override
    public Store getStoreByName(String name) {
        for (int i = 0; i < stores.length; i++) {
            if (stores[i] == null) break;
            if (stores[i].getName().equals(name)) {
                return stores[i];
            }
        }
        return null;
    }

    @Override
    public Store getStoreByAddress(Address address) {
        for (int i = 0; i < stores.length; i++) {
            if (stores[i] == null) break;
            if (stores[i].getAddress().equals(address)) {
                return stores[i];
            }
        }
        return null;
    }

    @Override
    public boolean containsStoreById(int id) {
        for (int i = 0; i < stores.length; i++) {
            if (stores[i] == null) break;
            if (stores[i].getId() == id) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsByStoreName(String name) {
        for (int i = 0; i < stores.length; i++) {
            if (stores[i] == null) break;
            if (stores[i].getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsByStoreAddress(Address address) {
        for (int i = 0; i < stores.length; i++) {
            if (stores[i] == null) break;
            if (stores[i].getAddress().equals(address)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsByStore(Store store) {
        for (int i = 0; i < stores.length; i++) {
            if (stores[i] == null) break;
            if (stores[i].equals(store)) {
                return true;
            }
        }
        return false;
    }
}
