package com.company.entity;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class User {
    private static int incId = 1;
    private int id = incId++;

    private String login;
    private String password;
    private int age;
    private String name;
    private Address address;

    private Role role;

    public User() {
    }

    public User(int id, String login, String password, int age, String name, Address address, Role role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.age = age;
        this.name = name;
        this.address = address;
        this.role = role;
    }

    public User(String login, String password, int age, String name, Address address, Role role) {
        this.login = login;
        this.password = password;
        this.age = age;
        this.name = name;
        this.address = address;
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(login, user.login) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", role=" + role +
                '}';
    }
}
