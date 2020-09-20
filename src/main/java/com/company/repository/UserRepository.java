package com.company.repository;

import com.company.entity.Address;
import com.company.entity.User;

public interface UserRepository {
    boolean saveUser(User user);

    String updateUserLoginById(String login, int id);
    String updateUserPasswordById(String password, int id);
    String updateUserNameById(String name, int id);
    Address updateUserAddressById(Address address, int id);
    int updateUserAgeById(int age, int id);

    void deleteUserById(int id);
    void deleteUserByLogin(String login);
    void deleteUser(User user);

    User[] getAllUsers();
    User[] getAllUsersByAge(int age);
    User getUserByLogin(String login);
    User[] getAllUsersByName(String name);
    User getUserByAddress(Address address);
    User getUserById(int id);

    boolean containsUserById(int id);
    boolean containsUserByLogin(String login);
    boolean containsUserByName(String name);
    boolean containsUserByAge(int age);
    boolean containsUserByAddress(Address address);
    boolean containsByUser(User user);
}
