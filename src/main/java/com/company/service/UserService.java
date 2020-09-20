package com.company.service;

import com.company.entity.Address;
import com.company.entity.User;

public interface UserService {
    boolean addUser(User user);

    boolean updateUserLoginById(String login, int id);
    boolean updateUserPasswordById(String password, int id);
    boolean updateUserNameById(String name, int id);
    boolean updateUserAddressById(Address address, int id);
    boolean updateUserAgeById(int age, int id);

    boolean removeUserById(int id);
    boolean removeUserByLogin(String login);
    boolean removeUser(User user);

    User[] findAllUsers();
    User[] findAllUsersByAge(int age);
    User findUserByLogin(String login);
    User[] findAllUsersByName(String name);
    User findUserByAddress(Address address);
    User findUserById(int id);

    boolean existUserById(int id);
    boolean existUserByLogin(String login);
    boolean existUserByName(String name);
    boolean existUserByAddress(Address address);
    boolean existUserByAge(int age);
    boolean existUser(User user);
}
