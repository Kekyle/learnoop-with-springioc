package com.company.repository.file;

import com.company.entity.*;
import com.company.repository.OrderRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileOrderRepository extends FileAbstractRepository implements OrderRepository {
    private static final int IS_DELIVERY = 1;
    private static final int USER_ID = 2;
    private static final int BOOK_ID = 3;
    private static final int ADDRESS = 4;
    private static final int STORE_ID = 5;
    private static final int STATUS = 6;
    private File userFile;
    private File role;
    private File addressFile;
    private File storeFile;
    private File authorFile;
    private File bookFile;
    private File status;

    public FileOrderRepository(File file,
                               File userFile,
                               File role,
                               File addressFile,
                               File storeFile,
                               File authorFile,
                               File bookFile,
                               File status) {
        super(file);
        this.userFile = userFile;
        this.role = role;
        this.addressFile = addressFile;
        this.storeFile = storeFile;
        this.authorFile = authorFile;
        this.bookFile = bookFile;
        this.status = status;
    }

    public static void main(String[] args) {
        FileOrderRepository fileOrderRepository = new FileOrderRepository(new File("orders.txt"),
                new File("users.txt"),
                new File("role.txt"),
                new File("addresses.txt"),
                new File("stores.txt"),
                new File("authors.txt"),
                new File("books.txt"),
                new File("status.txt"));
        FileBookRepository fileBookRepository = new FileBookRepository(new File("books.txt"));
        Book[] allBooks = fileBookRepository.getAllBooks();
//        fileOrderRepository.saveOrder(new Order(true, new User("test",
//                "test",
//                22,
//                "Test",
//                new Address("malina40"),
//                Role.USER),
//                allBooks,
//                new Address(null),
//                new Store("storeeee", new Address("aaadddrrr")),
//                Order.Status.ACTIVE));
//        System.out.println(fileOrderRepository.getOrderById(3));
//        System.out.println(Arrays.toString(fileOrderRepository.getAllByStatus(Order.Status.ACTIVE)));
////        System.out.println(fileOrderRepository.updateDeliveryAddress(new Address("new"), 3));
//        System.out.println(Arrays.toString(fileOrderRepository.getAllOrders()));
//      System.out.println(Arrays.toString(fileOrderRepository.getAllOrdersByPickUpStore(new Store("storeeee", new Address("aaadddrrr")))));
////        System.out.println(fileOrderRepository.getOrderByDeliveryAddress(new Address("adddddrrr")));
////        System.out.println(Arrays.toString(fileOrderRepository.getAllOrdersByUser(new User("test",
////                "test",
////                22,
////                "Test",
////                new Address("malina40"),
////                Role.USER))));
////        System.out.println(fileOrderRepository.containsOrderByBooks(allBooks));
//        System.out.println(fileOrderRepository.containsOrderById(1));
//        System.out.println(fileOrderRepository.containsOrderByDeliveryAddress(new Address("adddddrrr")));
////        System.out.println(fileOrderRepository.updatePickUpStore(new Store("NEW", new Address("NEWWW")), 3));
////        System.out.println(fileOrderRepository.updateDeliveryAddress(new Address("NEWadr"), 3));
//        System.out.println(fileOrderRepository.deleteOrderById(1));
////        System.out.println(fileOrderRepository.deleteOrderByDeliveryAddress(new Address("adddddrrr")));
////        System.out.println(fileOrderRepository.deleteOrderByPickUpStore(new Store("storeeee", new Address("aaadddrrr"))));

    }

    @Override
    public boolean saveOrder(Order order) {
        try {
            FileWriter fileWriter = new FileWriter(file, true);
            fileWriter.append("\n");
            int newId = getLastId() + 1;
            order.setId(newId);
            boolean isDelivery;
            isDelivery = isDelivery(order);
//            int[] arrId = getInts(order);
            fileWriter.write(order.getId() + SPLITTER
                    + isDelivery + SPLITTER
                    + order.getUser().getId() + SPLITTER
                    + toString(order.getBooks()) + SPLITTER
                    + order.getAddress().getStreet() + SPLITTER
                    + order.getStore().getId() + SPLITTER
                    + order.getStatus());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private int[] getInts(Order order) {
        Book[] books = order.getBooks();
        int[] arrId = new int[books.length];
        for (int i = 0; i < books.length; i++) {
            if (books[i] == null) continue;
            int id = books[i].getId();
            arrId[i] = id;
        }
        return arrId;
    }

    private String toString(Book[] books) {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < books.length; i++) {
            if (books[i] == null) continue;
            b.append(books[i].getId());
            if (i == books.length - 1) {
                return b.toString();
            }
            b.append(",");
        }
        return null;
    }

    private boolean isDelivery(Order order) {
        boolean isDelivery;
        if (order.getAddress() != null) {
            isDelivery = true;
        } else {
            isDelivery = false;
        }
        return isDelivery;
    }

    private void writeAll(List<Order> list, List<String> comments) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            int count = 0;
            int nullCount = 0;
            for (Order order : list) {
                if (order == null) {
                    bufferedWriter.write(comments.get(nullCount++));
                } else {
                    boolean isDelivery;
                    isDelivery = isDelivery(order);
//                    int[] arrId = getInts(order);
                    bufferedWriter.write(order.getId() + SPLITTER
                            + isDelivery + SPLITTER
                            + order.getUser() + SPLITTER
                            + toString(order.getBooks()) + SPLITTER
                            + order.getAddress().getStreet() + SPLITTER
                            + order.getStore() + SPLITTER
                            + order.getStatus());
                }
                if (count != list.size() - 1) {
                    bufferedWriter.write("\n");
                    count++;
                }
            }
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addFromFileToOrderList(List<Order> list, List<String> comments) {
        try {
            FileReader fileReader = new FileReader(file);
            FileReader userReader = new FileReader(userFile);
            FileReader roleReader = new FileReader(role);
            FileReader bookReader = new FileReader(bookFile);
            FileReader statusReader = new FileReader(status);
            FileReader storeReader = new FileReader(storeFile);
            FileReader addressReader = new FileReader(addressFile);
            FileReader authorReader = new FileReader(authorFile);
            BufferedReader roleBuffer = new BufferedReader(roleReader);
            BufferedReader userBuffer = new BufferedReader(userReader);
            BufferedReader addressBuffer = new BufferedReader(addressReader);
            BufferedReader storeBuffer = new BufferedReader(storeReader);
            BufferedReader authorBuffer = new BufferedReader(authorReader);
            BufferedReader bookBuffer = new BufferedReader(bookReader);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            BufferedReader statusBuffer = new BufferedReader(statusReader);
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                String[] split = s.split(SPLITTER);
                if (split[ID].equals(COMMENT_TAG)) {
                    list.add(null);
                    comments.add(s);
                    continue;
                }
                Order.Status status = getStatus(statusBuffer, split[STATUS]);
                User user = getUser(roleBuffer, userBuffer, split[USER_ID]);
                List<Book> books = getBooks(authorBuffer, bookBuffer, split[BOOK_ID]);
                Store store = getStore(storeBuffer, addressBuffer, split[STORE_ID]);
                Order order = new Order(Integer.parseInt(split[ID]),
                        Boolean.parseBoolean(split[IS_DELIVERY]),
                        user,
                        books.toArray(new Book[0]),
                        new Address(split[ADDRESS]),
                        store,
                        status);
                list.add(order);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Order.Status getStatus(BufferedReader statusBuffer, String stringStatus) throws IOException {
        String statusLine;
        Order.Status status = Order.Status.ACTIVE;
        while ((statusLine = statusBuffer.readLine()) != null) {
            String[] s1 = statusLine.split(SPLITTER);
            String s2 = s1[0];
            String s3 = stringStatus;
            if (s2.equals(s3)) {
                status = Order.Status.valueOf(s1[1]);
            }
        }
        return status;
    }

    private Store getStore(BufferedReader storeBuffer, BufferedReader addressBuffer, String s) {
        List<Order> list = new ArrayList<>();
        List<String> comments = new ArrayList<>();
        try {
            String storeLine;
            Store store = null;
            while ((storeLine = storeBuffer.readLine()) != null) {
                String[] storeSplitter = storeLine.split(SPLITTER);
                if (storeSplitter[ID].equals(COMMENT_TAG)) {
                    list.add(null);
                    comments.add(storeLine);
                    continue;
                }
                String addressLine;
                Address address = null;
                while ((addressLine = addressBuffer.readLine()) != null) {
                    String[] split = addressLine.split(SPLITTER);
                    if (split[ID].equals(COMMENT_TAG)) {
                        list.add(null);
                        comments.add(addressLine);
                        continue;
                    }
                    address = new Address(Integer.parseInt(split[ID]), split[1]);
                }
                String[] split = storeLine.split(SPLITTER);
                store = new Store(Integer.parseInt(split[ID]), split[1], address);
                return store;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Book> getBooks(BufferedReader authorBuffer, BufferedReader bookBuffer, String booksIndex) throws IOException {
        List<Order> list = new ArrayList<>();
        List<String> comments = new ArrayList<>();
        String s1 = booksIndex;
        String[] split1 = s1.split(",");
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < split1.length; i++) {
            int bookId = Integer.parseInt(split1[i]);
            String bookLine;
            Book book = null;
            while ((bookLine = bookBuffer.readLine()) != null) {
                String[] bookSplitter = bookLine.split(SPLITTER);
                if (bookSplitter[ID].equals(COMMENT_TAG)) {
                    list.add(null);
                    comments.add(bookLine);
                    continue;
                }
                String s3 = bookSplitter[ID];
                if (Integer.parseInt(s3) == bookId) {
                    String authorLine;
                    Author author = null;
                    while ((authorLine = authorBuffer.readLine()) != null) {
                        String[] split2 = authorLine.split(SPLITTER);
                        author = new Author(Integer.parseInt(split2[ID]), split2[1]);
                    }
                    book = new Book(Integer.parseInt(bookSplitter[ID]), bookSplitter[1], bookSplitter[2], author, Integer.parseInt(bookSplitter[4]));
                }
            }
            books.add(book);
        }
        return books;
    }

    private User getUser(BufferedReader roleBuffer, BufferedReader userBuffer, String userIndex) throws IOException {
        List<Order> list = new ArrayList<>();
        List<String> comments = new ArrayList<>();
        String userLine;
        User user = null;
        while ((userLine = userBuffer.readLine()) != null) {
            String[] userSplitter = userLine.split(SPLITTER);
            if (userSplitter[ID].equals(COMMENT_TAG)) {
                list.add(null);
                comments.add(userLine);
                continue;
            }
            String roleLine;
            Role role = Role.USER;
            while ((roleLine = roleBuffer.readLine()) != null) {
                String[] s1 = roleLine.split(SPLITTER);
                String s2 = s1[0];
                String s3 = userIndex;
                if (s2.equals(s3)) {
                    role = Role.valueOf(s1[1]);
                }
            }
            user = new User(Integer.parseInt(userSplitter[ID]),
                    userSplitter[1],
                    userSplitter[2],
                    Integer.parseInt(userSplitter[3]),
                    userSplitter[4],
                    new Address(userSplitter[5]),
                    role);
        }
        return user;
    }


    @Override
    public Book deleteBookFromOrder(Book book, int id) {
        List<Order> list = new ArrayList<>();
        List<String> listComment = new ArrayList<>();
        addFromFileToOrderList(list, listComment);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == null) continue;
            Book book1 = null;
            if (list.get(i).getId() == id) {
                Book[] books = list.get(i).getBooks();
                for (int j = 0; j < books.length; j++) {
                    if (books[j].equals(book)) {
                        book1 = books[j];
                        for (int k = j; k < books.length - 1; k++) {
                            books[k] = books[k + 1];
                        }
                    }
                }
                writeAll(list, listComment);
                return book1;
            }
        }
        return null;
    }

    @Override
    public boolean addBookToOrder(Book book, int id) {
        List<Order> list = new ArrayList<>();
        List<String> listComment = new ArrayList<>();
        addFromFileToOrderList(list, listComment);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == null) continue;
            if (list.get(i).getId() == id) {
                Book[] books = list.get(i).getBooks();
                for (int j = 0; j < books.length; j++) {
                    if (books[j] == null) {
                        books[j] = book;
                    }
                }
                Book[] books1 = Arrays.copyOf(books, books.length + 1);
                books1[books1.length - 1] = book;
                list.get(i).setBooks(books1);
                writeAll(list, listComment);
                return true;
            }
        }
        return false;
    }

    @Override
    public Book updateBookInOrder(Book book, Book newBook, int id) {
        List<Order> list = new ArrayList<>();
        List<String> listComment = new ArrayList<>();
        addFromFileToOrderList(list, listComment);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == null) continue;
            Book oldBook = null;
            if (list.get(i).getId() == id) {
                Book[] books = list.get(i).getBooks();
                for (int j = 0; j < books.length; j++) {
                    if (books[j].equals(book)) {
                        oldBook = books[j];
                        books[j] = newBook;
                    }
                }
                writeAll(list, listComment);
                return oldBook;
            }
        }
        return null;
    }

    @Override
    public Address updateDeliveryAddress(Address newAddress, int id) {
        List<Order> list = new ArrayList<>();
        List<String> listComment = new ArrayList<>();
        addFromFileToOrderList(list, listComment);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == null) continue;
            if (list.get(i).getId() == id) {
                Address oldAddress = list.get(i).getAddress();
                list.get(i).setAddress(newAddress);
                writeAll(list, listComment);
                return oldAddress;
            }
        }
        return null;
    }

    @Override
    public Store updatePickUpStore(Store newStore, int id) {
        List<Order> list = new ArrayList<>();
        List<String> listComment = new ArrayList<>();
        addFromFileToOrderList(list, listComment);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == null) continue;
            if (list.get(i).getId() == id) {
                Store oldStore = list.get(i).getStore();
                list.get(i).setStore(newStore);
                writeAll(list, listComment);
                return oldStore;
            }
        }
        return null;
    }

    @Override
    public boolean deleteOrderById(int id) {
        List<Order> list = new ArrayList<>();
        List<String> listComment = new ArrayList<>();
        addFromFileToOrderList(list, listComment);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == null) continue;
            if (list.get(i).getId() == id) {
                list.remove(i);
                writeAll(list, listComment);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteOrderByPickUpStore(Store store) {
        List<Order> list = new ArrayList<>();
        List<String> listComment = new ArrayList<>();
        addFromFileToOrderList(list, listComment);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == null) continue;
            if (list.get(i).getStore() == null) return false;
            if (list.get(i).getStore().equals(store)) {
                list.remove(i);
                writeAll(list, listComment);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteOrderByDeliveryAddress(Address address) {
        List<Order> list = new ArrayList<>();
        List<String> listComment = new ArrayList<>();
        addFromFileToOrderList(list, listComment);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == null) continue;
            if (list.get(i).getAddress().equals(address)) {
                list.remove(i);
                writeAll(list, listComment);
                return true;
            }
        }
        return false;
    }

    @Override
    public Order[] getAllOrders() {
        List<Order> list = new ArrayList<>();
        List<String> listComment = new ArrayList<>();
        addFromFileToOrderList(list, listComment);
        return list.toArray(new Order[0]);
    }

    @Override
    public Order getOrderById(int id) {
        try {
            FileReader fileReader = new FileReader(file);
            FileReader userReader = new FileReader(userFile);
            FileReader addressReader = new FileReader(addressFile);
            FileReader storeReader = new FileReader(storeFile);
            FileReader authorReader = new FileReader(authorFile);
            FileReader bookReader = new FileReader(bookFile);
            FileReader roleReader = new FileReader(role);
            FileReader statusReader = new FileReader(status);
            BufferedReader roleBuffer = new BufferedReader(roleReader);
            BufferedReader userBuffer = new BufferedReader(userReader);
            BufferedReader addressBuffer = new BufferedReader(addressReader);
            BufferedReader storeBuffer = new BufferedReader(storeReader);
            BufferedReader authorBuffer = new BufferedReader(authorReader);
            BufferedReader bookBuffer = new BufferedReader(bookReader);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            BufferedReader statusBuffer = new BufferedReader(statusReader);
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                String[] split = s.split(SPLITTER);
                if (split[ID].equals(COMMENT_TAG)) continue;
                if (Integer.parseInt(split[ID]) == id) {
                    User user = getUser(roleBuffer, userBuffer, split[USER_ID]);
                    List<Book> books = getBooks(authorBuffer, bookBuffer, split[3]);
                    Store store = getStore(storeBuffer, addressBuffer, split[5]);
                    Order.Status status = getStatus(statusBuffer, split[STATUS]);
                    return new Order(Integer.parseInt(split[ID]),
                            Boolean.parseBoolean(split[IS_DELIVERY]),
                            user,
                            books.toArray(new Book[0]),
                            new Address(split[ADDRESS]),
                            store,
                            status);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Order getOrderByDeliveryAddress(Address address) {
        try {
            FileReader fileReader = new FileReader(file);
            FileReader userReader = new FileReader(userFile);
            FileReader addressReader = new FileReader(addressFile);
            FileReader storeReader = new FileReader(storeFile);
            FileReader authorReader = new FileReader(authorFile);
            FileReader bookReader = new FileReader(bookFile);
            FileReader roleReader = new FileReader(role);
            FileReader statusReader = new FileReader(status);
            BufferedReader roleBuffer = new BufferedReader(roleReader);
            BufferedReader userBuffer = new BufferedReader(userReader);
            BufferedReader addressBuffer = new BufferedReader(addressReader);
            BufferedReader storeBuffer = new BufferedReader(storeReader);
            BufferedReader authorBuffer = new BufferedReader(authorReader);
            BufferedReader bookBuffer = new BufferedReader(bookReader);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            BufferedReader statusBuffer = new BufferedReader(statusReader);
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                String[] split = s.split(SPLITTER);
                if (split[ID].equals(COMMENT_TAG)) continue;
                if (split[ADDRESS].equals(address.getStreet())) {
                    User user = getUser(roleBuffer, userBuffer, split[USER_ID]);
                    List<Book> books = getBooks(authorBuffer, bookBuffer, split[3]);
                    Store store = getStore(storeBuffer, addressBuffer, split[5]);
                    Order.Status status = getStatus(statusBuffer, split[STATUS]);
                    return new Order(Integer.parseInt(split[ID]),
                            Boolean.parseBoolean(split[IS_DELIVERY]),
                            user,
                            books.toArray(new Book[0]),
                            new Address(split[ADDRESS]),
                            store,
                            status);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Order getOrderByUser(User user) {
        try {
            FileReader fileReader = new FileReader(file);
            FileReader userReader = new FileReader(userFile);
            FileReader addressReader = new FileReader(addressFile);
            FileReader storeReader = new FileReader(storeFile);
            FileReader authorReader = new FileReader(authorFile);
            FileReader bookReader = new FileReader(bookFile);
            FileReader roleReader = new FileReader(role);
            FileReader statusReader = new FileReader(status);
            BufferedReader roleBuffer = new BufferedReader(roleReader);
            BufferedReader userBuffer = new BufferedReader(userReader);
            BufferedReader addressBuffer = new BufferedReader(addressReader);
            BufferedReader storeBuffer = new BufferedReader(storeReader);
            BufferedReader authorBuffer = new BufferedReader(authorReader);
            BufferedReader bookBuffer = new BufferedReader(bookReader);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            BufferedReader statusBuffer = new BufferedReader(statusReader);
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                String[] split = s.split(SPLITTER);
                if (split[ID].equals(COMMENT_TAG)) continue;
                User user1 = getUser(roleBuffer, userBuffer, split[USER_ID]);
                if (user1.equals(user)) {
                    List<Book> books = getBooks(authorBuffer, bookBuffer, split[3]);
                    Store store = getStore(storeBuffer, addressBuffer, split[5]);
                    Order.Status status = getStatus(statusBuffer, split[STATUS]);
                    return new Order(Integer.parseInt(split[ID]),
                            Boolean.parseBoolean(split[IS_DELIVERY]),
                            user1,
                            books.toArray(new Book[0]),
                            new Address(split[ADDRESS]),
                            store,
                            status);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Order[] getAllOrdersByPickUpStore(Store store) {
        List<Order> list = new ArrayList<>();
        List<String> comments = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(file);
            FileReader userReader = new FileReader(userFile);
            FileReader addressReader = new FileReader(addressFile);
            FileReader storeReader = new FileReader(storeFile);
            FileReader authorReader = new FileReader(authorFile);
            FileReader bookReader = new FileReader(bookFile);
            FileReader roleReader = new FileReader(role);
            FileReader statusReader = new FileReader(status);
            BufferedReader roleBuffer = new BufferedReader(roleReader);
            BufferedReader userBuffer = new BufferedReader(userReader);
            BufferedReader addressBuffer = new BufferedReader(addressReader);
            BufferedReader storeBuffer = new BufferedReader(storeReader);
            BufferedReader authorBuffer = new BufferedReader(authorReader);
            BufferedReader bookBuffer = new BufferedReader(bookReader);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            BufferedReader statusBuffer = new BufferedReader(statusReader);
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                String[] split = s.split(SPLITTER);
                if (split[ID].equals(COMMENT_TAG)) {
                    list.add(null);
                    comments.add(s);
                    continue;
                }
                Store store1 = getStore(storeBuffer, addressBuffer, split[STORE_ID]);
                if (store1.equals(store)) {
                    User user = getUser(roleBuffer, userBuffer, split[USER_ID]);
                    List<Book> books = getBooks(authorBuffer, bookBuffer, split[3]);
                    Order.Status status = getStatus(statusBuffer, split[STATUS]);
                    Order order = new Order(Integer.parseInt(split[ID]),
                            Boolean.parseBoolean(split[IS_DELIVERY]),
                            user,
                            books.toArray(new Book[0]),
                            new Address(split[ADDRESS]),
                            store1,
                            status);
                    list.add(order);
                } else return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list.toArray(new Order[0]);
    }

    @Override
    public Order[] getAllByStatus(Order.Status status) {
        List<Order> list = new ArrayList<>();
        List<String> comments = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(file);
            FileReader userReader = new FileReader(userFile);
            FileReader addressReader = new FileReader(addressFile);
            FileReader storeReader = new FileReader(storeFile);
            FileReader authorReader = new FileReader(authorFile);
            FileReader bookReader = new FileReader(bookFile);
            FileReader roleReader = new FileReader(role);
            FileReader statusReader = new FileReader(this.status);
            BufferedReader roleBuffer = new BufferedReader(roleReader);
            BufferedReader userBuffer = new BufferedReader(userReader);
            BufferedReader addressBuffer = new BufferedReader(addressReader);
            BufferedReader storeBuffer = new BufferedReader(storeReader);
            BufferedReader authorBuffer = new BufferedReader(authorReader);
            BufferedReader bookBuffer = new BufferedReader(bookReader);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            BufferedReader statusBuffer = new BufferedReader(statusReader);
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                String[] split = s.split(SPLITTER);
                if (split[ID].equals(COMMENT_TAG)) {
                    list.add(null);
                    comments.add(s);
                    continue;
                }
                Order.Status status1 = getStatus(statusBuffer, split[STATUS]);
                if (status1.equals(status)) {
                    User user = getUser(roleBuffer, userBuffer, split[USER_ID]);
                    List<Book> books = getBooks(authorBuffer, bookBuffer, split[3]);
                    Store store1 = getStore(storeBuffer, addressBuffer, split[5]);
                    Order order = new Order(Integer.parseInt(split[ID]),
                            Boolean.parseBoolean(split[IS_DELIVERY]),
                            user,
                            books.toArray(new Book[0]),
                            new Address(split[ADDRESS]),
                            store1,
                            status1);
                    list.add(order);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list.toArray(new Order[0]);
    }

    @Override
    public Order[] getAllOrdersByUser(User user) {
        List<Order> list = new ArrayList<>();
        List<String> comments = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(file);
            FileReader userReader = new FileReader(userFile);
            FileReader addressReader = new FileReader(addressFile);
            FileReader storeReader = new FileReader(storeFile);
            FileReader authorReader = new FileReader(authorFile);
            FileReader bookReader = new FileReader(bookFile);
            FileReader roleReader = new FileReader(role);
            FileReader statusReader = new FileReader(status);
            BufferedReader roleBuffer = new BufferedReader(roleReader);
            BufferedReader userBuffer = new BufferedReader(userReader);
            BufferedReader addressBuffer = new BufferedReader(addressReader);
            BufferedReader storeBuffer = new BufferedReader(storeReader);
            BufferedReader authorBuffer = new BufferedReader(authorReader);
            BufferedReader bookBuffer = new BufferedReader(bookReader);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            BufferedReader statusBuffer = new BufferedReader(statusReader);
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                String[] split = s.split(SPLITTER);
                if (split[ID].equals(COMMENT_TAG)) {
                    list.add(null);
                    comments.add(s);
                    continue;
                }
                User curUser = getUser(roleBuffer, userBuffer, split[USER_ID]);
                if (curUser.equals(user)) {
                    Order.Status status = getStatus(statusBuffer, split[STATUS]);
                    List<Book> books = getBooks(authorBuffer, bookBuffer, split[3]);
                    Store store = getStore(storeBuffer, addressBuffer, split[5]);
                    Order order = new Order(Integer.parseInt(split[ID]),
                            Boolean.parseBoolean(split[IS_DELIVERY]),
                            curUser,
                            books.toArray(new Book[0]),
                            new Address(split[ADDRESS]),
                            store,
                            status);
                    list.add(order);
                } else return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list.toArray(new Order[0]);
    }

    @Override
    public boolean containsOrderById(int id) {
        List<Order> list = new ArrayList<>();
        List<String> listComment = new ArrayList<>();
        addFromFileToOrderList(list, listComment);
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                String[] split = s.split(SPLITTER);
                if (split[ID].equals(COMMENT_TAG)) continue;
                if (Integer.parseInt(split[ID]) == id) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean containsOrderByStatus(Order.Status status) {
        List<Order> list = new ArrayList<>();
        List<String> listComment = new ArrayList<>();
        addFromFileToOrderList(list, listComment);
        try {
            FileReader fileReader = new FileReader(file);
            FileReader statusReader = new FileReader(this.status);
            BufferedReader statusBuffer = new BufferedReader(statusReader);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                String[] split = s.split(SPLITTER);
                if (split[ID].equals(COMMENT_TAG)) continue;
                Order.Status status1 = getStatus(statusBuffer, split[STATUS]);
                if (status1.equals(status)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean containsOrderByDeliveryAddress(Address address) {
        List<Order> list = new ArrayList<>();
        List<String> listComment = new ArrayList<>();
        addFromFileToOrderList(list, listComment);
        try {
            FileReader fileReader = new FileReader(file);
            FileReader statusReader = new FileReader(this.status);
            BufferedReader statusBuffer = new BufferedReader(statusReader);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                String[] split = s.split(SPLITTER);
                if (split[ID].equals(COMMENT_TAG)) continue;
                if (split[ADDRESS].equals(address.getStreet())) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean containsOrderByPickUpStore(Store store) {
        List<Order> list = new ArrayList<>();
        List<String> listComment = new ArrayList<>();
        addFromFileToOrderList(list, listComment);
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                String[] split = s.split(SPLITTER);
                if (split[ID].equals(COMMENT_TAG)) continue;
                if (split[STORE_ID].equals(store.getName())) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean containsOrderByUser(User user) {
        List<Order> list = new ArrayList<>();
        List<String> comments = new ArrayList<>();
        addFromFileToOrderList(list, comments);
        try {
            FileReader fileReader = new FileReader(file);
            FileReader userReader = new FileReader(userFile);
            FileReader roleReader = new FileReader(role);
            BufferedReader roleBuffer = new BufferedReader(roleReader);
            BufferedReader userBuffer = new BufferedReader(userReader);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                String[] split = s.split(SPLITTER);
                if (split[ID].equals(COMMENT_TAG)) {
                    list.add(null);
                    comments.add(s);
                    continue;
                }
                User user1 = getUser(roleBuffer, userBuffer, split[USER_ID]);
                if (user1.equals(user)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean containsOrderByBooks(Book[] books) {
        List<Order> list = new ArrayList<>();
        List<String> comments = new ArrayList<>();
        addFromFileToOrderList(list, comments);
        try {
            FileReader fileReader = new FileReader(file);
            FileReader authorReader = new FileReader(authorFile);
            FileReader bookReader = new FileReader(bookFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            BufferedReader authorBuffer = new BufferedReader(authorReader);
            BufferedReader bookBuffer = new BufferedReader(bookReader);
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                String[] split = s.split(SPLITTER);
                if (split[ID].equals(COMMENT_TAG)) {
                    list.add(null);
                    comments.add(s);
                    continue;
                }
                List<Book> bookList = getBooks(authorBuffer, bookBuffer, split[3]);
                if (Arrays.equals(bookList.toArray(), books)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean containsOrder(Order order) {
        List<Order> list = new ArrayList<>();
        List<String> comments = new ArrayList<>();
        addFromFileToOrderList(list, comments);
        try {
            FileReader fileReader = new FileReader(file);
            FileReader userReader = new FileReader(userFile);
            FileReader addressReader = new FileReader(addressFile);
            FileReader storeReader = new FileReader(storeFile);
            FileReader authorReader = new FileReader(authorFile);
            FileReader bookReader = new FileReader(bookFile);
            FileReader roleReader = new FileReader(role);
            FileReader statusReader = new FileReader(status);
            BufferedReader roleBuffer = new BufferedReader(roleReader);
            BufferedReader userBuffer = new BufferedReader(userReader);
            BufferedReader addressBuffer = new BufferedReader(addressReader);
            BufferedReader storeBuffer = new BufferedReader(storeReader);
            BufferedReader authorBuffer = new BufferedReader(authorReader);
            BufferedReader bookBuffer = new BufferedReader(bookReader);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            BufferedReader statusBuffer = new BufferedReader(statusReader);
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                String[] split = s.split(SPLITTER);
                if (split[ID].equals(COMMENT_TAG)) {
                    list.add(null);
                    comments.add(s);
                    continue;
                }
                User user = getUser(roleBuffer, userBuffer, split[USER_ID]);
                List<Book> books = getBooks(authorBuffer, bookBuffer, split[3]);
                Store store = getStore(storeBuffer, addressBuffer, split[5]);
                Order.Status status = getStatus(statusBuffer, split[STATUS]);
                Order order1 = new Order(Integer.parseInt(split[ID]),
                        Boolean.parseBoolean(split[IS_DELIVERY]),
                        user,
                        books.toArray(new Book[0]),
                        new Address(split[ADDRESS]),
                        store,
                        status);
                if (order1.equals(order)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
