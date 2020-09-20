package com.company.repository;

import com.company.entity.*;

public interface OrderRepository {
    boolean saveOrder(Order order);

    Book deleteBookFromOrder(Book book, int id);
    boolean addBookToOrder(Book book, int id);
    Book updateBookInOrder(Book book, Book newBook, int id);

    Address updateDeliveryAddress(Address newAddress, int id);
    Store updatePickUpStore(Store newStore, int id);

    boolean deleteOrderById(int id);
    boolean deleteOrderByPickUpStore(Store store);
    boolean deleteOrderByDeliveryAddress(Address address);

    Order[] getAllOrders();
    Order getOrderById(int id);
    Order getOrderByDeliveryAddress(Address address);
    Order getOrderByUser(User user);
    Order[] getAllOrdersByPickUpStore(Store store);
    Order[] getAllByStatus(Order.Status status);
    Order[] getAllOrdersByUser(User user);

    boolean containsOrderById(int id);
    boolean containsOrderByStatus(Order.Status status);
    boolean containsOrderByDeliveryAddress(Address address);
    boolean containsOrderByPickUpStore(Store store);
    boolean containsOrderByUser(User user);
    boolean containsOrderByBooks(Book[] books);
    boolean containsOrder(Order order);
}
