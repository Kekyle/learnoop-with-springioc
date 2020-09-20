package com.company.service;

import com.company.entity.Address;
import com.company.entity.User;
import com.company.repository.UserRepository;
import com.company.repository.inmemory.InMemoryUserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean addUser(User user) {
        if (userRepository.containsByUser(user)) {
            return false;
        }
        return userRepository.saveUser(user);
    }

    @Override
    public boolean updateUserLoginById(String login, int id) {
        if (userRepository.containsUserById(id)) {
            userRepository.updateUserLoginById(login, id);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateUserPasswordById(String password, int id) {
        if (userRepository.containsUserById(id)) {
            userRepository.updateUserPasswordById(password, id);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateUserNameById(String name, int id) {
        if (userRepository.containsUserById(id)) {
            userRepository.updateUserNameById(name, id);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateUserAddressById(Address address, int id) {
        if (userRepository.containsUserById(id)) {
            userRepository.updateUserAddressById(address, id);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateUserAgeById(int age, int id) {
        if (userRepository.containsUserById(id)) {
            userRepository.updateUserAgeById(age, id);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeUserById(int id) {
        if (userRepository.containsUserById(id)) {
            userRepository.deleteUserById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeUserByLogin(String login) {
        if (userRepository.containsUserByLogin(login)) {
            userRepository.deleteUserByLogin(login);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeUser(User user) {
        if (userRepository.containsByUser(user)) {
            userRepository.deleteUser(user);
            return true;
        }
        return false;
    }

    @Override
    public User[] findAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public User[] findAllUsersByAge(int age) {
        if (userRepository.containsUserByAge(age)) {
            return userRepository.getAllUsersByAge(age);
        }
        return null;
    }

    @Override
    public User findUserByLogin(String login) {
        if (userRepository.containsUserByLogin(login)) {
            return userRepository.getUserByLogin(login);
        }
        return null;
    }

    @Override
    public User[] findAllUsersByName(String name) {
        if (userRepository.containsUserByName(name)) {
            return userRepository.getAllUsersByName(name);
        }
        return null;
    }

    @Override
    public User findUserByAddress(Address address) {
        if (userRepository.containsUserByAddress(address)) {
            return userRepository.getUserByAddress(address);
        }
        return null;
    }

    @Override
    public User findUserById(int id) {
        if (userRepository.containsUserById(id)) {
            return userRepository.getUserById(id);
        }
        return null;
    }

    @Override
    public boolean existUserById(int id) {
        return userRepository.containsUserById(id);
    }

    @Override
    public boolean existUserByLogin(String login) {
        return userRepository.containsUserByLogin(login);
    }

    @Override
    public boolean existUserByName(String name) {
        return userRepository.containsUserByName(name);
    }

    @Override
    public boolean existUserByAddress(Address address) {
        return userRepository.containsUserByAddress(address);
    }

    @Override
    public boolean existUserByAge(int age) {
        return userRepository.containsUserByAge(age);
    }

    @Override
    public boolean existUser(User user) {
        return userRepository.containsByUser(user);
    }

}
