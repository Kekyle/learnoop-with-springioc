package com.company.entity;

import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.Arrays;
import java.util.Objects;

@Component
public class Order implements Comparable<Order>{
    private static int incId = 1;
    private int id = incId++;

    private boolean isDelivery;
    private User user;
    private Book[] books;

    private Address address;

    private Store store;

    private Status status = Status.ACTIVE;

    public Order() {
    }

    public Order(int id, boolean isDelivery, User user, Book[] books, Address address, Store store, Status status) {
        this.id = id;
        this.isDelivery = isDelivery;
        this.user = user;
        this.books = books;
        this.address = address;
        this.store = store;
        this.status = status;
    }

    public Order(boolean isDelivery, User user, Book[] books, Address address, Store store, Status status) {
        this.isDelivery = isDelivery;
        this.user = user;
        this.books = books;
        this.address = address;
        this.store = store;
        this.status = status;
    }

    public Order(User user, Book[] books, Address address) {
        this.user = user;
        this.books = books;
        this.address = address;
        this.isDelivery = true;
    }

    public Order(User user, Book[] books, Store store) {
        this.user = user;
        this.books = books;
        this.store = store;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isDelivery() {
        return isDelivery;
    }

    public void setDelivery(boolean delivery) {
        isDelivery = delivery;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book[] getBooks() {
        return books;
    }

    public void setBooks(Book[] books) {
        this.books = books;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(user, order.user) &&
                Arrays.equals(books, order.books) &&
                status == order.status;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(user, status);
        result = 31 * result + Arrays.hashCode(books);
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", isDelivery=" + isDelivery +
                ", user=" + user +
                ", books=" + Arrays.toString(books) +
                ", address=" + address +
                ", store=" + store +
                ", status=" + status +
                '}';
    }

    @Override
    public int compareTo(Order o) {
        return user.getName().compareTo(o.getUser().getName());
    }

    public enum Status {
        ACTIVE,
        CLOSE
    }
}
