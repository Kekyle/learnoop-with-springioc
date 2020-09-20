package com.company.repository.db;

import com.company.entity.Address;
import com.company.repository.AddressRepository;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Component
public class DBAddressRepository extends DBAbstractRepository implements AddressRepository {
    private static final String SAVE_ADDRESS = "insert into addresses values (default, ?)";
    private final String GET_OLD_STREET = "select * from addresses where id=?";
    private final String UPDATE_STREET = "update addresses set street=? where id=?";
    private final String GET_ALL_ADDRESSES = "select * from addresses";
    private final String GET_ADDRESS_BY_ID = "select * from addresses where id=?";
    private final String GET_ADDRESS_BY_STREET = "select * from addresses where street=?";
    private final String DELETE_ADDRESS_BY_ID = "delete from addresses where id=?";
    private final String DELETE_ADDRESS_BY_STREET = "delete from addresses where street=?";
    private final String DELETE_ADDRESS = "delete from addresses where street=?";
    private final String GET_ALL_BY_ADDRESS = "select * from addresses where id=?";

    public DBAddressRepository() throws SQLException {
    }

    @Override
    public boolean saveAddress(Address address) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SAVE_ADDRESS);
            preparedStatement.setString(1, address.getStreet());
            return preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String updateStreetNameById(String street, int id) {
        try {
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement1 = connection.prepareStatement(GET_OLD_STREET);
            preparedStatement1.setInt(1, id);
            ResultSet resultSet = preparedStatement1.executeQuery();
            resultSet.next();
            String oldStreet = resultSet.getString(2);

            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_STREET);
            preparedStatement.setString(1, street);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();

            connection.commit();
            connection.setAutoCommit(true);

            return oldStreet;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Address[] getAllAddresses() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_ADDRESSES);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Address> list = new ArrayList<>();
            while (resultSet.next()) {
                int addressId = resultSet.getInt(1);
                String street = resultSet.getString(2);
                list.add(new Address(addressId, street));
            }
            return list.toArray(new Address[0]);
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public Address getAddressById(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ADDRESS_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                return new Address(resultSet.getInt(1), resultSet.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Address getAddressByStreet(String street) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ADDRESS_BY_STREET);
            preparedStatement.setString(1, street);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                return new Address(resultSet.getInt(1), resultSet.getString(2));
            }
        } catch (SQLException e) {
            return null;
        }
        return null;
    }

    @Override
    public void deleteAddressById(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ADDRESS_BY_ID);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAddressByStreetName(String street) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ADDRESS_BY_STREET);
            preparedStatement.setString(1, street);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAddress(Address address) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ADDRESS);
            preparedStatement.setInt(1, address.getId());
            preparedStatement.setString(2, address.getStreet());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean containsAddressById(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ADDRESS_BY_ID);
            preparedStatement.setInt(1, id);
            return preparedStatement.executeQuery().next();
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean containsAddressByStreetName(String street) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ADDRESS_BY_STREET);
            preparedStatement.setString(1, street);
            return preparedStatement.executeQuery().next();
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean containsAddress(Address address) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_BY_ADDRESS);
            preparedStatement.setInt(1, address.getId());
            preparedStatement.setString(2, address.getStreet());
            return preparedStatement.executeQuery().next();
        } catch (SQLException e) {
            return false;
        }
    }
}
