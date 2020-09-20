package com.company.consoleapp.action;

import com.company.consoleapp.util.ConsoleReader;
import com.company.consoleapp.util.ConsoleWriter;
import com.company.consoleapp.util.Reader;
import com.company.consoleapp.util.Writer;
import com.company.consoleapp.validator.AddressValidator;
import com.company.consoleapp.validator.BookValidator;
import com.company.consoleapp.validator.OrderValidator;
import com.company.entity.*;
import com.company.service.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;
@Component
public class OrderActionImpl implements OrderAction {
    private Writer writer;
    private Reader reader;
    private OrderService orderService;
    private StoreService storeService;
    private BookService bookService;
    private UserService userService;
    private AddressService addressService;

    @Override
    public void addOrder(Book[] books) {
        writer.write("\nВыберите тип доставки:\n1 - Доставка\n2 - Самовывоз\n");
        writer.write("Вы выбрали: ");
        int choiceOfDeliveryType = reader.readInt();
        switch (choiceOfDeliveryType) {
            case 1:
                writer.write("\nВведите адрес доставки: ");
                String street = reader.readString();
                if (AddressValidator.invalidStreet(street)) {
                    writer.write("\nНекорректное название улицы\n");
                    return;
                }
                Address address = new Address(street);
                Order orderDelivery = new Order(UserActionImpl.session.getUser(), bookService.findAllBooks(), address);
                boolean addDelivery = orderService.addOrder(orderDelivery);
                if (addDelivery) {
                    writer.write("\nЗаказ добавлен\n");
                } else {
                    writer.write("\nЗаказ не добавлен\n");
                }
                break;
            case 2:
                writer.write("\nВыберите пункт самовывоза: \n");
                Store[] allStores = storeService.findAllStores();
                for (int j = 0; j < allStores.length; j++) {
                    writer.write("Магазин " + (j + 1) + " - " + allStores[j].getName() + "\n");
                }
                writer.write("Вы выбрали: ");
                int storeSelection = reader.readInt() - 1;
                if (storeSelection < allStores.length && storeSelection >= 0) {
                    Store store = allStores[storeSelection];
                    Order orderPickUp = new Order(UserActionImpl.session.getUser(), bookService.findAllBooks(), store);
                    boolean addPickUp = orderService.addOrder(orderPickUp);
                    if (addPickUp) {
                        writer.write("\nЗаказ добавлен\n");
                    } else {
                        writer.write("\nЗаказ не добавлен\n");
                    }
                } else {
                    writer.write("\nВы выбрали что-то не то (м.б. пункт, которого даже нет в списке)\n");
                }
                break;
            default:
                writer.write("\nВы выбрали что-то не то. Выберите тип доставки из пунктов 1-2\n");
        }
    }

    @Override
    public void updateBookInOrder() {
        writer.write("\n\n---Замена книги в заказе---\n");
        Order order = getOrder();
        if (order == null) return;
        writer.write("\nВыберите книгу, которую хотите заменить: \n");
        Book[] books = getBooks(order);
        writer.write("Вы выбрали: ");
        int bookSelection = reader.readInt() - 1;
        if (bookSelection < books.length && bookSelection >= 0) {
            Book book = books[bookSelection];
            writer.write("\nВведите название книги: ");
            String title = reader.readString();
            if (BookValidator.invalidTitle(title)) {
                writer.write("\nНекорректное название книги (м.б. слишком короткое)\n");
                return;
            }
//            if (bookService.existBookByTitle(title)) {
//
//            }
            Book newBook = bookService.findBookByTitle(title);
            if (newBook == null) {
                writer.write("Book not found");
                return;
            }

            if (orderService.updateBookInOrder(book, newBook, order.getId())) {
                writer.write("\nКнига успешно заменена\n");
            } else {
                writer.write("\nНе удалось заменить книгу. Что-то пошло не так\n");
            }
        } else {
            writer.write("\nВы выбрали что-то не то (м.б. пункт, которого даже нет в списке)\n");
        }
    }

    @Override
    public void updateDeliveryAddress() {
        writer.write("\n\n---Обновление адреса доставки заказа---\n");
//        Order order = getOrder();
        writer.write("\nВыберите заказ, в котором хотите изменить адрес:\n");
        Order[] orders = orderService.findAllOrders();
        if (orders == null) return;
        for (Order order : orders) {
            if (order.getStore() != null) continue;
            writer.write("\nЗаказ № " + order.getId() + "\n"
                    + "Статус: " + order.getStatus() + "\n"
                    + "Пользователь: "
                    + " Имя - " + order.getUser().getName()
                    + "; Логин - " + order.getUser().getLogin()
                    + "; Возраст - " + order.getUser().getAge()
                    + "; Адрес пользователя - " + order.getUser().getAddress().getStreet() + "\n"
                    + "Книги: " + Arrays.toString(order.getBooks()) + "\n"
                    + "Адрес доставки: " + order.getAddress().getStreet() + "\n");
        }
//        if (order == null) return;
        writer.write("\nВыберите заказ: ");
        int orderSelection = reader.readInt() - 1;
        if (orderSelection < orders.length && orderSelection >= 0) {
            if (orders[orderSelection].getStore() != null) {
                writer.write("\nВы выбрали что-то не то (м.б. пункт, которого даже нет в списке)\n");
                return;
            }
            Order order = orders[orderSelection];
            writer.write("\nВведите новый адрес доставки: ");
            String newStreet = reader.readString();
            if (AddressValidator.invalidStreet(newStreet)) {
                writer.write("\nНекорректное название улицы\n");
                return;
            }
            Address newAddress = new Address(newStreet);
            if (orderService.updateDeliveryAddress(newAddress, order.getId())) {
                writer.write("\nАдрес доставки успешно обновлен !\n");
            } else {
                writer.write("\nНе удалось обновить адрес доставки. Что-то пошло не так\n");
            }
        } else {
            writer.write("\nВы выбрали что-то не то (м.б. пункт, которого даже нет в списке)\n");
        }
    }

    @Override
    public void updatePickUpStore() {
        writer.write("\n\n---Обновление пункта самовывоза заказа---\n");
        Order order = getOrder();
        if (order == null) return;
        writer.write("\nВыберите новый пункт самовывоза:\n");
        Store[] allStores = getStores();
        writer.write("Вы выбрали: ");
        int storeSelection = reader.readInt() - 1;
        if (storeSelection < allStores.length && storeSelection >= 0) {
            Store store = allStores[storeSelection];
            if (orderService.updatePickUpStore(store, order.getId())) {
                writer.write("\nПункт самовывоза был успешно обновлен !\n");
            } else {
                writer.write("\nНе удалось обновить пункт самовывоза. Что-то пошло не так\n");
            }
        } else {
            writer.write("\nВы выбрали что-то не то (м.б. пункт, которого даже нет в списке)\n");
        }
    }

    @Override
    public void updateOrderStatus() {
        writer.write("\nВыберите заказ, у которого хотите изменить статус: \n");
        Order[] all = orderService.findAllOrders();
        for (Order order : all) {
            writer.write("\nЗаказ №" + order.getId() + "\n"
                    + "статус: " + order.getStatus() + "\n");
        }
        writer.write("\nВы выбрали: ");
        int orderSelection = reader.readInt() - 1;
        if (orderSelection < all.length && orderSelection >= 0) {
            Order order = all[orderSelection];
            writer.write("\nВыберите новый статус: \n");
            Order.Status[] values = getStatuses();
            writer.write("\nВы выбрали: ");
            int statusSelection = reader.readInt() - 1;
            Order.Status value = values[statusSelection];
            order.setStatus(value);
        } else {
            writer.write("\nВы выбрали что-то не то (м.б. пункт, которого даже нет в списке)\n");
        }
    }

    @Override
    public void removeBookFromOrder() {
        writer.write("\n\n---Удаление книги из заказа---\n");
        Order order = getOrder();
        writer.write("\nВыберите книгу из этого заказа: \n");
        Book[] books = getBooks(order);
        writer.write("\nВы выбрали: ");
        int bookSelection = reader.readInt() - 1;
        if (bookSelection < books.length && bookSelection >= 0) {
            Book book = books[bookSelection];
            if (orderService.removeBookFromOrder(book, order.getId())) {
                writer.write("\nКнига успешно удалена\n");
            } else {
                writer.write("\nНе удалось удалить книгу в заказе. Что-то пошло не так\n");
            }
        } else {
            writer.write("\nВы выбрали что-то не то (м.б. пункт, которого даже нет в списке)\n");
        }
    }

    @Override
    public void removeBookFromOrderByUser() {
        writer.write("\n\n---Удаление книги из заказа---\n");
        Order[] orders = orderService.findAllOrdersByUser(UserActionImpl.session.getUser());
        writer.write("\nВаши заказы:\n");
        for (int i = 0; i < orders.length; i++) {
            writer.write("\nЗаказ " + (i + 1) + "\n"
                    + "Книги: " + Arrays.toString(orders[i].getBooks()) + "\n");
            if (orders[i].getStore() != null) {
                writer.write("Адрес магазина: " + orders[i].getStore().getAddress().getStreet() + "\n");
            } else if (orders[i].getAddress() != null) {
                writer.write("Адрес доставки: " + orders[i].getAddress().getStreet() + "\n");
            }
        }
        writer.write("\nВыберите заказ: ");
        int orderSelection = reader.readInt() - 1;
        Order order = orders[orderSelection];
        writer.write("\nВыберите книгу из этого заказа: \n");
        Book[] books = getBooks(order);
        writer.write("\nВы выбрали: ");
        int bookSelection = reader.readInt() - 1;
        if (bookSelection < books.length && bookSelection >= 0) {
            Book book = books[bookSelection];
            if (orderService.removeBookFromOrder(book, order.getId())) {
                writer.write("\nКнига успешно удалена\n");
            } else {
                writer.write("\nНе удалось удалить книгу в заказе. Что-то пошло не так\n");
            }
        } else {
            writer.write("\nВы выбрали что-то не то (м.б. пункт, которого даже нет в списке)\n");
        }
    }

    @Override
    public void removeOrderById() {
        writer.write("\nВведите id заказа, который хотите удалить: ");
        int id = reader.readInt();
        if (OrderValidator.invalidId(id)) {
            writer.write("\nВы ввели id, с которым что-то не то (м.б. < 0 или такого id вообще нет)\n");
            return;
        }
        orderService.removeOrderById(id);
    }

    @Override
    public void removeOrderByPickUpStore() {
        writer.write("\nВыберите магазин, по которому хотите удалить все заказы: \n");
        Store[] all = getStores();
        writer.write("Вы выбрали: ");
        int storeSelection = reader.readInt() - 1;
        Store store = all[storeSelection];
        if (orderService.removeOrderByPickUpStore(store)) {
            writer.write("\nЗаказы магазина " + store.getName() + " были успешно удалены !\n");
        } else {
            writer.write("\nНе удалось удалить заказы магазина. Что-то пошло не так (вероятно у этого магазина нет заказов)\n");
        }
    }

    @Override
    public void removeOrderByDeliveryAddress() {
        writer.write("\nВведите адрес доставки, по которому хотите удалить заказ: ");
        String street = reader.readString();
        if (AddressValidator.invalidStreet(street)) {
            writer.write("\nНекорректное название улицы\n");
            return;
        }
        Address address = new Address(street);
        orderService.removeOrderByDeliveryAddress(address);
    }

    @Override
    public void findAllOrders() {
        Order[] all = orderService.findAllOrders();
        for (Order order : all) {
            if(order == null) continue;
            writer.write("\nЗаказ № " + order.getId() + "\n"
                    + "Статус: " + order.getStatus() + "\n"
                    + "Пользователь: "
                    + " Имя - " + order.getUser().getName()
                    + "; Логин - " + order.getUser().getLogin()
                    + "; Возраст - " + order.getUser().getAge()
                    + "; Адрес пользователя - " + order.getUser().getAddress().getStreet() + "\n"
                    + "Книги: " + Arrays.toString(order.getBooks()) + "\n");
            if (order.getStore() != null) {
                writer.write("Адрес магазина: " + order.getStore().getAddress().getStreet() + "\n");
            } else if (order.getAddress() != null) {
                writer.write("Адрес доставки: " + order.getAddress().getStreet() + "\n");
            }
        }
    }

    @Override
    public void findOrderById() {
        writer.write("\nВведите id заказа, который хотите найти: ");
        int id = reader.readInt();
        if (OrderValidator.invalidId(id)) {
            writer.write("\nВы ввели id, с которым что-то не то (м.б. < 0 или такого id вообще нет)\n");
            return;
        }
        Order order = orderService.findOrderById(id);
        writer.write("\nЗаказ №" + order.getId() + "\n"
                + "Статус: " + order.getStatus() + "\n"
                + "Пользователь: "
                + " Имя - " + order.getUser().getName()
                + "; Логин - " + order.getUser().getLogin()
                + "; Возраст - " + order.getUser().getAge()
                + "; Адрес пользователя - " + order.getUser().getAddress().getStreet() + "\n"
                + "Книги: " + Arrays.toString(order.getBooks()) + "\n");
        if (order.getStore() != null) {
            writer.write("Адрес магазина: " + order.getStore().getAddress().getStreet() + "\n");
        } else if (order.getAddress() != null) {
            writer.write("Адрес доставки: " + order.getAddress().getStreet() + "\n");
        }
    }

    @Override
    public void findOrderByDeliveryAddress() {
        writer.write("\nВведите адрес заказа, который хотите найти: ");
        String street = reader.readString();
        if (AddressValidator.invalidStreet(street)) {
            writer.write("\nНекорректное название улицы\n");
            return;
        }
        Address addressOfOrder = new Address(street);
        if (orderService.existOrderByDeliveryAddress(addressOfOrder)) {
            Order order = orderService.findOrderByDeliveryAddress(addressOfOrder);
            writer.write("\nЗаказ №" + order.getId() + "\n"
                    + "Адрес доставки: " + order.getAddress().getStreet() + "\n"
                    + "Статус: " + order.getStatus() + "\n"
                    + "Пользователь: "
                    + " Имя - " + order.getUser().getName()
                    + "; Логин - " + order.getUser().getLogin()
                    + "; Возраст - " + order.getUser().getAge()
                    + "; Адрес пользователя - " + order.getUser().getAddress().getStreet() + "\n"
                    + "Книги: " + Arrays.toString(order.getBooks()) + "\n");
        } else {
            writer.write("\nУ нас нет адреса, который вы ищете\n");
        }
    }

    @Override
    public void findOrderByUser() {
        writer.write("\nВыберите пользователя, заказ которого хотитет найти:\n");
        User[] all = userService.findAllUsers();
        System.out.println();
        for (int i = 0; i < all.length; i++) {
            User user = all[i];
            writer.write("Пользователь: " + (i + 1)
                    + "; Имя - " + user.getName()
                    + "; Логин - " + user.getLogin()
                    + "; Возраст - " + user.getAge()
                    + "; Адрес пользователя - " + user.getAddress().getStreet() + "\n");
        }
        writer.write("Вы выбрали: ");
        int userSelection = reader.readInt() - 1;
        User user = all[userSelection];
        Order order = orderService.findOrderByUser(user);
        writer.write("\nЗаказ пользователя с логином \"" + order.getUser().getLogin() + "\"\n"
                + "Статус: " + order.getStatus() + "\n"
                + "Пользователь: "
                + " Имя - " + order.getUser().getName()
                + "; Логин - " + order.getUser().getLogin()
                + "; Возраст - " + order.getUser().getAge()
                + "; Адрес пользователя - " + order.getUser().getAddress().getStreet() + "\n"
                + "Книги: " + Arrays.toString(order.getBooks()) + "\n");
        if (order.getStore() != null) {
            writer.write("Адрес магазина (пункта самовывоза): " + order.getStore().getAddress().getStreet() + "\n");
        } else if (order.getAddress() != null) {
            writer.write("Адрес доставки: " + order.getAddress().getStreet() + "\n");
        }
    }

    @Override
    public void findAllByUser() {
        // TODO: 22.04.2020 Реализовать множ выборку заказов для опр пользователя
        Order[] allOrdersByUser = orderService.findAllOrdersByUser(UserActionImpl.session.getUser());
//        Order orderByUser = orderService.findOrderByUser(UserActionImpl.session.getUser());
//        writer.write(orderByUser.toString());
        for (int i = 0; i < allOrdersByUser.length; i++) {
            Order order = allOrdersByUser[i];
            writer.write("\nЗаказ № " + (i + 1) + "\n"
                    + "Статус: " + order.getStatus() + "\n"
                    + "Пользователь: "
                    + " Имя - " + order.getUser().getName()
                    + "; Логин - " + order.getUser().getLogin()
                    + "; Возраст - " + order.getUser().getAge()
                    + "; Адрес пользователя - " + order.getUser().getAddress().getStreet() + "\n"
                    + "Книги: " + Arrays.toString(order.getBooks()) + "\n");
            if (order.getStore() != null) {
                writer.write("Адрес магазина: " + order.getStore().getAddress().getStreet() + "\n");
            } else if (order.getAddress() != null) {
                writer.write("Адрес доставки: " + order.getAddress().getStreet() + "\n");
            }
        }
    }

    @Override
    public void findAllOrdersByPickUpStore() {
        writer.write("\nВыберите магазин, у которого хотите найти все заказы:\n");
        Store[] all = getStores();
        writer.write("Вы выбрали: ");
        int storeSelection = reader.readInt() - 1;
        Store store = all[storeSelection];
        if (orderService.existOrderByPickUpStore(store)) {
            Order[] allByStore = orderService.findAllOrdersByPickUpStore(store);
            for (Order order : allByStore) {
                writer.write("\nЗаказ №" + order.getId() + "\n"
                        + "Адрес магазина(пункта самовывоза): " + order.getStore().getAddress().getStreet() + "\n"
                        + "Статус: " + order.getStatus() + "\n"
                        + "Пользователь: "
                        + " Имя - " + order.getUser().getName()
                        + "; Логин - " + order.getUser().getLogin()
                        + "; Возраст - " + order.getUser().getAge()
                        + "; Адрес пользователя - " + order.getUser().getAddress().getStreet() + "\n"
                        + "Книги: " + Arrays.toString(order.getBooks()) + "\n");
            }
        } else {
            writer.write("\nУ данного магазина нет заказов\n");
        }
    }

    @Override
    public void findAllOrdersByStatus() {
        writer.write("\nВыберите статус, по которому хотите найти все заказы: \n");
        Order.Status[] values = getStatuses();
        writer.write("\nВы выбрали: ");
        int selection = reader.readInt() - 1;
        Order.Status value = values[selection];
        if (orderService.existOrderByStatus(value)) {
            Order[] all = orderService.findAllOrdersByStatus(value);
            for (int i = 0; i < all.length; i++) {
                Order order = all[i];
                writer.write("\nЗаказ № " + (i + 1) + "\n"
                        + "Статус: " + order.getStatus() + "\n"
                        + "Пользователь: "
                        + " Имя - " + order.getUser().getName()
                        + "; Логин - " + order.getUser().getLogin()
                        + "; Возраст - " + order.getUser().getAge()
                        + "; Адрес пользователя - " + order.getUser().getAddress().getStreet() + "\n"
                        + "Книги: " + Arrays.toString(order.getBooks()) + "\n");
                if (order.getStore() != null) {
                    writer.write("Адрес магазина: " + order.getStore().getAddress().getStreet() + "\n");
                } else if (order.getAddress() != null) {
                    writer.write("Адрес доставки: " + order.getAddress().getStreet() + "\n");
                }
            }
        } else {
            writer.write("\nЗаказов с таким статусом нет\n");
        }
    }

    @Override
    public void findAllOrdersByUser() {
        writer.write("\nВыберите пользователя, у которого хотите найти все заказы: \n");
        User[] users = userService.findAllUsers();
        System.out.println();
        for (User user : users) {
            writer.write("Пользователь " + user.getId() + "\n"
                    + "Логин -  " + user.getLogin() + "\n"
                    + "Пароль - " + user.getPassword() + "\n"
                    + "Имя - " + user.getName() + "\n"
                    + "Возраст - " + user.getAge() + "\n"
                    + "Адрес - " + user.getAddress().getStreet() + "\n\n");
        }
        writer.write("\nВы выбрали: ");
        int selection = reader.readInt() - 1;
        User user = users[selection];
        if (orderService.existOrderByUser(user)) {
            Order[] all = orderService.findAllOrdersByUser(user);
            for (int i = 0; i < all.length; i++) {
                Order order = all[i];
                writer.write("\nЗаказ № " + (i + 1) + "\n"
                        + "Статус: " + order.getStatus() + "\n"
                        + "Пользователь: "
                        + " Имя - " + order.getUser().getName()
                        + "; Логин - " + order.getUser().getLogin()
                        + "; Возраст - " + order.getUser().getAge()
                        + "; Адрес пользователя - " + order.getUser().getAddress().getStreet() + "\n"
                        + "Книги: " + Arrays.toString(order.getBooks()) + "\n");
                if (order.getStore() != null) {
                    writer.write("Адрес магазина: " + order.getStore().getAddress().getStreet() + "\n");
                } else if (order.getAddress() != null) {
                    writer.write("Адрес доставки: " + order.getAddress().getStreet() + "\n");
                }
            }
        } else {
            writer.write("\nЗаказов с таким статусом нет\n");
        }
    }

    private Order getOrder() {
        writer.write("\nВыберите магазин, в котором вы работаете:\n");
        Store[] allStores = getStores();
        writer.write("Вы выбрали: ");
        int storeSelection = reader.readInt() - 1;
        Store store = allStores[storeSelection];
        if (orderService.existOrderByPickUpStore(store)) {
            Order[] orders = orderService.findAllOrdersByPickUpStore(store);
            writer.write("\nДоступные заказы:\n");
            for (int i = 0; i < orders.length; i++) {
                writer.write("\nЗаказ " + (i + 1) + "\n"
                        + "Статус: " + orders[i].getStatus() + "\n"
                        + "Пользователь: "
                        + " Имя - " + orders[i].getUser().getName()
                        + "; Логин - " + orders[i].getUser().getLogin()
                        + "; Возраст - " + orders[i].getUser().getAge()
                        + "; Адрес пользователя - " + orders[i].getUser().getAddress().getStreet() + "\n"
                        + "Книги: " + Arrays.toString(orders[i].getBooks()) + "\n");
                if (orders[i].getStore() != null) {
                    writer.write("Адрес магазина: " + orders[i].getStore().getAddress().getStreet() + "\n");
                } else if (orders[i].getAddress() != null) {
                    writer.write("Адрес доставки: " + orders[i].getAddress().getStreet() + "\n");
                }
            }
            writer.write("\nВыберите заказ: ");
            int orderSelection = reader.readInt() - 1;
            return orders[orderSelection];
        } else {
            writer.write("\nУ данного магазина нет заказов\n");
        }
        return null;
    }

    private Book[] getBooks(Order order) {
        Book[] books = order.getBooks();
        for (int i = 0; i < books.length; i++) {
            if (books[i] == null) continue;
            writer.write("\nКнига " + (i + 1)
                    + "; Название: " + books[i].getTitle()
                    + "; Автор: " + books[i].getAuthor().getName()
                    + "; Опсиание: " + books[i].getDescription()
                    + "; Цена = " + books[i].getPrice() + "\n");
        }
        return books;
    }

    private Store[] getStores() {
        Store[] all = storeService.findAllStores();
        for (int i = 0; i < all.length; i++) {
            writer.write("Магазин " + (i + 1) + " - \"" + all[i].getName() + "\" по адресу " + all[i].getAddress().getStreet() + "\n");
        }
        return all;
    }

    private Order.Status[] getStatuses() {
        Order.Status[] values = Order.Status.values();
        for (int i = 0; i < values.length; i++) {
            writer.write("Статус " + (i + 1) + " - " + values[i].name() + "\n");
        }
        return values;
    }
}
