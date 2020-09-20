package com.company.repository.file;

import com.company.entity.Address;
import com.company.entity.Store;
import com.company.repository.StoreRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileStoreRepository extends FileAbstractRepository implements StoreRepository {
    private static int STORE_NAME = 1;
    private static int ADDRESS_ID = 2;

    public FileStoreRepository(File file) {
        super(file);
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
    public boolean saveStore(Store store) {
        try {
            FileWriter fileWriter = new FileWriter(file, true);
            fileWriter.append("\n");
            int newId = getLastId() + 1;
            store.setId(newId);
            fileWriter.write(store.getId() + SPLITTER
                    + store.getName() + SPLITTER
                    + store.getAddress().getId());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void writeAll(List<Store> listStore, List<String> comments) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            int count = 0;
            int nullCount = 0;
            for (Store store : listStore) {
                if (store == null) {
                    bufferedWriter.write(comments.get(nullCount++));
                } else {
                    bufferedWriter.write(store.getId() + SPLITTER
                            + store.getName() + SPLITTER
                            + store.getAddress().getId());
                }
                if (count != listStore.size() - 1) {
                    bufferedWriter.write("\n");
                    count++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addFromFileToStoreList(List<Store> list, List<String> comments) {
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
                Store store = new Store(Integer.parseInt(split[ID]),
                        split[STORE_NAME],
                        new Address(Integer.parseInt(split[ADDRESS_ID]), null));
                list.add(store);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String updateStoreNameById(String name, int id) {
        List<Store> list = new ArrayList<>();
        List<String> listComment = new ArrayList<>();
        addFromFileToStoreList(list, listComment);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == null) continue;
            if (list.get(i).getId() == id) {
                String oldName = list.get(i).getName();
                list.get(i).setName(name);
                writeAll(list, listComment);
                return oldName;
            }
        }
        return null;
    }

    @Override
    public Address updateStoreAddressById(Address address, int id) {
        List<Store> list = new ArrayList<>();
        List<String> listComment = new ArrayList<>();
        addFromFileToStoreList(list, listComment);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == null) continue;
            if (list.get(i).getId() == id) {
                Address oldAddress = list.get(i).getAddress();
                list.get(i).setAddress(address);
                writeAll(list, listComment);
                return oldAddress;
            }
        }
        return null;
    }

    @Override
    public void deleteStoreById(int id) {
        List<Store> list = new ArrayList<>();
        List<String> listComment = new ArrayList<>();
        addFromFileToStoreList(list, listComment);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == null) continue;
            if (list.get(i).getId() == id) {
                list.remove(i);
                writeAll(list, listComment);
                break;
            }
        }
    }

    @Override
    public void deleteStoreByName(String name) {
        List<Store> list = new ArrayList<>();
        List<String> listComment = new ArrayList<>();
        addFromFileToStoreList(list, listComment);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == null) continue;
            if (list.get(i).getName().equals(name)) {
                list.remove(i);
                writeAll(list, listComment);
                break;
            }
        }
    }

    @Override
    public void deleteByStore(Store store) {
        List<Store> list = new ArrayList<>();
        List<String> listComment = new ArrayList<>();
        addFromFileToStoreList(list, listComment);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == null) continue;
            if (list.get(i).equals(store)) {
                list.remove(i);
                writeAll(list, listComment);
                break;
            }
        }
    }

    @Override
    public void deleteStoreByAddress(Address address) {
        List<Store> list = new ArrayList<>();
        List<String> listComment = new ArrayList<>();
        addFromFileToStoreList(list, listComment);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == null) continue;
            if (list.get(i).getAddress().equals(address)) {
                list.remove(i);
                writeAll(list, listComment);
                break;
            }
        }
    }

    @Override
    public Store[] getAllStores() {
        List<Store> list = new ArrayList<>();
        List<String> listComment = new ArrayList<>();
        addFromFileToStoreList(list, listComment);
        return list.toArray(new Store[0]);
    }

    @Override
    public Store getStoreById(int id) {
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                String[] split = s.split(SPLITTER);
                if (split[ID].equals("#")) continue;
                if (Integer.parseInt(split[ID]) == id) {
                    return new Store(Integer.parseInt(split[ID]),
                            split[STORE_NAME],
                            new Address(Integer.parseInt(split[ADDRESS_ID]), null));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Store getStoreByName(String name) {
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String s;
            while ((s = bufferedReader.readLine()) != null){
                String[] split = s.split(SPLITTER);
                if (split[ID].equals("#")) continue;
                if (split[STORE_NAME].equals(name)) {
                    return new Store(Integer.parseInt(split[ID]),
                            split[STORE_NAME],
                            new Address(Integer.parseInt(split[ADDRESS_ID]), null));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Store getStoreByAddress(Address address) {
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String s;
            while ((s = bufferedReader.readLine()) != null){
                String[] split = s.split(SPLITTER);
                if (split[ID].equals("#")) continue;
                if (Integer.parseInt(split[ADDRESS_ID]) == address.getId()) {
                    return new Store(Integer.parseInt(split[ID]),
                            split[STORE_NAME],
                            new Address(Integer.parseInt(split[ADDRESS_ID]), null));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean containsStoreById(int id) {
        return super.containsByIdOrPrice(id, ID);
//        try {
//            FileReader fileReader = new FileReader(file);
//            BufferedReader bufferedReader = new BufferedReader(fileReader);
//            String s;
//            while ((s = bufferedReader.readLine()) != null){
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
    public boolean containsByStoreName(String name) {
        return super.containsByName(name, STORE_NAME);
//        try {
//            FileReader fileReader = new FileReader(file);
//            BufferedReader bufferedReader = new BufferedReader(fileReader);
//            String s;
//            while ((s = bufferedReader.readLine()) != null) {
//                String[] split = s.split(SPLITTER);
//                if (split[ID].equals("#")) continue;
//                if (split[STORE_NAME].equals(name)) {
//                    return true;
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return false;
    }

    @Override
    public boolean containsByStoreAddress(Address address) {
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                String[] split = s.split(SPLITTER);
                if (split[ID].equals("#")) continue;
                if (Integer.parseInt(split[ADDRESS_ID]) == address.getId()) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean containsByStore(Store store) {
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                String[] split = s.split(SPLITTER);
                if (split[ID].equals("#")) continue;
                if (split[STORE_NAME].equals(store.getName())) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
