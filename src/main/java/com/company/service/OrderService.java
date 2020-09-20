package com.company.service;

import com.company.entity.*;

public interface OrderService {
    boolean addOrder(Order order);
    boolean removeBookFromOrder(Book book, int id);
    boolean updateBookInOrder(Book book, Book newBook, int id);

    boolean updateDeliveryAddress(Address newAddress, int id);
    boolean updatePickUpStore(Store newStore, int id);

    boolean removeOrderById(int id);
    boolean removeOrderByPickUpStore(Store store);
    boolean removeOrderByDeliveryAddress(Address address);

    Order[] findAllOrders();
    Order findOrderById(int id);
    Order findOrderByDeliveryAddress(Address address);
    Order findOrderByUser(User user);
    Order[] findAllOrdersByPickUpStore(Store store);
    Order[] findAllOrdersByStatus(Order.Status status);
    Order[] findAllOrdersByUser(User user);

    boolean existOrderById(int id);
    boolean existOrderByBooks(Book[] books);
    boolean existOrderByOrder(Order order);
    boolean existOrderByStatus(Order.Status status);
    boolean existOrderByPickUpStore(Store store);
    boolean existOrderByDeliveryAddress(Address address);
    boolean existOrderByUser(User user);
}
