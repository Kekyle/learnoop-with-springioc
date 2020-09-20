package com.company.consoleapp.action;

public interface UserAction {
    void addUser();

    void updateUserLoginById();
    void updateUserPasswordById();
    void updateUserNameById();
    void updateUserAddressById();
    void updateUserAgeById();

    void updateUserLogin();
    void updateUserPassword();
    void updateUserName();
    void updateUserAddress();
    void updateUserAge();

    void removeUserById();
    void removeUserByLogin();
    void removeUser();

    void findAboutUser();
    void findAllUsers();
    void findAllUsersByAge();
    void findUserByLogin();
    void findAllUsersByName();
    void findUserByAddress();
    void findUserById();

    void registration();
    void authorization();
    void logout();
}
