package com.company.entity;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class Address {
//    private static int incId = 1;
//    private int id = incId++;

    private int id;
    private String street;

    public Address() {
    }

    public Address(String street) {
        this.street = street;
    }

    public Address(int id, String street) {
        this.id = id;
        this.street = street;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(street, address.street);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street);
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", street='" + street + '\'' +
                '}';
    }
}
