package com.company.repository.db;

import com.company.entity.*;
import com.company.repository.OrderRepository;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
@Component
public class DBOrderRepository extends DBAbstractRepository implements OrderRepository {

    private final String SAVE_BOOK_DELIVERY_TRUE = "insert into orders values (default, ?, ?, ?, ?, ?)";
    private final String SAVE_BOOK_DELIVERY_FALSE = "insert into orders values (default, ?, ?, ?, ?, ?)";
    private final String SAVE_IN_ORDERS_BOOKS = "insert into orders_books values(?, ?)";
    private final String ADD_BOOK_TO_ORDER = "insert into orders_books values (?, ?)";
    private final String UPDATE_BOOK_IN_ORDER = "update orders_books set book_id=? where order_id=?";
    private final String UPDATE_DELIVERY_ADDRESS = "update orders set address=? where id=?";
    private final String UPDATE_PICKUP_STORE = "update orders set store_id=? where id=?";
    private final String DELETE_FROM_ORDERS_BOOKS_BY_ID = "delete from orders_books where order_id=?";
    private final String DELETE_ORDER_BY_ID = "delete from orders where id=?";
    private final String DELETE_ORDER_BY_DELIVERY_ADDRESS = "delete from orders where address=?";
    private final String DELETE_ORDER_BY_PICK_UP_STORE = "delete from orders where store_id=?";
    private final String DELETE_BOOK_FROM_ORDER = "delete from orders_books where book_id=?";
    private final String GET_ALL_FROM_ORDER = "select * from orders";
    private final String GET_ORDER_BY_ID = "select * from orders where id=?";
    private final String GET_ORDER_BY_DELIVERY_ADDRESS = "select * from orders where address=?";
    private final String GET_ORDER_BY_USER = "select * from orders where user_id=?";
    private final String GET_ORDER_BY_STATUS = "select * from orders where status=?";
    private final String GET_ORDER_BY_STORE = "select * from orders where store_id=?";
    private final String GET_ORDER_ID = "select o.id from orders o where store_id=?";
    private final String GET_BOOK = "select b.id, b.title, b.description, a.name, b.price from orders o join orders_books ob on o.id = ob.order_id join books b on ob.book_id = b.id join authors a on b.author_id = a.id where o.id=?";
    private final String GET_USER = "select u.id, u.login, u.password, u.age, u.name, a.street, r.role from orders o join users u on o.user_id = u.id join roles r on u.role_id = r.id join addresses a on u.address_id = a.id";
    private final String GET_STORE = "select s.id, s.name, a.id, a.street from orders o join stores s on o.store_id = s.id join addresses a on s.address_id = a.id";
    private final String GET_ADDRESS_BY_ORDER_ID = "select o.address from orders o where o.id=?";
    private final String GET_STORE_BY_ORDER_ID = "select s.id, s.name, a.street from orders o join stores s on o.store_id = s.id join addresses a on s.address_id = a.id where o.id=?";
    private final String GET_ORDER_ID_BY_ADDRESS = "select o.id from orders o where address=?";

    public DBOrderRepository() throws SQLException {
    }

    public static void main(String[] args) throws SQLException {
        DBOrderRepository dbOrderRepository = new DBOrderRepository();
//        DBBookRepository dbBookRepository = new DBBookRepository();
//        Book[] allBooks = dbBookRepository.getAllBooks();
//        Book[] ab = new Book[3];
//        ab[0] = new Book(99, "xxx", "xxx", new Author(99, "GGG"), 999);
//        ab[1] = new Book(100, "yyy", "yyy", new Author(99, "HHH"), 777);
//        ab[2] = new Book(150, "zzz", "zzz", new Author(99, "ZZZ"), 888);
//        dbOrderRepository.saveOrder(new Order(16,false,
//                new User(
//                        "bb",
//                        "bb",
//                        22,
//                        "bb",
//                        new Address(3, "truda 40"),
//                        Role.ADMIN),
//                allBooks,
//                new Address(1, "malina 30"),
//                new Store(8, "store1", new Address(1, "malina 30")),
//                Order.Status.ACTIVE));

//        System.out.println(dbOrderRepository.updateBookInOrder(new Book("aaaa", "aaaa", new Author(3, "Bulg"), 22),
//                new Book("bbb", "bbb", new Author(4, "Heming"), 33), 1));
//        System.out.println(dbOrderRepository.updateDeliveryAddress(new Address(3, "truda 40"), 1));
//        System.out.println(dbOrderRepository.updatePickUpStore(new Store(9, "store2", new Address(3, "truda 40")), 6));
//        System.out.println(dbOrderRepository.addBookToOrder(new Book(3,
//                        "aaaa",
//                        "aaaa",
//                        new Author(3, "Heming"),
//                        22),
//                99));
//        System.out.println(Arrays.toString(dbOrderRepository.getAllOrders()));
//        System.out.println(dbOrderRepository.getOrderById(1));
//        System.out.println(dbOrderRepository.getOrderByDeliveryAddress(new Address(3, "truda 40")));
//        System.out.println(dbOrderRepository.getOrderByUser(new User(7,
//                "new",
//                "new",
//                99,
//                "new",
//                new Address(1, "malina 30"),
//                Role.USER)));
//        System.out.println(Arrays.toString(dbOrderRepository.getAllOrdersByPickUpStore(new Store(8, "store1", new Address(3, "malina 30")))));
//        System.out.println(Arrays.toString(dbOrderRepository.getAllOrdersByUser(new User(7,
//                "new",
//                "new",
//                99,
//                "new",
//                new Address(1, "malina 30"),
//                Role.USER))));
//        System.out.println(Arrays.toString(dbOrderRepository.getAllByStatus(Order.Status.ACTIVE)));
////        System.out.println(dbOrderRepository.containsOrderByBooks(allBooks));
//        System.out.println(dbOrderRepository.containsOrder(new Order(false,
//                new User(
//                        "bb",
//                        "bb",
//                        22,
//                        "bb",
//                        new Address(3, "truda 40"),
//                        Role.ADMIN),
//                allBooks,
//                new Address(1, "malina 30"),
//                new Store(8, "store1", new Address(1, "malina 30")),
//                Order.Status.ACTIVE)));
//        System.out.println(dbOrderRepository.containsOrderByDeliveryAddress(new Address(3, "truda 40")));
//        System.out.println(dbOrderRepository.containsOrderById(100));
//        System.out.println(dbOrderRepository.containsOrderByPickUpStore(new Store(9, "store2", new Address(3, "truda 40"))));
//        System.out.println(dbOrderRepository.containsOrderByStatus(Order.Status.ACTIVE));
//        System.out.println(dbOrderRepository.containsOrderByUser(new User(
//                "bb",
//                "bb",
//                22,
//                "bb",
//                new Address(3, "truda 40"),
//                Role.ADMIN)));
//        System.out.println(dbOrderRepository.deleteOrderById(4));
//        System.out.println(dbOrderRepository.deleteOrderByDeliveryAddress(new Address(3, "truda 40")));
//        System.out.println(dbOrderRepository.deleteOrderByPickUpStore(new Store(9, "store2", new Address(3, "truda 40"))));
//        System.out.println(dbOrderRepository.deleteBookFromOrder(new Book(3, "aaaa", "aaaa", new Author(3, "Heming"), 22), 14));
//        System.out.println(dbOrderRepository.containsOrderByBooks(allBooks));
    }

    @Override
    public boolean saveOrder(Order order) {
        try {
            if (order.isDelivery()) {
                connection.setAutoCommit(false);

                PreparedStatement preparedStatement = connection.prepareStatement(SAVE_BOOK_DELIVERY_TRUE);
                preparedStatement.setBoolean(1, order.isDelivery());
                preparedStatement.setInt(2, order.getUser().getId());
                preparedStatement.setString(3, order.getAddress().getStreet());
                preparedStatement.setInt(4, 0);
                preparedStatement.setString(5, order.getStatus().name());
                preparedStatement.execute();
                saveOrdersBooks(order);

                connection.commit();
                connection.setAutoCommit(true);
                return true;
            } else {
                connection.setAutoCommit(false);
                PreparedStatement preparedStatement = connection.prepareStatement(SAVE_BOOK_DELIVERY_FALSE);
                preparedStatement.setBoolean(1, order.isDelivery());
                preparedStatement.setInt(2, order.getUser().getId());
                preparedStatement.setInt(3, 0);
                preparedStatement.setInt(4, order.getStore().getId());
                preparedStatement.setString(5, order.getStatus().name());
                preparedStatement.execute();
                saveOrdersBooks(order);

                connection.commit();
                connection.setAutoCommit(true);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void saveOrdersBooks(Order order) throws SQLException {
        Book[] books = order.getBooks();
        for (int i = 0; i < order.getBooks().length; i++) {
            int id = books[i].getId();
            PreparedStatement preparedStatement1 = connection.prepareStatement(GET_ALL_FROM_ORDER);
            ResultSet resultSet = preparedStatement1.executeQuery();
            resultSet.next();
            int orderId = resultSet.getInt(1);
            PreparedStatement preparedStatement = connection.prepareStatement(SAVE_IN_ORDERS_BOOKS);
            preparedStatement.setInt(1, orderId);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
        }
    }

    @Override
    public Book deleteBookFromOrder(Book book, int id) {
        try {
            PreparedStatement preparedStatement1 = connection.prepareStatement(GET_BOOK);
            preparedStatement1.setInt(1, id);
            ResultSet resultSet = preparedStatement1.executeQuery();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BOOK_FROM_ORDER);
            preparedStatement.setInt(1, book.getId());
            preparedStatement.execute();
            return getBook(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Book getBook(ResultSet resultSet) {
        try {
            int bookId = 0;
            String title = null;
            String description = null;
            String authorName = null;
            int price = 0;
            while (resultSet.next()) {
                bookId = resultSet.getInt(1);
                title = resultSet.getString(2);
                description = resultSet.getString(3);
                authorName = resultSet.getString(4);
                price = resultSet.getInt(5);
            }
            return new Book(bookId, title, description, new Author(authorName), price);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean addBookToOrder(Book book, int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_BOOK_TO_ORDER);
            preparedStatement.setInt(1, id);
            PreparedStatement preparedStatement1 = connection.prepareStatement(GET_ALL_FROM_ORDER);
            ResultSet resultSet = preparedStatement1.executeQuery();
            while (resultSet.next()) {
                int orderId = resultSet.getInt(1);
                if (orderId == id) {
                    preparedStatement.setInt(2, book.getId());
                    preparedStatement.execute();
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Book updateBookInOrder(Book book, Book newBook, int id) {
        try {
            PreparedStatement preparedStatement1 = connection.prepareStatement(GET_BOOK);
            preparedStatement1.setInt(1, id);
            ResultSet resultSet = preparedStatement1.executeQuery();
            Book orderBook = getBook(resultSet);

            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BOOK_IN_ORDER);
            preparedStatement.setInt(1, orderBook.getId());
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();

            return orderBook;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Address updateDeliveryAddress(Address newAddress, int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ADDRESS_BY_ORDER_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String street = resultSet.getString(1);
            Address address = new Address(street);

            PreparedStatement preparedStatement1 = connection.prepareStatement(UPDATE_DELIVERY_ADDRESS);
            preparedStatement1.setInt(2, id);
            preparedStatement1.setString(1, newAddress.getStreet());
            preparedStatement1.executeUpdate();

            return address;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Store updatePickUpStore(Store newStore, int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_STORE_BY_ORDER_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int storeId = resultSet.getInt(1);
            String storeName = resultSet.getString(2);
            String street = resultSet.getString(3);
            Store store = new Store(storeId, storeName, new Address(street));

            PreparedStatement preparedStatement1 = connection.prepareStatement(UPDATE_PICKUP_STORE);
            preparedStatement1.setInt(2, id);
            preparedStatement1.setInt(1, newStore.getId());
            preparedStatement1.executeUpdate();

            return store;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteOrderById(int id) {
        try {
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement1 = connection.prepareStatement(DELETE_FROM_ORDERS_BOOKS_BY_ID);
            preparedStatement1.setInt(1, id);
            preparedStatement1.execute();

            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ORDER_BY_ID);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();

            connection.commit();
            connection.setAutoCommit(true);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteOrderByPickUpStore(Store store) {
        try {
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement1 = connection.prepareStatement(GET_ORDER_ID);
            preparedStatement1.setInt(1, store.getId());
            preparedStatement1.execute();
            ResultSet resultSet = preparedStatement1.executeQuery();
            resultSet.next();
            int orderId = resultSet.getInt(1);

            PreparedStatement preparedStatement2 = connection.prepareStatement(DELETE_ORDER_BY_PICK_UP_STORE);
            preparedStatement2.setInt(1, store.getId());
            preparedStatement2.execute();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_FROM_ORDERS_BOOKS_BY_ID);
            preparedStatement.setInt(1, orderId);
            preparedStatement.execute();

            connection.commit();
            connection.setAutoCommit(true);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteOrderByDeliveryAddress(Address address) {
        try {
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement2 = connection.prepareStatement(GET_ORDER_ID_BY_ADDRESS);
            preparedStatement2.setString(1, address.getStreet());
            ResultSet resultSet = preparedStatement2.executeQuery();
            resultSet.next();
            int orderId = resultSet.getInt(1);

            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ORDER_BY_DELIVERY_ADDRESS);
            preparedStatement.setString(1, address.getStreet());
            preparedStatement.execute();

            PreparedStatement preparedStatement1 = connection.prepareStatement(DELETE_FROM_ORDERS_BOOKS_BY_ID);
            preparedStatement1.setInt(1, orderId);
            preparedStatement1.execute();

            connection.commit();
            connection.setAutoCommit(true);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private List<Order> findAll(ResultSet resultSet) {
        try {
            List<Order> list = new ArrayList<>();
            while (resultSet.next()) {
                int orderId = resultSet.getInt(1);
                boolean isDelivery = resultSet.getBoolean(2);
                String street = resultSet.getString(4);
                Address address = new Address(street);
                String orderStatus = resultSet.getString(6);
                Order.Status status = Order.Status.valueOf(orderStatus);
                User user = getUser();
                Book[] books = getBooks();
                Store store = getStore();
                list.add(new Order(orderId, isDelivery, user, books, address, store, status));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Book[] getBooks() throws SQLException {
        PreparedStatement preparedStatement1 = connection.prepareStatement(GET_ALL_FROM_ORDER);
        ResultSet resultSet1 = preparedStatement1.executeQuery();
        resultSet1.next();
        int id = resultSet1.getInt(1);
        PreparedStatement preparedStatement = connection.prepareStatement(GET_BOOK);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Book> list = new ArrayList<>();
        while (resultSet.next()) {
            int bookId = resultSet.getInt(1);
            String title = resultSet.getString(2);
            String description = resultSet.getString(3);
            String bookAuthor = resultSet.getString(4);
            int price = resultSet.getInt(5);
            Author author = new Author(bookAuthor);
            list.add(new Book(bookId, title, description, author, price));
        }
        return list.toArray(new Book[0]);
    }

    private Store getStore() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(GET_STORE);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        int storeId = resultSet.getInt(1);
        String storeName = resultSet.getString(2);
        int addressId = resultSet.getInt(3);
        String storeStreet = resultSet.getString(4);
        return new Store(storeId, storeName, new Address(addressId, storeStreet));
    }

    private User getUser() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(GET_USER);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        int userId = resultSet.getInt(1);
        String login = resultSet.getString(2);
        String password = resultSet.getString(3);
        int age = resultSet.getInt(4);
        String name = resultSet.getString(5);
        String userAddress = resultSet.getString(6);
        String userRole = resultSet.getString(7);
        Role role = Role.valueOf(userRole);
        return new User(userId, login, password, age, name, new Address(userAddress), role);
    }

    @Override
    public Order[] getAllOrders() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_FROM_ORDER);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Order> list = findAll(resultSet);
            return list.toArray(new Order[0]);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Order[0];
    }

    private Order findOne(ResultSet resultSet) {
        try {
            resultSet.next();
            int orderId = resultSet.getInt(1);
            boolean isDelivery = resultSet.getBoolean(2);
            String street = resultSet.getString(4);
            Address address = new Address(street);
            String orderStatus = resultSet.getString(6);
            Order.Status status = Order.Status.valueOf(orderStatus);
            User user = getUser();
            Book[] books = getBooks();
            Store store = getStore();
            return new Order(orderId, isDelivery, user, books, address, store, status);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Order getOrderById(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ORDER_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return findOne(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Order getOrderByDeliveryAddress(Address address) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ORDER_BY_DELIVERY_ADDRESS);
            preparedStatement.setString(1, address.getStreet());
            ResultSet resultSet = preparedStatement.executeQuery();
            return findOne(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Order getOrderByUser(User user) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ORDER_BY_USER);
            preparedStatement.setInt(1, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            return findOne(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Order[] getAllOrdersByPickUpStore(Store store) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_FROM_ORDER);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Order> list = new ArrayList<>();
            while (resultSet.next()) {
                int orderId = resultSet.getInt(1);
                boolean isDelivery = resultSet.getBoolean(2);
                String street = resultSet.getString(4);
                Address address = new Address(street);
                String orderStatus = resultSet.getString(6);
                Order.Status status = Order.Status.valueOf(orderStatus);
                User user = getUser();
                Book[] books = getBooks();
                Store userStore = getStore();
                if (userStore.equals(store)) {
                    list.add(new Order(orderId, isDelivery, user, books, address, userStore, status));
                }
            }
            return list.toArray(new Order[0]);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Order[0];
    }

    @Override
    public Order[] getAllByStatus(Order.Status status) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_FROM_ORDER);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Order> list = new ArrayList<>();
            while (resultSet.next()) {
                int orderId = resultSet.getInt(1);
                boolean isDelivery = resultSet.getBoolean(2);
                String street = resultSet.getString(4);
                Address address = new Address(street);
                String orderStatus = resultSet.getString(6);
                Order.Status oStatus = Order.Status.valueOf(orderStatus);
                User user = getUser();
                Book[] books = getBooks();
                Store store = getStore();
                if (oStatus.equals(status)) {
                    list.add(new Order(orderId, isDelivery, user, books, address, store, oStatus));
                }
            }
            return list.toArray(new Order[0]);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Order[0];
    }

    @Override
    public Order[] getAllOrdersByUser(User user) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_FROM_ORDER);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Order> list = new ArrayList<>();
            while (resultSet.next()) {
                int orderId = resultSet.getInt(1);
                boolean isDelivery = resultSet.getBoolean(2);
                String street = resultSet.getString(4);
                Address address = new Address(street);
                String orderStatus = resultSet.getString(6);
                Order.Status status = Order.Status.valueOf(orderStatus);
                User orderUser = getUser();
                Book[] books = getBooks();
                Store store = getStore();
                if (orderUser.equals(user)) {
                    list.add(new Order(orderId, isDelivery, orderUser, books, address, store, status));
                }
            }
            return list.toArray(new Order[0]);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Order[0];
    }

    @Override
    public boolean containsOrderById(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ORDER_BY_ID);
            preparedStatement.setInt(1, id);
            return preparedStatement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean containsOrderByStatus(Order.Status status) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ORDER_BY_STATUS);
            preparedStatement.setString(1, String.valueOf(status));
            return preparedStatement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean containsOrderByDeliveryAddress(Address address) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ORDER_BY_DELIVERY_ADDRESS);
            preparedStatement.setString(1, address.getStreet());
            return preparedStatement.executeQuery().next();
        } catch (SQLException e) {
           e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean containsOrderByPickUpStore(Store store) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ORDER_BY_STORE);
            preparedStatement.setInt(1, store.getId());
            return preparedStatement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean containsOrderByUser(User user) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ORDER_BY_USER);
            preparedStatement.setInt(1, user.getId());
            return preparedStatement.executeQuery().next();
        } catch (SQLException e) {
           e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean containsOrderByBooks(Book[] books) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_FROM_ORDER);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Order> list = findAll(resultSet);
            if (list == null) return false;
            for (Order order : list) {
                if (method(order.getBooks(), books)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean method(Book[] books, Book[] books2) {
        List<Book> books1 = Arrays.asList(books);
        List<Book> books3 = Arrays.asList(books2);
        Collections.sort(books1);
        Collections.sort(books3);
        return books1.equals(books3);
    }

    @Override
    public boolean containsOrder(Order order) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ORDER_BY_DELIVERY_ADDRESS);
            preparedStatement.setString(1, order.getAddress().getStreet());
            return preparedStatement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
