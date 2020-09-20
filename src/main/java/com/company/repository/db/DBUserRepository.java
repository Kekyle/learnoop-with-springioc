package com.company.repository.db;

import com.company.entity.Address;
import com.company.entity.Role;
import com.company.entity.User;
import com.company.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Component
public class DBUserRepository extends DBAbstractRepository implements UserRepository {
    private final String SAVE_USER = "insert into users values(default, ?, ?, ?, ?, ?, ?)";
    private final String DELETE_USER_BY_ID = "delete from users where id=?";
    private final String GET_ALL_USERS = "select users.id, login, password, age, name, a.street, r.role from users join addresses a on users.address_id = a.id join roles r on users.role_id = r.id";
    private final String GET_OLD_LOGIN = "select * from users where id=?";
    private final String UPDATE_LOGIN = "update users set login=? where id=?";
    private final String GET_OLD_PASSWORD = "select * from users where id=?";
    private final String UPDATE_PASSWORD = "update users set password=? where id=?";
    private final String GET_OLD_NAME = "select * from users where id=?";
    private final String UPDATE_NAME = "update users set name=? where id=?";
    private final String GET_OLD_AGE = "select * from users where id=?";
    private final String UPDATE_AGE = "update users set age=? where id=?";
    private final String DELETE_USER_BY_LOGIN = "delete from users where login=?";
    private final String DELETE_USER = "delete from users where login=?";
    private final String GET_USER_BY_LOGIN = "select users.id, login, password, age, name, a.street, r.role from users join addresses a on users.address_id = a.id join roles r on users.role_id = r.id where login=?";
    private final String GET_USER_BY_ADDRESS = "select users.id, login, password, age, name, a.street, r.role from users join addresses a on users.address_id = a.id join roles r on users.role_id = r.id where address_id=?";
    private final String GET_USER_BY_ID = "select users.id, login, password, age, name, a.street, r.role from users join addresses a on users.address_id = a.id join roles r on users.role_id = r.id where users.id=?";
    private final String CONTAINS_USER_BY_ID = "select * from users where id=?";
    private final String CONTAINS_USER_BY_LOGIN = "select * from users where login=?";
    private final String CONTAINS_USER_BY_NAME = "select * from users where name=?";
    private final String CONTAINS_USER_BY_AGE = "select * from users where age=?";
    private final String CONTAINS_BY_USER = "select * from users where login=?";
    private final String UPDATE_ADDRESS_BY_ID = "update users set address_id=? where id=?";
    private final String GET_ADDRESS_ID_BY_NAME = "select a.id from addresses a where a.street=?";
    private final String GET_ADDRESS_BY_USER = "select a.id, a.street from users join addresses a on users.address_id = a.id where users.id=?";
    private Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

    public DBUserRepository() throws SQLException {
    }

    public static void main(String[] args) throws SQLException {
        DBUserRepository dbUserRepository = new DBUserRepository();
//        dbUserRepository.saveUser(new User("bb", "bb", 22, "bb", new Address(3, "truda 40"), Role.ADMIN));
//        System.out.println(dbUserRepository.updateUserLoginById("new", 7));
//        System.out.println(dbUserRepository.updateUserPasswordById("new", 7));
//        System.out.println(dbUserRepository.updateUserAgeById(99, 7));
//        System.out.println(dbUserRepository.updateUserNameById("new", 7));
//        System.out.println(dbUserRepository.updateUserAddressById(new Address(5, "mira 50"), 6));
//        dbUserRepository.deleteUserById(5);
//        dbUserRepository.deleteUser(new User("bb", "bb", 22, "bb", new Address(1, "malina 30"), Role.USER));
//        dbUserRepository.deleteUserByLogin("bb");
//        System.out.println(Arrays.toString(dbUserRepository.getAllUsers()));
//        System.out.println(Arrays.toString(dbUserRepository.getAllUsersByAge(22)));
//        System.out.println(dbUserRepository.getUserByLogin("new"));
//        System.out.println(dbUserRepository.getUserByAddress(new Address(1, "malina 30")));
//        System.out.println(dbUserRepository.getUserById(8));
//        System.out.println(dbUserRepository.updateUserAddressById(new Address(2, "truda 40"), 9));
    }

    @Override
    public boolean saveUser(User user) {
        try {
            int roleId = getRoleId(user);
            PreparedStatement preparedStatement1 = connection.prepareStatement("select a.id from addresses a where street=?");
            preparedStatement1.setString(1, user.getAddress().getStreet());
            ResultSet resultSet = preparedStatement1.executeQuery();
            resultSet.next();
            int userAddressId = resultSet.getInt(1);
            PreparedStatement preparedStatement = connection.prepareStatement(SAVE_USER);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setInt(3, user.getAge());
            preparedStatement.setString(4, user.getName());
            preparedStatement.setInt(5, userAddressId);
            preparedStatement.setInt(6, roleId);
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private int getRoleId(User user) {
        int roleId;
        if (user.getRole().equals(Role.ADMIN)) {
            return roleId = 1;
        } else if (user.getRole().equals(Role.USER)) {
            return roleId = 2;
        }
        return -1;
    }

    @Override
    public String updateUserLoginById(String login, int id) {
        try {
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement(GET_OLD_LOGIN);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String oldLogin = resultSet.getString(2);

            PreparedStatement preparedStatement1 = connection.prepareStatement(UPDATE_LOGIN);
            preparedStatement1.setString(1, login);
            preparedStatement1.setInt(2, id);
            preparedStatement1.executeUpdate();

            connection.commit();
            connection.setAutoCommit(true);

            return oldLogin;
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
    public String updateUserPasswordById(String password, int id) {
        try {
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement(GET_OLD_PASSWORD);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String oldPassword = resultSet.getString(3);

            PreparedStatement preparedStatement1 = connection.prepareStatement(UPDATE_PASSWORD);
            preparedStatement1.setString(1, password);
            preparedStatement1.setInt(2, id);
            preparedStatement1.executeUpdate();

            connection.commit();
            connection.setAutoCommit(true);

            return oldPassword;
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
    public String updateUserNameById(String name, int id) {
        try {
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement(GET_OLD_NAME);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String oldName = resultSet.getString(5);

            PreparedStatement preparedStatement1 = connection.prepareStatement(UPDATE_NAME);
            preparedStatement1.setString(1, name);
            preparedStatement1.setInt(2, id);
            preparedStatement1.executeUpdate();

            connection.commit();
            connection.setAutoCommit(true);

            return oldName;
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
    public Address updateUserAddressById(Address address, int id) {
        try {
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement(GET_ADDRESS_BY_USER);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int oldAddressId = resultSet.getInt(1);
            String oldStreet = resultSet.getString(2);

            PreparedStatement preparedStatement2 = connection.prepareStatement(GET_ADDRESS_ID_BY_NAME);
            preparedStatement2.setString(1, address.getStreet());
            ResultSet resultSet1 = preparedStatement2.executeQuery();
            resultSet1.next();
            int addressId = resultSet1.getInt(1);
            PreparedStatement preparedStatement1 = connection.prepareStatement(UPDATE_ADDRESS_BY_ID);
            preparedStatement1.setInt(1, addressId);
            preparedStatement1.setInt(2, id);
            preparedStatement1.executeUpdate();

            connection.commit();
            connection.setAutoCommit(true);

            return new Address(oldAddressId, oldStreet);
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
    public int updateUserAgeById(int age, int id) {
        try {
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement(GET_OLD_AGE);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int oldAge = resultSet.getInt(4);

            PreparedStatement preparedStatement1 = connection.prepareStatement(UPDATE_AGE);
            preparedStatement1.setInt(1, age);
            preparedStatement1.setInt(2, id);
            preparedStatement1.executeUpdate();

            connection.commit();
            connection.setAutoCommit(true);

            return oldAge;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return 0;
    }

    @Override
    public void deleteUserById(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_BY_ID);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUserByLogin(String login) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_BY_LOGIN);
            preparedStatement.setString(1, login);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUser(User user) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User[] getAllUsers() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_USERS);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<User> list = new ArrayList<>();
            while (resultSet.next()) {
                int userId = resultSet.getInt(1);
                String login = resultSet.getString(2);
                String password = resultSet.getString(3);
                int age = resultSet.getInt(4);
                String name = resultSet.getString(5);
                String street = resultSet.getString(6);
                String userRole = resultSet.getString(7);
                Address address = new Address(street);
                Role role = Role.valueOf(userRole);
                list.add(new User(userId, login, password, age, name, address, role));
            }
            return list.toArray(new User[0]);
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public User[] getAllUsersByAge(int age) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_USERS);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<User> list = new ArrayList<>();
            while (resultSet.next()) {
                int userId = resultSet.getInt(1);
                String login = resultSet.getString(2);
                String password = resultSet.getString(3);
                int userAge = resultSet.getInt(4);
                String name = resultSet.getString(5);
                String street = resultSet.getString(6);
                String userRole = resultSet.getString(7);
                Address address = new Address(street);
                Role role = Role.valueOf(userRole);
                if (userAge == age) {
                    list.add(new User(userId, login, password, userAge, name, address, role));
                }
            }
            return list.toArray(new User[0]);
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public User getUserByLogin(String login) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_LOGIN);
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String street = resultSet.getString(6);
                String userRole = resultSet.getString(7);
                Address address = new Address(street);
                Role role = Role.valueOf(userRole);
                return new User(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getInt(4),
                        resultSet.getString(5),
                        address,
                        role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User[] getAllUsersByName(String name) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_USERS);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<User> list = new ArrayList<>();
            while (resultSet.next()) {
                int userId = resultSet.getInt(1);
                String login = resultSet.getString(2);
                String password = resultSet.getString(3);
                int age = resultSet.getInt(4);
                String userName = resultSet.getString(5);
                String street = resultSet.getString(6);
                String userRole = resultSet.getString(7);
                Address address = new Address(street);
                Role role = Role.valueOf(userRole);
                if (userName.equals(name)) {
                    list.add(new User(userId, login, password, age, userName, address, role));
                }
            }
            return list.toArray(new User[0]);
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public User getUserByAddress(Address address) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_ADDRESS);
            preparedStatement.setInt(1, address.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String street = resultSet.getString(6);
                String userRole = resultSet.getString(7);
                Address userAddress = new Address(street);
                Role role = Role.valueOf(userRole);
                return new User(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getInt(4),
                        resultSet.getString(5),
                        userAddress,
                        role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User getUserById(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String street = resultSet.getString(6);
                String userRole = resultSet.getString(7);
                Address userAddress = new Address(street);
                Role role = Role.valueOf(userRole);
                return new User(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getInt(4),
                        resultSet.getString(5),
                        userAddress,
                        role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean containsUserById(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(CONTAINS_USER_BY_ID);
            preparedStatement.setInt(1, id);
            return preparedStatement.executeQuery().next();
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean containsUserByLogin(String login) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(CONTAINS_USER_BY_LOGIN);
            preparedStatement.setString(1, login);
            return preparedStatement.executeQuery().next();
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean containsUserByName(String name) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(CONTAINS_USER_BY_NAME);
            preparedStatement.setString(1, name);
            return preparedStatement.executeQuery().next();
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean containsUserByAge(int age) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(CONTAINS_USER_BY_AGE);
            preparedStatement.setInt(1, age);
            return preparedStatement.executeQuery().next();
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean containsUserByAddress(Address address) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_ADDRESS);
            preparedStatement.setInt(1, address.getId());
            return preparedStatement.executeQuery().next();
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean containsByUser(User user) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(CONTAINS_BY_USER);
            preparedStatement.setString(1, user.getLogin());
            return preparedStatement.executeQuery().next();
        } catch (SQLException e) {
            return false;
        }
    }
}
