package com.company.service;

import com.company.entity.*;
import com.company.repository.OrderRepository;
import com.company.repository.inmemory.InMemoryOrderRepository;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public boolean addOrder(Order order) {
        if (orderRepository.containsOrder(order)) {
            return false;
        }
        return orderRepository.saveOrder(order);
    }

    @Override
    public boolean removeBookFromOrder(Book book, int id) {
        if (orderRepository.containsOrderById(id)){
            orderRepository.deleteBookFromOrder(book, id);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateBookInOrder(Book book, Book newBook, int id) {
        if (orderRepository.containsOrderById(id)){
            orderRepository.updateBookInOrder(book, newBook, id);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateDeliveryAddress(Address newAddress, int id) {
        if (orderRepository.containsOrderById(id)) {
            orderRepository.updateDeliveryAddress(newAddress, id);
            return true;
        }
        return false;
    }

    @Override
    public boolean updatePickUpStore(Store newStore, int id) {
        if (orderRepository.containsOrderById(id)){
            orderRepository.updatePickUpStore(newStore, id);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeOrderById(int id) {
        if (orderRepository.containsOrderById(id)){
            orderRepository.deleteOrderById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeOrderByPickUpStore(Store store) {
        if (orderRepository.containsOrderByPickUpStore(store)) {
            orderRepository.deleteOrderByPickUpStore(store);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeOrderByDeliveryAddress(Address address) {
        if (orderRepository.containsOrderByDeliveryAddress(address)) {
            orderRepository.deleteOrderByDeliveryAddress(address);
            return true;
        }
        return false;
    }

    @Override
    public Order[] findAllOrders() {
        return orderRepository.getAllOrders();
    }

    @Override
    public Order findOrderById(int id) {
        if (orderRepository.containsOrderById(id)){
            return orderRepository.getOrderById(id);
        }
        return null;
    }

    @Override
    public Order findOrderByDeliveryAddress(Address address) {
        if (orderRepository.containsOrderByDeliveryAddress(address)) {
            return orderRepository.getOrderByDeliveryAddress(address);
        }
        return null;
    }

    @Override
    public Order findOrderByUser(User user) {
        if (orderRepository.containsOrderByUser(user)) {
            return orderRepository.getOrderByUser(user);
        }
        return null;
    }

    @Override
    public Order[] findAllOrdersByPickUpStore(Store store) {
        if (orderRepository.containsOrderByPickUpStore(store)) {
            return orderRepository.getAllOrdersByPickUpStore(store);
        }
        return null;
    }

    @Override
    public Order[] findAllOrdersByStatus(Order.Status status) {
        if (orderRepository.containsOrderByStatus(status)) {
            return orderRepository.getAllByStatus(status);
        }
        return null;
    }

    @Override
    public Order[] findAllOrdersByUser(User user) {
        if (orderRepository.containsOrderByUser(user)) {
            return orderRepository.getAllOrdersByUser(user);
        }
        return null;
    }

    @Override
    public boolean existOrderById(int id) {
        return orderRepository.containsOrderById(id);
    }

    @Override
    public boolean existOrderByBooks(Book[] books) {
        return orderRepository.containsOrderByBooks(books);
    }

    @Override
    public boolean existOrderByOrder(Order order) {
        return orderRepository.containsOrder(order);
    }

    @Override
    public boolean existOrderByStatus(Order.Status status) {
        return orderRepository.containsOrderByStatus(status);
    }

    @Override
    public boolean existOrderByPickUpStore(Store store) {
        return orderRepository.containsOrderByPickUpStore(store);
    }

    @Override
    public boolean existOrderByDeliveryAddress(Address address) {
        return orderRepository.containsOrderByDeliveryAddress(address);
    }

    @Override
    public boolean existOrderByUser(User user) {
        return orderRepository.containsOrderByUser(user);
    }
}
