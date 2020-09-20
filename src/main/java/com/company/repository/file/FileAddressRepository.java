package com.company.repository.file;

import com.company.entity.Address;
import com.company.repository.AddressRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileAddressRepository extends FileAbstractRepository implements AddressRepository {
    private static final int STREET = 1;

    public FileAddressRepository(File file) {
        super(file);
    }

    @Override
    public boolean saveAddress(Address address) {
        try {
            FileWriter fileWriter = new FileWriter(file, true);
            fileWriter.append("\n");
            int newId = getLastId() + 1;
            address.setId(newId);
            fileWriter.write(address.getId() + SPLITTER + address.getStreet());
            fileWriter.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

//    private int getLastId() {
//        try {
//            FileReader fileReader = new FileReader(file);
//            BufferedReader bufferedReader = new BufferedReader(fileReader);
//            String line = null;
//            while (true) {
//                String s = bufferedReader.readLine();
//                if (s == null) {
//                    return Integer.parseInt(line.split(SPLITTER)[0]);
//                }
//                line = s;
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return 0;
//    }

    @Override
    public String updateStreetNameById(String street, int id) {
        List<Address> list = new ArrayList<>();
        List<String> listComment = new ArrayList<>();
        addFromFileToAddressList(list, listComment);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == null) continue;
            if (list.get(i).getId() == id) {
                String oldStreet = list.get(i).getStreet();
                list.get(i).setStreet(street);
                writeAll(list, listComment);
                return oldStreet;
            }
        }
        return null;
    }

    private void writeAll(List<Address> addressList, List<String> comments) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            int count = 0;
            int nullCount = 0;
            for (Address address : addressList) {
                if (address == null) {
                    bufferedWriter.write(comments.get(nullCount++));
                } else {
                    bufferedWriter.write(address.getId() + SPLITTER + address.getStreet());
                }
                if (count != addressList.size() - 1) {
                    bufferedWriter.write("\n");
                    count++;
                }
            }
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Address[] getAllAddresses() {
        List<Address> list = new ArrayList<>();
        List<String> listComment = new ArrayList<>();
        addFromFileToAddressList(list, listComment);
        return list.toArray(new Address[0]);
    }

    @Override
    public Address getAddressById(int id) {
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                String[] split = s.split(SPLITTER);
                if (split[ID].equals("#")) continue;
                if (Integer.parseInt(split[ID]) == id) {
                    return new Address(split[STREET]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Address getAddressByStreet(String street) {
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                String[] split = s.split(SPLITTER);
                if (split[ID].equals("#")) continue;
                if (split[STREET].equals(street)) {
                    return new Address(split[STREET]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteAddressById(int id) {
        List<Address> list = new ArrayList<>();
        List<String> listComment = new ArrayList<>();
        addFromFileToAddressList(list, listComment);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == null) continue;
            if (list.get(i).getId() == id) {
                list.remove(i);
                writeAll(list, listComment);
                break;
            }
        }
    }

    private void addFromFileToAddressList(List<Address> list, List<String> comments) {
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                String[] split = s.split(SPLITTER);
                if (split[ID].equals("#")) {
                    list.add(null);
                    comments.add(s);
                    continue;
                }
                Address address = new Address(Integer.parseInt(split[ID]), split[STREET]);
                list.add(address);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAddressByStreetName(String street) {
        List<Address> list = new ArrayList<>();
        List<String> listComment = new ArrayList<>();
        addFromFileToAddressList(list, listComment);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == null) continue;
            if (list.get(i).getStreet().equals(street)) {
                list.remove(i);
                writeAll(list, listComment);
                break;
            }
        }
    }

    @Override
    public void deleteAddress(Address address) {
        List<Address> list = new ArrayList<>();
        List<String> listComment = new ArrayList<>();
        addFromFileToAddressList(list, listComment);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == null) continue;
            if (list.get(i).equals(address)) {
                list.remove(i);
                writeAll(list, listComment);
                break;
            }
        }
    }

    @Override
    public boolean containsAddressById(int id) {
        return super.containsByIdOrPrice(id, ID);
//        try {
//            FileReader fileReader = new FileReader(file);
//            BufferedReader bufferedReader = new BufferedReader(fileReader);
//            String s;
//            while ((s = bufferedReader.readLine()) != null) {
//                String[] split = s.split(SPLITTER);
//                if (split[ID].equals("#")) continue;
//                if (Integer.parseInt(split[ID]) == id) {
//                    return true;
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return false;
    }

    @Override
    public boolean containsAddressByStreetName(String street) {
        return super.containsByName(street, STREET);
//        try {
//            FileReader fileReader = new FileReader(file);
//            BufferedReader bufferedReader = new BufferedReader(fileReader);
//            String s;
//            while ((s = bufferedReader.readLine()) != null) {
//                String[] split = s.split(SPLITTER);
//                if (split[ID].equals("#")) continue;
//                if (split[STREET].equals(street)) {
//                    return true;
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return false;
    }

    @Override
    public boolean containsAddress(Address address) {
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                String[] split = s.split(SPLITTER);
                if (split[ID].equals("#")) continue;
                if (split[STREET].equals(address.getStreet())) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
