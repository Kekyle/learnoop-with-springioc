package com.company.repository.inmemory;

import com.company.entity.*;
import com.company.repository.OrderRepository;

import java.util.Arrays;

public class InMemoryOrderRepository implements OrderRepository {
    private static Order[] orders = new Order[10];

    @Override
    public boolean saveOrder(Order order) {
        for (int i = 0; i < orders.length; i++) {
            if (orders[i] == null) {
                orders[i] = order;
                return true;
            }
        }
        return false;
    }

    @Override
    public Book deleteBookFromOrder(Book book, int id) {
        for (int i = 0; i < orders.length; i++) {
            Order order = orders[i];
            if (order == null) break;
            if (order.getId() == id) {
                Book[] books = order.getBooks();
                for (int j = 0; j < books.length; j++) {
                    if (books[j].equals(book)) {
                        for (int k = j; k < books.length - 1; k++) {
                            books[k] = books[k + 1];
                        }
//                        break;
                    }
                }
                break;
            }
        }
        return null;
    }

    @Override
    public boolean addBookToOrder(Book book, int id) {
        for (int i = 0; i < orders.length; i++) {
            if (orders[i] == null) break;
            if (orders[i].getId() == id) {
                Book[] books = orders[i].getBooks();
                boolean isFull = true;
                for (int j = 0; j < books.length; j++) {
                    if (books[j] == null) {
                        isFull = false;
                        books[j] = book;
                        return true;
                    }
                }
//                if (isFull){
                    Book[] books1 = Arrays.copyOf(books, books.length + 1);
                    books1[books1.length - 1] = book;
                    orders[i].setBooks(books1);
//                }
            }
        }
        return false;
    }

    @Override
    public Book updateBookInOrder(Book book, Book newBook, int id) {
        for (int i = 0; i < orders.length; i++) {
            if (orders[i] == null) break;
            if (orders[i].getId() == id) {
                Book[] books = orders[i].getBooks();
                for (int j = 0; j < books.length; j++) {
                    if (books[j].equals(book)) {
                        Book oldBook = books[j];
                        books[j] = newBook;
                        return oldBook;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public Address updateDeliveryAddress(Address newAddress, int id) {
        for (int i = 0; i < orders.length; i++) {
            if (orders[i] == null) break;
            if (orders[i].getId() == id) {
                Address oldAddress = orders[i].getAddress();
                orders[i].setAddress(newAddress);
                return oldAddress;
            }
        }
        return null;
    }

    @Override
    public Store updatePickUpStore(Store newStore, int id) {
        for (int i = 0; i < orders.length; i++) {
            if (orders[i] == null) break;
            if (orders[i].getId() == id) {
                Store oldStore = orders[i].getStore();
                orders[i].setStore(newStore);
                return oldStore;
            }
        }
        return null;
    }

    @Override
    public boolean deleteOrderById(int id) {
        for (int i = 0; i < orders.length; i++) {
            if (orders[i] == null) break;
            if (orders[i].getId() == id) {
                for (int j = i; j < orders.length - 1; j++) {
                    orders[j] = orders[j + 1];
                }
                break;
            }
        }
        return false;
    }

    @Override
    public boolean deleteOrderByPickUpStore(Store store) {
        boolean flag = false;
        for (int i = 0; i < orders.length; i++) {
            if (orders[i] == null) break;
            if (orders[i].getStore() == null) continue;
            if (orders[i].getStore().equals(store)) {
                for (int j = i; j < orders.length - 1; j++) {
                    orders[j] = orders[j + 1];
                }
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public boolean deleteOrderByDeliveryAddress(Address address) {
        for (int i = 0; i < orders.length; i++) {
            if (orders[i] == null) break;
            if (orders[i].getAddress() == null) continue;
            if (orders[i].getAddress().equals(address)) {
                for (int j = i; j < orders.length - 1; j++) {
                    orders[j] = orders[j + 1];
                }
                break;
            }
        }
        return false;
    }

    @Override
    public Order[] getAllOrders() {
        int count = 0;
        for (int i = 0; i < orders.length; i++) {
            if (orders[i] != null) {
                count++;
            }
        }
        Order[] newOrders = new Order[count];
        for (int i = 0; i < newOrders.length; i++) {
            newOrders[i] = orders[i];
        }
        return newOrders;
    }

    @Override
    public Order getOrderById(int id) {
        for (int i = 0; i < orders.length; i++) {
            if (orders[i] == null) break;
            if (orders[i].getId() == id) {
                return orders[i];
            }
        }
        return null;
    }

    @Override
    public Order getOrderByDeliveryAddress(Address address) {
        for (int i = 0; i < orders.length; i++) {
            if (orders[i] == null) break;
            if (orders[i].getAddress() == null) continue;
            if (orders[i].getAddress().equals(address)) {
                return orders[i];
            }
        }
        return null;
    }

    @Override
    public Order getOrderByUser(User user) {
        for (int i = 0; i < orders.length; i++) {
            if (orders[i] == null) break;
            if (orders[i].getUser().equals(user)) {
                return orders[i];
            }
        }
        return null;
    }

    @Override
    public Order[] getAllOrdersByPickUpStore(Store store) {
        int count = 0;
        for (int i = 0; i < orders.length; i++) {
            if (orders[i] == null) break;
            if (orders[i].getStore() == null) continue;
            if (orders[i].getStore().equals(store)) {
                count++;
            }
        }
        Order[] newOrders = new Order[count];
        for (int i = 0, j = 0; i < orders.length; i++) {
            if (orders[i] == null) break;
            if (orders[i].getStore() == null) continue;
            if (orders[i].getStore().equals(store)) {
                newOrders[j++] = orders[i];
            }
        }
        return newOrders;
    }

    @Override
    public Order[] getAllByStatus(Order.Status status) {
        int count = 0;
        for (int i = 0; i < orders.length; i++) {
            if (orders[i] == null) break;
            if (orders[i].getStatus().equals(status)) {
                count++;
            }
        }
        Order[] newOrders = new Order[count];
        for (int i = 0, j = 0; i < orders.length; i++) {
            if (orders[i] == null) break;
            if (orders[i].getStatus().equals(status)) {
                newOrders[j++] = orders[i];
            }
        }
        return newOrders;
    }

    @Override
    public Order[] getAllOrdersByUser(User user) {
        int count = 0;
        for (int i = 0; i < orders.length; i++) {
            if (orders[i] == null) break;
            if (orders[i].getUser().equals(user)) {
                count++;
            }
        }
        Order[] newOrders = new Order[count];
        for (int i = 0, j = 0; i < orders.length; i++) {
            if (orders[i] == null) break;
            if (orders[i].getUser().equals(user)) {
                newOrders[j++] = orders[i];
            }
        }
        return newOrders;
    }

    @Override
    public boolean containsOrderById(int id) {
        for (int i = 0; i < orders.length; i++) {
            if (orders[i] == null) break;
            if (orders[i].getId() == id) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsOrderByStatus(Order.Status status) {
        for (int i = 0; i < orders.length; i++) {
            if (orders[i] == null) break;
            if (orders[i].getStatus().equals(status)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsOrderByDeliveryAddress(Address address) {
        for (int i = 0; i < orders.length; i++) {
            if (orders[i] == null) break;
            if (orders[i].getAddress() == null) continue;
            if (orders[i].getAddress().equals(address)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsOrderByPickUpStore(Store store) {
        for (int i = 0; i < orders.length; i++) {
            if (orders[i] == null) break;
            if (orders[i].getStore() == null) continue;
            if (orders[i].getStore().equals(store)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsOrderByUser(User user) {
        for (int i = 0; i < orders.length; i++) {
            if (orders[i] == null) break;
            if (orders[i].getUser().equals(user)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsOrderByBooks(Book[] books) {
        for (int i = 0; i < orders.length; i++) {
            if (orders[i] == null) break;
            for (int k = 0; k < orders.length; k++) {
                if (Arrays.equals(orders[k].getBooks(), books)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean containsOrder(Order order) {
        for (int i = 0; i < orders.length; i++) {
            if (orders[i] == null) break;
            if (orders[i].equals(order)) {
                return true;
            }
        }
        return false;
    }
}
