package com.company.repository.inmemory;

import com.company.entity.Address;
import com.company.entity.User;
import com.company.repository.UserNotFoundException;
import com.company.repository.UserRepository;

public class InMemoryUserRepository implements UserRepository {
    private static User[] users = new User[10];
    private int size = 0;

    @Override
    public boolean saveUser(User user) {
        for (int i = 0; i < users.length; i++) {
            if (users[i] == null) {
                users[i] = user;
                size++;
                return true;
            }
        }
        return false;
    }

    @Override
    public String updateUserLoginById(String login, int id) {
//        String oldLogin;
        for (int i = 0; i < users.length; i++) {
            if (users[i] == null) break;
            if (users[i].getId() == id) {
                String oldLogin = users[i].getLogin();
                users[i].setLogin(login);
                return oldLogin;
            }
        }
        return null;
    }

    @Override
    public String updateUserPasswordById(String password, int id) {
        String oldPassword;
        for (int i = 0; i < users.length; i++) {
            if (users[i] == null) break;
            if (users[i].getId() == id) {
                oldPassword = users[i].getPassword();
                users[i].setPassword(password);
                return oldPassword;
            }
        }
        return null;
    }

    @Override
    public String updateUserNameById(String name, int id) {
        String oldName;
        for (int i = 0; i < users.length; i++) {
            if (users[i] == null) break;
            if (users[i].getId() == id) {
                oldName = users[i].getName();
                users[i].setName(name);
                return oldName;
            }
        }
        return null;
    }

    @Override
    public Address updateUserAddressById(Address address, int id) {
        Address oldAddress;
        for (int i = 0; i < users.length; i++) {
            if (users[i] == null) break;
            if (users[i].getId() == id) {
                oldAddress = users[i].getAddress();
                users[i].setAddress(address);
                return oldAddress;
            }
        }
        return null;
    }

    @Override
    public int updateUserAgeById(int age, int id) {
        int oldAge;
        for (int i = 0; i < users.length; i++) {
            if (users[i] == null) break;
            if (users[i].getId() == id) {
                oldAge = users[i].getAge();
                users[i].setAge(age);
                return oldAge;
            }
        }
        return -1;
    }

    @Override
    public void deleteUserById(int id) {
        for (int i = 0; i < users.length; i++) {
            if (users[i] == null) break;
            if (users[i].getId() == id) {
                for (int j = i; j < users.length - 1; j++) {
                    users[j] = users[j + 1];
                }
                if (users.length == size) {
                    users[users.length - 1] = null;
                }
                size--;
                break;
            }
        }
    }

    @Override
    public void deleteUserByLogin(String login) {
        for (int i = 0; i < users.length; i++) {
            if (users[i] == null) break;
            if (users[i].getLogin().equals(login)) {
                for (int j = i; j < users.length - 1; j++) {
                    users[j] = users[j + 1];
                }
                break;
            }
        }
    }

    @Override
    public void deleteUser(User user) {
        for (int i = 0; i < users.length; i++) {
            if (users[i] == null) break;
            if (users[i].equals(user)) {
                for (int j = i; j < users.length - 1; j++) {
                    users[j] = users[j + 1];
                }
                break;
            }
        }
    }

    @Override
    public User[] getAllUsers() {
        int count = 0;
        for (int i = 0; i < users.length; i++) {
            if (users[i] != null) {
                count++;
            }
        }
        User[] newUsers = new User[count];
        for (int i = 0; i < newUsers.length; i++) {
            newUsers[i] = users[i];
        }
        return newUsers;
    }

    @Override
    public User[] getAllUsersByAge(int age) {
        int count = 0;
        for (int i = 0; i < users.length; i++) {
            if (users[i] == null) break;
            if (users[i].getAge() == age) {
                count++;
            }
        }
        User[] newUsers = new User[count];
        int indexes = 0;
        for (int i = 0; i < users.length; i++) {
            if (users[i] == null) break;
            if (users[i].getAge() == age) {
                newUsers[indexes++] = users[i];
            }
        }
        return newUsers;
    }

    @Override
    public User getUserByLogin(String login) {
        for (int i = 0; i < users.length; i++) {
            if (users[i] == null) break;
            if (users[i].getLogin().equals(login)) {
                return users[i];
            }
        }
        throw new UserNotFoundException("User not found");
    }

    @Override
    public User[] getAllUsersByName(String name) {
        int count = 0;
        for (int i = 0; i < users.length; i++) {
            if (users[i] == null) break;
            if (users[i].getName().equals(name)) {
                count++;
            }
        }
        User[] newUsers = new User[count];
        int indexes = 0;
        for (int i = 0; i < users.length; i++) {
            if (users[i] == null) break;
            if (users[i].getName().equals(name)) {
                newUsers[indexes++] = users[i];
            }
        }
        return newUsers;
    }

    @Override
    public User getUserByAddress(Address address) {
        for (int i = 0; i < users.length; i++) {
            if (users[i] == null) break;
            if (users[i].getAddress().equals(address)) {
                return users[i];
            }
        }
        return null;
    }

    @Override
    public User getUserById(int id) {
        for (int i = 0; i < users.length; i++) {
            if (users[i] == null) break;
            if (users[i].getId() == id) {
                return users[i];
            }
        }
        return null;
    }

    @Override
    public boolean containsUserById(int id) {
        for (int i = 0; i < users.length; i++) {
            if (users[i] == null) break;
            if (users[i].getId() == id) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsUserByLogin(String login) {
        for (int i = 0; i < users.length; i++) {
            if (users[i] == null) break;
            if (users[i].getLogin().equals(login)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsUserByName(String name) {
        for (int i = 0; i < users.length; i++) {
            if (users[i] == null) break;
            if (users[i].getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsUserByAge(int age) {
        for (int i = 0; i < users.length; i++) {
            if (users[i] == null) break;
            if (users[i].getAge() == age) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsUserByAddress(Address address) {
        for (int i = 0; i < users.length; i++) {
            if (users[i] == null) break;
            if (users[i].getAddress().equals(address)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsByUser(User user) {
        for (int i = 0; i < users.length; i++) {
            if (users[i] == null) break;
            if (users[i].equals(user)) {
                return true;
            }
        }
        return false;
    }
}
