package com.company.consoleapp.action;

import com.company.consoleapp.util.ConsoleReader;
import com.company.consoleapp.util.ConsoleWriter;
import com.company.consoleapp.util.Reader;
import com.company.consoleapp.util.Writer;
import com.company.consoleapp.validator.AddressValidator;
import com.company.entity.Address;
import com.company.service.AddressService;
import com.company.service.AddressServiceImpl;
import org.springframework.stereotype.Component;

@Component
public class AddressActionImpl implements AddressAction {
    private Writer writer;
    private Reader reader;
    private AddressService addressService;

    @Override
    public void addAddress() {
        writer.write("\nВведите название улицы: ");
        String street = reader.readString();
        if (AddressValidator.invalidStreet(street)) {
            writer.write("\nНекорректное название улицы (слишком короткое) !\n");
            return;
        }
        Address address = new Address(street);
        addressService.addAddress(address);
    }

    @Override
    public void updateStreetNameById() {
        writer.write("\nВведите id улицы, у которой хотите обновить название: ");
        int id = reader.readInt();
        if (AddressValidator.invalidId(id)) {
            writer.write("\nНекорректный id (м.б. < 0)!\n");
            return;
        }
        if (addressService.existAddressById(id)) {
            writer.write("\nВведите новое название улицы: ");
            String newStreet = reader.readString();
            if (AddressValidator.invalidStreet(newStreet)) {
                writer.write("\nНекорректное название улицы (слишком короткое) !\n");
                return;
            }
            addressService.updateStreetNameById(newStreet, id);
        } else {
            writer.write("\nВы ввели id, с которым что-то не то (м.б. такого id вообще нет или id < 0)\n");
        }
    }

    @Override
    public void findAllAddresses() {
        Address[] all = addressService.findAllAddresses();
        System.out.println();
        for (Address address : all) {
            if(address == null) continue;
            writer.write("Улица: " + address.getStreet() + "\n");
        }
    }

    @Override
    public void findAddressById() {
        writer.write("\nВведите id улицы, которую хотите найти: ");
        int id = reader.readInt();
        if (AddressValidator.invalidId(id)) {
            writer.write("\nНекорректный id (м.б. < 0)!\n");
            return;
        }
        if (addressService.existAddressById(id)) {
            Address addressById = addressService.findAddressById(id);
            writer.write("\nУлица: " + addressById.getStreet() + "\n");
        } else {
            writer.write("\nВы ввели id, с которым что-то не то (м.б. такого id вообще нет или id < 0)\n");
        }
    }

    @Override
    public void findAddressByStreetName() {
        writer.write("\nВведите название улицы, которую хотите найти: ");
        String street = reader.readString();
        if (AddressValidator.invalidStreet(street)) {
            writer.write("\nНекорректное название улицы (слишком короткое) !\n");
            return;
        }
        if (addressService.existAddressByStreetName(street)) {
            Address addressByStreet = addressService.findAddressByStreetName(street);
            writer.write("\nУлица: " + addressByStreet.getStreet() + "\n");
        } else {
            writer.write("\nВы ввели название улицы, адреса по которой у нас нет\n");
        }
    }

    @Override
    public void removeAddressById() {
        writer.write("\nВведите id адреса, который хотите удалить: ");
        int id = reader.readInt();
        if (AddressValidator.invalidId(id)) {
            writer.write("\nНекорректный id (м.б. < 0)!\n");
            return;
        }
        if (addressService.existAddressById(id)) {
            addressService.removeAddressById(id);
            writer.write("\nАдрес успешно удален !\n");
        } else {
            writer.write("\nВы ввели id, с которым что-то не то (м.б. такого id вообще нет или id < 0)\n");
        }
    }

    @Override
    public void removeAddressByStreet() {
        writer.write("\nВведите название улицы, которую хотите удалить: ");
        String street = reader.readString();
        if (AddressValidator.invalidStreet(street)) {
            writer.write("\nНекорректное название улицы (слишком короткое) !\n");
            return;
        }
        if (addressService.existAddressByStreetName(street)) {
            addressService.removeAddressByStreet(street);
            writer.write("\nАдрес успешно удален !\n");
        } else {
            writer.write("\nВы ввели название улицы, адреса по которой у нас нет\n");
        }
    }

    @Override
    public void removeByAddress() {
        writer.write("\nВыберите адрес, который хотите удалить:\n");
        Address[] all = addressService.findAllAddresses();
        for (int i = 0; i < all.length; i++) {
            writer.write("Адрес " + (i + 1) + " - " + all[i].getStreet() + "\n");
        }
        writer.write("Вы выбрали: ");
        int addressSelection = reader.readInt() - 1;
        if (AddressValidator.invalidId(addressSelection)) {
            writer.write("\nНекорректный id (м.б. < 0)!\n");
            return;
        }
        if (addressSelection < all.length && addressSelection >= 0) {
            Address address = all[addressSelection];
            addressService.removeByAddress(address);
            writer.write("\nАдрес успешно удален !\n");
        } else {
            writer.write("\nВы выбрали улицу, которой нет в списке\n");
        }
    }
}
