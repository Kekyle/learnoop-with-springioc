package com.company.repository.db;

import com.company.entity.Address;
import com.company.entity.Store;
import com.company.repository.StoreRepository;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Component
public class DBStoreRepository extends DBAbstractRepository implements StoreRepository {

    private final String GET_ADDRESS = "select a.id, a.street from stores s join addresses a on s.address_id = a.id";
    private final String SAVE_STORE = "insert into stores values(default, ?, ?)";
    private final String SAVE_OLD_NAME = "select * from stores where id=?";
    private final String UPDATE_STORE_NAME = "update stores set name=? where id=?";
    private final String GET_OLD_ADDRESS = "select a.id, a.street from stores s join addresses a on s.address_id = a.id where s.id=?";
    private final String UPDATE_STORE_ADDRESS_BY_ID = "update stores set address_id=? where id=?";
    private final String DELETE_STORE_BY_ID = "delete from stores where id=?";
    private final String DELETE_STORE_BY_NAME = "delete from stores where name=?";
    private final String DELETE_STORE = "delete from stores where id=?";
    private final String DELETE_STORE_BY_ADDRESS = "delete from stores where address_id=?";
    private final String GET_ALL_STORES = "select * from stores";
    private final String GET_STORE_BY_ID = "select * from stores where id=?";
    private final String GET_STORE_BY_NAME = "select * from stores where name=?";
    private final String GET_STORE_BY_ADDRESS = "select * from stores where address_id=?";

    public DBStoreRepository() throws SQLException {
    }

    public static void main(String[] args) throws SQLException {
        DBStoreRepository dbStoreRepository = new DBStoreRepository();
//        dbStoreRepository.saveStore(new Store("store1", new Address(1,"malina 30")));
//        dbStoreRepository.saveStore(new Store("store2", new Address(3,"truda 40")));
//        System.out.println(dbStoreRepository.updateStoreAddressById(new Address(1, "malina 30"), 2));
//        System.out.println(dbStoreRepository.updateStoreNameById("new", 2));
//        dbStoreRepository.deleteStoreById(2);
//        dbStoreRepository.deleteStoreByName("store2");
//        dbStoreRepository.deleteStoreByAddress(new Address(3,"truda 40"));
//        dbStoreRepository.deleteByStore(new Store(7, "store2", new Address(3,"truda 40")));
//        System.out.println(Arrays.toString(dbStoreRepository.getAllStores()));
//        System.out.println(dbStoreRepository.getStoreByAddress(new Address(3, "truda 40")));
//        System.out.println(dbStoreRepository.getStoreById(8));
//        System.out.println(dbStoreRepository.getStoreByName("store2"));
//        System.out.println(dbStoreRepository.containsStoreById(8));
//        System.out.println(dbStoreRepository.containsByStoreName("store5"));
//        System.out.println(dbStoreRepository.containsByStoreAddress(new Address(3, "truda 40")));
//        System.out.println(dbStoreRepository.containsByStore(new Store(8, "store1", new Address(1, "malina 30"))));
    }

    @Override
    public boolean saveStore(Store store) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SAVE_STORE);
            preparedStatement.setString(1, store.getName());
            preparedStatement.setInt(2, store.getAddress().getId());
            return preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Store findOne(ResultSet resultSet){
        try {
            resultSet.next();
            int id = resultSet.getInt(1);
            String storeName = resultSet.getString(2);
            Address address = getAddress();
            return new Store(id, storeName, address);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    private List<Store> findAll(ResultSet resultSet){
        try {
            List<Store> list = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String storeName = resultSet.getString(2);
                Address address = getAddress();
                list.add(new Store(id, storeName, address));
            }
            return list;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    private Address getAddress() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(GET_ADDRESS);
        ResultSet resultSet1 = preparedStatement.executeQuery();
        resultSet1.next();
        int addressId = resultSet1.getInt(1);
        String street = resultSet1.getString(2);
        return new Address(addressId, street);
    }

    @Override
    public String updateStoreNameById(String name, int id) {
        try {
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement(SAVE_OLD_NAME);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String oldName = resultSet.getString(2);

            PreparedStatement preparedStatement1 = connection.prepareStatement(UPDATE_STORE_NAME);
            preparedStatement1.setString(1, name);
            preparedStatement1.setInt(2, id);
            preparedStatement1.executeUpdate();

            connection.commit();
            connection.setAutoCommit(true);
            return oldName;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Address updateStoreAddressById(Address address, int id) {
        try {
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement(GET_OLD_ADDRESS);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int addressId = resultSet.getInt(1);
            String street = resultSet.getString(2);
            Address address1 = new Address(addressId, street);

            PreparedStatement preparedStatement1 = connection.prepareStatement(UPDATE_STORE_ADDRESS_BY_ID);
            preparedStatement1.setInt(1, address.getId());
            preparedStatement1.setInt(2, id);
            preparedStatement1.executeUpdate();

            connection.commit();
            connection.setAutoCommit(true);
            return address1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteStoreById(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_STORE_BY_ID);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deleteStoreByName(String name) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_STORE_BY_NAME);
            preparedStatement.setString(1, name);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteByStore(Store store) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_STORE);
            preparedStatement.setInt(1, store.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteStoreByAddress(Address address) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_STORE_BY_ADDRESS);
            preparedStatement.setInt(1, address.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Store[] getAllStores() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_STORES);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Store> list = findAll(resultSet);
            return list != null ? list.toArray(new Store[0]) : null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Store getStoreById(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_STORE_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return findOne(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Store getStoreByName(String name) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_STORE_BY_NAME);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            return findOne(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Store getStoreByAddress(Address address) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_STORE_BY_ADDRESS);
            preparedStatement.setInt(1, address.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            return findOne(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean containsStoreById(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_STORE_BY_ID);
            preparedStatement.setInt(1, id);
            return preparedStatement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean containsByStoreName(String name) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_STORE_BY_NAME);
            preparedStatement.setString(1, name);
            return preparedStatement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean containsByStoreAddress(Address address) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_STORE_BY_ADDRESS);
            preparedStatement.setInt(1, address.getId());
            return preparedStatement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean containsByStore(Store store) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_STORE_BY_ID);
            preparedStatement.setInt(1, store.getId());
            return preparedStatement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
