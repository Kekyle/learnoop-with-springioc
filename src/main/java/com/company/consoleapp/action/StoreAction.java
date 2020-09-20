package com.company.consoleapp.action;

public interface StoreAction {
    void addStore();

    void updateStoreNameById();

    void updateStoreAddressById();

    void removeStoreById();

    void removeStoreByName();

    void removeByStore();

    void removeStoreByAddress();

    void findAllStores();

    void findStoreById();

    void findStoreByName();

    void findStoreByAddress();
}
