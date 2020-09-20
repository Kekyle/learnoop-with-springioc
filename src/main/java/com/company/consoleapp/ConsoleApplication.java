package com.company.consoleapp;

import com.company.Application;
import com.company.consoleapp.action.*;
import com.company.consoleapp.util.ConsoleReader;
import com.company.consoleapp.util.ConsoleWriter;
import com.company.consoleapp.util.Reader;
import com.company.consoleapp.util.Writer;
import com.company.entity.Role;
import com.company.repository.*;
import com.company.repository.db.*;
import com.company.repository.file.*;
import com.company.service.*;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;

@Component
public class ConsoleApplication implements Application {
    private Writer writer;
    private Reader reader;
    private AuthorAction authorAction;
    private BookAction bookAction;
    private AddressAction addressAction;
    private StoreAction storeAction;
    private UserAction userAction;
    private OrderAction orderAction;

    public ConsoleApplication() throws SQLException {
    }

    @Override
    public void start() {
        while (true) {
            if (UserActionImpl.session == null) {
                showGuestMenu();
                writer.write("\nВы выбрали: ");
                switch (reader.readInt()) {
                    case 0:
                        return;
                    case 1:
                        userAction.registration();
                        break;
                    case 2:
                        userAction.authorization();
                        break;
                    default:
                        writer.write("\nВы выбрали что-то не то. Выберите действие из пунктов 0-2\n");
                }
            }
            if (UserActionImpl.session != null &&
                    UserActionImpl.session.getUser().getRole().equals(Role.USER)) {
                showUserMenu();
                writer.write("\nВы выбрали: ");
                switch (reader.readInt()) {
                    case 0:
                        return;
                    case 1:
                        showBookMenuForUser();
                        writer.write("\nВы выбрали: ");
                        switch (reader.readInt()) {
                            case 0:
                                continue;
                            case 1:
                                bookAction.findAllBooks();
                                break;
                            case 2:
                                bookAction.findAllBooksByAuthor();
                                break;
                            case 3:
                                bookAction.findBookByTitle();
                                break;
                            case 4:
                                bookAction.findAllBooksByPrice();
                                break;
                            default:
                                writer.write("\nВы выбрали что-то не то. Выберите действие из пунктов 0-4\n");
                        }
                        break;
                    case 2:
                        showAccountMenu();
                        writer.write("\nВы выбрали: ");
                        switch (reader.readInt()) {
                            case 0:
                                continue;
                            case 1:
                                userAction.updateUserName();
                                break;
                            case 2:
                                userAction.updateUserLogin();
                                break;
                            case 3:
                                userAction.updateUserPassword();
                                break;
                            case 4:
                                userAction.updateUserAddress();
                                break;
                            case 5:
                                showBasketMenu();
                                writer.write("\nВы выбрали: ");
                                switch (reader.readInt()) {
                                    case 0:
                                        continue;
                                    case 1:
                                        orderAction.addOrder(UserActionImpl.session.getBasket().getBooks());
                                        break;
                                    case 2:
                                        bookAction.findAllBooksInBasket();
                                        break;
                                    default:
                                        writer.write("\nВы выбрали что-то не то. Выберите действие из пунктов 0-2\n");
                                }
                                break;
                            case 6:
                                showOrderMenuForUser();
                                writer.write("\nВы выбрали: ");
                                switch (reader.readInt()) {
                                    case 0:
                                        continue;
                                    case 1:
                                        orderAction.findAllByUser();
                                        break;
                                    default:
                                        writer.write("\nВы выбрали что-то не то. Выберите действие из пунктов 0-1\n");
                                }
                                break;
                            case 7:
                                orderAction.removeBookFromOrderByUser();
                                break;
                            case 8:
                                userAction.findAboutUser();
                                break;
                            default:
                                writer.write("\nВы выбрали что-то не то. Выберите действие из пунктов 0-6\n");
                        }
                        break;
                    case 3:
                        userAction.logout();
                        break;
                    default:
                        writer.write("\nВы выбрали что-то не то. Выберите действие из пунктов 0-3\n");
                }
            }
            if (UserActionImpl.session != null && UserActionImpl.session.getUser().getRole().equals(Role.ADMIN)) {
                String name = UserActionImpl.session.getUser().getName();
                writer.write("\nПривет, " + name + "!\n");
                showAdminMenu();
                writer.write("\nВы выбрали: ");
                switch (reader.readInt()) {
                    case 0:
                        return;
                    case 1:
                        showAuthorMenu();
                        writer.write("\nВы выбрали: ");
                        switch (reader.readInt()) {
                            case 0:
                                continue;
                            case 1:
                                authorAction.addAuthor();
                                break;
                            case 2:
                                authorAction.findAllAuthors();
                                break;
                            case 3:
                                authorAction.findAuthorById();
                                break;
                            case 4:
                                authorAction.findAuthorByName();
                                break;
                            case 5:
                                authorAction.updateAuthorNameById();
                                break;
                            case 6:
                                authorAction.removeAuthorById();
                                break;
                            case 7:
                                authorAction.removeAuthorByName();
                                break;
                            default:
                                writer.write("\nВы выбрали что-то не то. Выберите действие из пунктов 0-7\n");
                        }
                        break;
                    case 2:
                        showBookMenuForAdmin();
                        writer.write("\nВы выбрали: ");
                        switch (reader.readInt()) {
                            case 0:
                                continue;
                            case 1:
                                bookAction.addBook();
                                break;
                            case 2:
                                bookAction.findAllBooks();
                            case 3:
                                bookAction.findAllBooksByAuthor();
                                break;
                            case 4:
                                bookAction.findBookById();
                                break;
                            case 5:
                                bookAction.findBookByTitle();
                                break;
                            case 6:
                                bookAction.findAllBooksByPrice();
                                break;
                            case 7:
                                bookAction.updateBookTitleById();
                                break;
                            case 8:
                                bookAction.updateBookAuthorById();
                                break;
                            case 9:
                                bookAction.updateBookDescriptionById();
                                break;
                            case 10:
                                bookAction.updateBookPriceById();
                                break;
                            case 11:
                                bookAction.removeBookById();
                                break;
                            case 12:
                                bookAction.removeBookByTitle();
                                break;
                            default:
                                writer.write("\nВы выбрали что-то не то. Выберите действие из пунктов 0-12\n");
                        }
                        break;
                    case 3:
                        showAddressMenu();
                        writer.write("\nВы выбрали: ");
                        switch (reader.readInt()) {
                            case 0:
                                continue;
                            case 1:
                                addressAction.addAddress();
                                break;
                            case 2:
                                addressAction.updateStreetNameById();
                                break;
                            case 3:
                                addressAction.findAllAddresses();
                                break;
                            case 4:
                                addressAction.findAddressById();
                                break;
                            case 5:
                                addressAction.findAddressByStreetName();
                                break;
                            case 6:
                                addressAction.removeAddressById();
                                break;
                            case 7:
                                addressAction.removeAddressByStreet();
                                break;
                            case 8:
                                addressAction.removeByAddress();
                                break;
                            default:
                                writer.write("\nВы выбрали что-то не то. Выберите действие из пунктов 0-8\n");
                        }
                        break;
                    case 4:
                        showStoreMenu();
                        writer.write("\nВы выбрали: ");
                        switch (reader.readInt()) {
                            case 0:
                                continue;
                            case 1:
                                storeAction.addStore();
                                break;
                            case 2:
                                storeAction.updateStoreAddressById();
                                break;
                            case 3:
                                storeAction.updateStoreNameById();
                                break;
                            case 4:
                                storeAction.findAllStores();
                                break;
                            case 5:
                                storeAction.findStoreById();
                                break;
                            case 6:
                                storeAction.findStoreByName();
                                break;
                            case 7:
                                storeAction.findStoreByAddress();
                                break;
                            case 8:
                                storeAction.removeStoreById();
                                break;
                            case 9:
                                storeAction.removeStoreByName();
                                break;
                            case 10:
                                storeAction.removeByStore();
                                break;
                            case 11:
                                storeAction.removeStoreByAddress();
                                break;
                            default:
                                writer.write("\nВы выбрали что-то не то. Выберите действие из пунктов 0-11\n");
                        }
                        break;
                    case 5:
                        showUserMenuForAdmin();
                        writer.write("\nВы выбрали: ");
                        switch (reader.readInt()) {
                            case 0:
                                continue;
                            case 1:
                                userAction.findAllUsers();
                                break;
                            case 2:
                                userAction.findUserById();
                                break;
                            case 3:
                                userAction.findUserByLogin();
                                break;
                            case 4:
                                userAction.findAllUsersByName();
                                break;
                            case 5:
                                userAction.findAllUsersByAge();
                                break;
                            case 6:
                                userAction.findUserByAddress();
                                break;
                            case 7:
                                userAction.updateUserLoginById();
                                break;
                            case 8:
                                userAction.updateUserPasswordById();
                                break;
                            case 9:
                                userAction.updateUserNameById();
                                break;
                            case 10:
                                userAction.updateUserAgeById();
                                break;
                            case 11:
                                userAction.updateUserAddressById();
                                break;
                            case 12:
                                userAction.removeUserById();
                                break;
                            case 13:
                                userAction.removeUserByLogin();
                                break;
                            case 14:
                                userAction.removeUser();
                                break;
                            default:
                                writer.write("\nВы выбрали что-то не то. Выберите действие из пунктов 0-14\n");
                        }
                        break;
                    case 6:
                        showOrderMenuForAdmin();
                        writer.write("\nВы выбрали: ");
                        switch (reader.readInt()) {
                            case 0:
                                continue;
                            case 1:
                                orderAction.findAllOrders();
                                break;
                            case 2:
                                orderAction.findOrderById();
                                break;
                            case 3:
                                orderAction.findOrderByDeliveryAddress();
                                break;
                            case 4:
                                orderAction.findOrderByUser();
                                break;
                            case 5:
                                orderAction.findAllOrdersByUser();
                                break;
                            case 6:
                                orderAction.findAllOrdersByPickUpStore();
                                break;
                            case 7:
                                orderAction.findAllOrdersByStatus();
                            case 8:
                                orderAction.updateOrderStatus();
                                break;
                            case 9:
                                orderAction.updatePickUpStore();
                                break;
                            case 10:
                                orderAction.updateDeliveryAddress();
                                break;
                            case 11:
                                orderAction.removeOrderById();
                                break;
                            case 12:
                                orderAction.removeOrderByPickUpStore();
                                break;
                            case 13:
                                orderAction.removeOrderByDeliveryAddress();
                                break;
                            default:
                                writer.write("\nВы выбрали что-то не то. Выберите действие из пунктов 0-13\n");
                        }
                        break;
                    case 7:
                        userAction.logout();
                        break;
                    default:
                        writer.write("\nВы выбрали что-то не то. Выберите действие из пунктов 0-7\n");
                }
            }
        }
    }


    private void showAdminMenu() {
//        String name = UserActionImpl.session.getUser().getName();
//            writer.write("\nПривет, " + name + "!\n");
        writer.write("\n0 - Выход из программы\n");
        writer.write("1 - Меню авторов\n");
        writer.write("2 - Меню книг\n");
        writer.write("3 - Меню адресов\n");
        writer.write("4 - Меню магазинов\n");
        writer.write("5 - Меню пользователей\n");
        writer.write("6 - Меню заказов\n");
        writer.write("7 - Выход из аккаунта\n");
    }

    private void showUserMenu() {
        String name = UserActionImpl.session.getUser().getName();
        writer.write("\nПривет, " + name + "!\n");
        writer.write("\n0 - Выход из программы\n");
        writer.write("1 - Меню книг\n");
        writer.write("2 - Личный кабинет\n");
        writer.write("3 - Выход из аккаунта\n");
    }

    private void showGuestMenu() {
        writer.write("\nHello Guest!\n");
        writer.write("\n0 - Выход\n");
        writer.write("1 - Регистрация\n");
        writer.write("2 - Авторизация\n");
    }

    private void showBasketMenu() {
        writer.write("\n0 - Выход в главное меню\n");
        writer.write("1 - Оформить заказ\n");
        writer.write("2 - Просмотреть корзину\n");
    }

    private void showAccountMenu() {
        writer.write("\n0 - Выход в главное меню\n");
        writer.write("1 - Обновить имя\n");
        writer.write("2 - Обновить логин\n");
        writer.write("3 - Обновить пароль\n");
        writer.write("4 - Обновить адрес\n");
        writer.write("5 - Перейти в корзину\n");
        writer.write("6 - Перейти к заказам\n");
        writer.write("7 - Удалить книгу из заказа\n");
        writer.write("8 - Показать профиль\n");
    }

    private void showOrderMenuForUser() {
        writer.write("\n0 - Выход в главное меню\n");
        writer.write("1 - Показать мои заказы\n");
        writer.write("2 - Удалить книгу из заказа\n");
    }

    private void showAuthorMenu() {
        writer.write("\n0 - Выход в главное меню\n");
        writer.write("1 - Добавить нового автора\n");
        writer.write("2 - Найти всех авторов\n");
        writer.write("3 - Найти автора по ID\n");
        writer.write("4 - Найти автора по имени\n");
        writer.write("5 - Обновить имя автора по ID\n");
        writer.write("6 - Удалить автора по ID\n");
        writer.write("7 - Удалить автора по имени\n");
    }

    private void showBookMenuForUser() {
        writer.write("\n0 - Выход в главное меню\n");
        writer.write("1 - Найти все книги\n");
        writer.write("2 - Найти все книги по автору\n");
        writer.write("3 - Найти книгу по названию\n");
        writer.write("4 - Найти книги по цене\n");
    }

    private void showBookMenuForAdmin() {
        writer.write("\n0 - Выход в главное меню\n");
        writer.write("1 - Добавить новую книгу\n");
        writer.write("2 - Найти все книги\n");
        writer.write("3 - Найти все книги по автору\n");
        writer.write("4 - Найти книгу по ID\n");
        writer.write("5 - Найти книгу по названию\n");
        writer.write("6 - Найти книги по цене\n");
        writer.write("7 - Обновить название книги по ID\n");
        writer.write("8 - Обновить автора книги по ID\n");
        writer.write("9 - Обновить описание книги по ID\n");
        writer.write("10 - Обновить цену книги по ID\n");
        writer.write("11 - Удалить книгу по ID\n");
        writer.write("12 - Удалить книгу по названию\n");
    }

    private void showAddressMenu() {
        writer.write("\n0 - Выход в главное меню\n");
        writer.write("1 - Добавить новый адрес\n");
        writer.write("2 - Обновить адрес по ID\n");
        writer.write("3 - Найти все адреса\n");
        writer.write("4 - Найти адрес по ID\n");
        writer.write("5 - Найти адрес по названию улицы\n");
        writer.write("6 - Удлаить адрес по ID\n");
        writer.write("7 - Удалить адрес по названию улицы\n");
        writer.write("8 - Удалить адрес из списка\n");
    }

    private void showStoreMenu() {
        writer.write("\n0 - Выход в главное меню\n");
        writer.write("1 - Добавить новый магазин\n");
        writer.write("2 - Обновить адрес магазина по ID\n");
        writer.write("3 - Обновить название магазина по ID\n");
        writer.write("4 - Найти все магазины\n");
        writer.write("5 - Найти магазин по ID\n");
        writer.write("6 - Найти магазин по названию\n");
        writer.write("7 - Найти магазин по адресу\n");
        writer.write("8 - Удлаить магазин по ID\n");
        writer.write("9 - Удалить магазин по названию \n");
        writer.write("10 - Удалить магазин из списка\n");
        writer.write("11 - Удалить магазин по адресу из списка\n");
    }

    private void showUserMenuForAdmin() {
        writer.write("\n0 - Выход в главное меню\n");
        writer.write("1 - Найти всех пользователей\n");
        writer.write("2 - Найти пользователя по ID\n");
        writer.write("3 - Найти пользователя по логину\n");
        writer.write("4 - Найти всех пользователей по имени\n");
        writer.write("5 - Найти всех пользователей по возрасту\n");
        writer.write("6 - Найти пользователя по адресу\n");
        writer.write("7 - Обновить логин пользователя по ID\n");
        writer.write("8 - Обновить пароль пользователя по ID\n");
        writer.write("9 - Обновить имя пользователя по ID\n");
        writer.write("10 - Обновить возраст пользователя по ID\n");
        writer.write("11 - Обновить адрес пользователя по ID\n");
        writer.write("12 - Удалить пользователя по ID\n");
        writer.write("13 - Удалить пользователя по логину\n");
        writer.write("14 - Удалить пользователя из списка\n");
    }

    private void showOrderMenuForAdmin() {
        writer.write("\n0 - Выход в главное меню\n");
        writer.write("1 - Найти все заказы\n");
        writer.write("2 - Найти заказ по ID\n");
        writer.write("3 - Найти заказ по адресу доставки\n");
        writer.write("4 - Найти заказ по пользователю\n");
        writer.write("5 - Найти все заказы по пользователю\n");
        writer.write("6 - Найти все заказы по пункту самовывоза\n");
        writer.write("7 - Найти все заказы по статусу\n");
        writer.write("8 - Обновить статус заказа\n");
        writer.write("9 - Обновить пункт самовывоза\n");
        writer.write("10 - Обновить адрес доставки\n");
        writer.write("11 - Удалить заказ по ID\n");
        writer.write("12 - Удалить заказ по пункту самовывоза\n");
        writer.write("13 - Удалить заказ по адресу доставки\n");
    }
}
