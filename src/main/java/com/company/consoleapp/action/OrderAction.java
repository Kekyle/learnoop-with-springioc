package com.company.consoleapp.action;

import com.company.entity.Book;

public interface OrderAction {
    void addOrder(Book[] books);

    void removeBookFromOrder();
    void removeBookFromOrderByUser();
    void updateBookInOrder();

    void updateDeliveryAddress();
    void updatePickUpStore();
    void updateOrderStatus();

    void removeOrderById();
    void removeOrderByPickUpStore();
    void removeOrderByDeliveryAddress();

    void findAllOrders();
    void findOrderById();
    void findOrderByDeliveryAddress();
    void findOrderByUser();
    void findAllByUser();
    void findAllOrdersByPickUpStore();
    void findAllOrdersByStatus();
    void findAllOrdersByUser();
}
