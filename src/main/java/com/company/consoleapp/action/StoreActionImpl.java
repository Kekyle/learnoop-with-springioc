package com.company.consoleapp.action;

import com.company.consoleapp.util.ConsoleReader;
import com.company.consoleapp.util.ConsoleWriter;
import com.company.consoleapp.util.Reader;
import com.company.consoleapp.util.Writer;
import com.company.consoleapp.validator.StoreValidator;
import com.company.entity.Address;
import com.company.entity.Store;
import com.company.service.AddressService;
import com.company.service.AddressServiceImpl;
import com.company.service.StoreService;
import com.company.service.StoreServiceImpl;
import org.springframework.stereotype.Component;

@Component
public class StoreActionImpl implements StoreAction {
    private Writer writer;
    private Reader reader;
    private StoreService storeService;
    private AddressService addressService;

    @Override
    public void addStore() {
        writer.write("\nВведите имя магазина: ");
        String name = reader.readString();
        if (StoreValidator.invalidName(name)) {
            writer.write("\nНекорректное имя магазина (слишком короткое) !\n");
            return;
        }
        writer.write("\nВыберите адрес магазина: \n");
        Address[] allAddresses = addressService.findAllAddresses();
        for (int i = 0; i < allAddresses.length; i++) {
            writer.write("Адрес " + (i + 1) + " - \"" + allAddresses[i].getStreet() + "\"\n");
        }
        writer.write("Вы выбрали: ");
        int i = reader.readInt() - 1;
        if (i < allAddresses.length && i >= 0) {
            Address address = allAddresses[i];
            Store store = new Store(name, address);
            storeService.addStore(store);
        }else {
            writer.write("\nВы выбрали улицу, которой нет в списке\n");
        }
    }

    @Override
    public void updateStoreNameById() {
        writer.write("\nВведите id магазина, у которого хотите обновить имя: ");
        int id = reader.readInt();
        if (StoreValidator.invalidId(id)) {
            writer.write("\nНекорректный id (м.б. < 0)!\n");
            return;
        }
        if (storeService.existStoreById(id)) {
            writer.write("\nВведите новое имя магазина: ");
            String name = reader.readString();
            if (StoreValidator.invalidName(name)) {
                writer.write("\nНекорректное имя магазина (слишком короткое) !\n");
                return;
            }
            storeService.updateStoreNameById(name, id);
        } else {
            writer.write("\nВы ввели id, с которым что-то не то (м.б. такого id вообще нет или id < 0)\n");
        }
    }

    @Override
    public void updateStoreAddressById() {
        writer.write("\nВведите id магазина, у которого хотите изменить адрес: ");
        int id = reader.readInt();
        if (StoreValidator.invalidId(id)) {
            writer.write("\nНекорректный id (м.б. < 0)!\n");
            return;
        }
        if (storeService.existStoreById(id)) {
            writer.write("\nВыберите новый адрес из списка: \n");
            Address[] allAddresses = addressService.findAllAddresses();
            for (int i = 0; i < allAddresses.length; i++) {
                writer.write("Адрес " + (i + 1) + " - \"" + allAddresses[i].getStreet() + "\"\n");
            }
            writer.write("Вы выбрали: ");
            int i = reader.readInt() - 1;
            if (i < allAddresses.length && i >= 0) {
                Address address = allAddresses[i];
                storeService.updateStoreAddressById(address, id);
            }else {
                writer.write("\nВы выбрали улицу, которой нет в списке\n");
            }
        } else {
            writer.write("\nВы ввели id, с которым что-то не то (м.б. такого id вообще нет или id < 0)\n");
        }
    }

    @Override
    public void removeStoreById() {
        writer.write("\nВведите id магазина, который хотите удалить: ");
        int id = reader.readInt();
        if (StoreValidator.invalidId(id)) {
            writer.write("\nНекорректный id (м.б. < 0)!\n");
            return;
        }
        if (storeService.existStoreById(id)) {
            storeService.removeStoreById(id);
        } else {
            writer.write("\nВы ввели id, с которым что-то не то (м.б. такого id вообще нет или id < 0)\n");
        }
    }

    @Override
    public void removeStoreByName() {
        writer.write("\nВведите название магазина, который хотите удалить: ");
        String name = reader.readString();
        if (StoreValidator.invalidName(name)) {
            writer.write("\nНекорректное имя магазина (слишком короткое) !\n");
            return;
        }
        if (storeService.existStoreByName(name)) {
            storeService.removeStoreByName(name);
        }else {
            writer.write("\nВы ввели название магазина, которого у нас нет\n");
        }
    }

    @Override
    public void removeByStore() {
        writer.write("\nВыберите магазин, который хотите удалить: \n");
        Store[] all = storeService.findAllStores();
        for (int i = 0; i < all.length; i++) {
            writer.write("Магазин " + (i + 1) + " - \"" + all[i].getName() + "\"\n");
        }
        writer.write("Вы выбрали: ");
        int i = reader.readInt() - 1;
        if (i < all.length && i >= 0) {
            Store store = all[i];
            storeService.removeByStore(store);
        }else {
            writer.write("\nВы выбрали магазин, которого нет в списке\n");
        }
    }

    @Override
    public void removeStoreByAddress() {
        writer.write("\nВыберите адрес магазина, который хотите удалить: \n");
        Address[] all = addressService.findAllAddresses();
        for (int i = 0; i < all.length; i++) {
            writer.write("Адрес " + (i + 1) + " - \"" + all[i].getStreet() + "\"\n");
        }
        writer.write("Вы выбрали: ");
        int i = reader.readInt() - 1;
        if (i < all.length && i >= 0) {
            Address address = all[i];
            if (storeService.existStoreByAddress(address)) {
                storeService.removeStoreByAddress(address);
                writer.write("\nМагазин по данному адресу был успешно удален !\n");
            }else {
                writer.write("\nУдалить не удалось, возможно по данному адресу магазина нет\n");
            }
        }else {
            writer.write("\nВы выбрали улицу, которой нет в списке\n");
        }
    }

    @Override
    public void findAllStores() {
        Store[] all = storeService.findAllStores();
        System.out.println();
        for (Store store : all) {
            if(store == null) continue;
            writer.write("Магазин - \"" + store.getName() + "\", по адресу \"" + store.getAddress().getStreet() + "\"\n");
        }
    }

    @Override
    public void findStoreById() {
        writer.write("\nВведите id магазина, который хотите найти: ");
        int id = reader.readInt();
        if (StoreValidator.invalidId(id)) {
            writer.write("\nНекорректный id (м.б. < 0)!\n");
            return;
        }
        if (storeService.existStoreById(id)) {
            Store store = storeService.findStoreById(id);
            writer.write("Магазин - \"" + store.getName() + "\"\n");
        } else {
            writer.write("\nВы ввели id, с которым что-то не то (м.б. такого id вообще нет или id < 0)\n");
        }
    }

    @Override
    public void findStoreByName() {
        writer.write("\nВведите название магазина, который хотите найти: ");
        String name = reader.readString();
        if (StoreValidator.invalidName(name)) {
            writer.write("\nНекорректное имя магазина (слишком короткое) !\n");
            return;
        }
        if (storeService.existStoreByName(name)) {
            Store store = storeService.findStoreByName(name);
            writer.write("Магазин \"" + store.getName() + "\"\n");
        }else {
            writer.write("\nВы ввели название магазина, которого у нас нет\n");
        }
    }

    @Override
    public void findStoreByAddress() {
        writer.write("\nВыберите адрес магазина, который хотите найти: \n");
        Address[] all = addressService.findAllAddresses();
        for (int i = 0; i < all.length; i++) {
            writer.write("Адрес " + (i + 1) + " - \"" + all[i].getStreet() + "\"\n");
        }
        writer.write("Вы выбрали: ");
        int i = reader.readInt() - 1;
        if (i < all.length && i >= 0) {
            Address address = all[i];
            if (storeService.existStoreByAddress(address)) {
                Store store = storeService.findStoreByAddress(address);
                writer.write("Магазин \"" + store.getName() + "\"\n");
            }else{
                writer.write("\nМагазина по такому адресу нет\n");
            }
        }else {
            writer.write("\nВы выбрали улицу, которой нет в списке\n");
        }
    }
}
