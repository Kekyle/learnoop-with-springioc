package com.company.consoleapp.action;

import com.company.consoleapp.util.*;
import com.company.consoleapp.validator.AddressValidator;
import com.company.consoleapp.validator.UserValidator;
import com.company.entity.Address;
import com.company.entity.Role;
import com.company.entity.User;
import com.company.service.UserService;
import com.company.service.UserServiceImpl;
import org.springframework.stereotype.Component;

@Component
public class UserActionImpl implements UserAction {
    public static Session session = null;

    private Writer writer;
    private Reader reader;
    private UserService userService;

    @Override
    public void addUser() {
        writer.write("\nВведите логин пользователя: ");
        String login = reader.readString();
        if (UserValidator.invalidLogin(login)) {
            writer.write("\nНекорректный логин (м.б. слишком короткий) !\n");
            return;
        }
        writer.write("Введите пароль: ");
        String password = reader.readString();
        if (UserValidator.invalidPassword(password)) {
            writer.write("\nНекорректный пароль (м.б. слишком короткий) !\n");
            return;
        }
        writer.write("Введите имя пользователя: ");
        String name = reader.readString();
        if (UserValidator.invalidName(name)) {
            writer.write("\nНекорректное имя (м.б. слишком короткое) !\n");
            return;
        }
        writer.write("Введите возраст: ");
        int age = reader.readInt();
        if (UserValidator.invalidAge(age)) {
            writer.write("\nНекорректный возраст (м.б. < 0) !\n");
            return;
        }
        writer.write("Введите адрес: ");
        String street = reader.readString();
        if (AddressValidator.invalidStreet(street)) {
            writer.write("\nНекорректное название улицы (слишком короткое) !\n");
            return;
        }
        Address address = new Address(street);

        if (login.equals("admin")) {
            User user = new User(login, password, age, name, address, Role.ADMIN);
            if (userService.addUser(user)) {
                writer.write("\nРегистрация прошла успешно !\n");
            } else {
                writer.write("\nЧто-то не удалось, возможно такой пользователь уже есть\n");
            }
        } else {
            User user = new User(login, password, age, name, address, Role.USER);
            if (userService.addUser(user)) {
                writer.write("\nРегистрация прошла успешно !\n");
            } else {
                writer.write("\nЧто-то не удалось, возможно такой пользователь уже есть\n");
            }
        }

    }

    @Override
    public void updateUserLoginById() {
        writer.write("\nВведите id пользователя, у которого хотите изменить логин: ");
        int id = reader.readInt();
        if (UserValidator.invalidId(id)) {
            writer.write("\nНекорректный id (м.б. < 0)!\n");
            return;
        }
        if (userService.existUserById(id)) {
            writer.write("Введите новый логин: ");
            String login = reader.readString();
            if (UserValidator.invalidLogin(login)) {
                writer.write("\nНекорректный логин (м.б. слишком короткий) !\n");
                return;
            }
            if (userService.updateUserLoginById(login, id)) {
                User user = userService.findUserById(id);
                writer.write("Логин успешно обновлен на \"" + user.getLogin() + "\"\n");
            } else {
                writer.write("\nНе удалось обновить логин\n");
            }
        } else {
            writer.write("\nВы ввели id, с которым что-то не то (м.б. такого id вообще нет или id < 0)\n");
        }
    }

    @Override
    public void updateUserPasswordById() {
        writer.write("\nВведите id пользователя, у которого хотите изменить пароль: ");
        int id = reader.readInt();
        if (UserValidator.invalidId(id)) {
            writer.write("\nНекорректный id (м.б. < 0)!\n");
            return;
        }
        if (userService.existUserById(id)) {
            writer.write("Введите новый пароль: ");
            String password = reader.readString();
            if (UserValidator.invalidPassword(password)) {
                writer.write("\nНекорректный пароль (м.б. слишком короткий) !\n");
                return;
            }
            if (userService.updateUserPasswordById(password, id)) {
                User user = userService.findUserById(id);
                writer.write("Пароль успешно обновлен на - " + user.getPassword() + "\n");
            } else {
                writer.write("\nНе удалось обновить пароль\n");
            }
        } else {
            writer.write("\nВы ввели id, с которым что-то не то (м.б. такого id вообще нет или id < 0)\n");
        }
    }

    @Override
    public void updateUserNameById() {
        writer.write("\nВведите id пользователя, у которого хотите изменить имя: ");
        int id = reader.readInt();
        if (UserValidator.invalidId(id)) {
            writer.write("\nНекорректный id (м.б. < 0)!\n");
            return;
        }
        if (userService.existUserById(id)) {
            writer.write("Введите новое имя: ");
            String name = reader.readString();
            if (UserValidator.invalidName(name)) {
                writer.write("\nНекорректное имя (м.б. слишком короткое) !\n");
                return;
            }
            if (userService.updateUserNameById(name, id)) {
                User user = userService.findUserById(id);
                writer.write("Имя успешно обновлено на \"" + user.getName() + "\"\n");
            } else {
                writer.write("\nНе удалось обновить имя\n");
            }
        } else {
            writer.write("\nВы ввели id, с которым что-то не то (м.б. такого id вообще нет или id < 0)\n");
        }
    }

    @Override
    public void updateUserAddressById() {
        writer.write("\nВведите id пользователя, у которого хотите изменить адрес: ");
        int id = reader.readInt();
        if (UserValidator.invalidId(id)) {
            writer.write("\nНекорректный id (м.б. < 0)!\n");
            return;
        }
        if (userService.existUserById(id)) {
            writer.write("Введите новый адрес: ");
            String street = reader.readString();
            if (AddressValidator.invalidStreet(street)) {
                writer.write("\nНекорректное название улицы (слишком короткое) !\n");
                return;
            }
            Address address = new Address(street);
            if (userService.updateUserAddressById(address, id)) {
                User user = userService.findUserById(id);
//        user.setAddress(newAddress);
                writer.write("Адрес успешно обновлен на - " + user.getAddress().getStreet() + "\n");
            } else {
                writer.write("\nНе удалось обновить адрес\n");
            }
        } else {
            writer.write("\nВы ввели id, с которым что-то не то (м.б. такого id вообще нет или id < 0)\n");
        }
        // TODO: 11.04.2020 Реализовать findById()
    }

    @Override
    public void updateUserAgeById() {
        writer.write("\nВведите id пользователя, у которого хотите изменить возраст: ");
        int id = reader.readInt();
        if (UserValidator.invalidId(id)) {
            writer.write("\nНекорректный id (м.б. < 0)!\n");
            return;
        }
        if (userService.existUserById(id)) {
            writer.write("Введите новый возраст: ");
            int age = reader.readInt();
            if (UserValidator.invalidAge(age)) {
                writer.write("\nНекорректный возраст (м.б. < 0) !\n");
                return;
            }
            if (userService.updateUserAgeById(age, id)) {
                User user = userService.findUserById(id);
                writer.write("Возраст успешно обновлено на " + user.getAge() + "\n");
            } else {
                writer.write("\nНе удалось обновить возраст\n");
            }
        } else {
            writer.write("\nВы ввели id, с которым что-то не то (м.б. такого id вообще нет или id < 0)\n");
        }
    }

    @Override
    public void updateUserLogin() {
        writer.write("Введите новый логин: ");
        String login = reader.readString();
        if (UserValidator.invalidLogin(login)) {
            writer.write("\nInvalid login\n");
            return;
        }
        boolean b = userService.updateUserLoginById(login, session.getUser().getId());
    }

    @Override
    public void updateUserPassword() {
        writer.write("Введите новый пароль: ");
        String password = reader.readString();
        if (UserValidator.invalidPassword(password)) {
            writer.write("\nInvalid password\n");
            return;
        }
        boolean b = userService.updateUserPasswordById(password, session.getUser().getId());
    }

    @Override
    public void updateUserName() {
        writer.write("Введите новое имя: ");
        String name = reader.readString();
        if (UserValidator.invalidName(name)) {
            writer.write("\nInvalid name\n");
            return;
        }
        boolean b = userService.updateUserNameById(name, session.getUser().getId());
    }

    @Override
    public void updateUserAddress() {
        writer.write("Введите новый адрес: ");
        String street = reader.readString();
        if (AddressValidator.invalidStreet(street)) {
            writer.write("\nInvalid street\n");
            return;
        }
        Address address = new Address(street);
        boolean b = userService.updateUserAddressById(address, session.getUser().getId());
    }

    @Override
    public void updateUserAge() {
        writer.write("Введите новый возраст: ");
        int age = reader.readInt();
        if (UserValidator.invalidAge(age)) {
            writer.write("\nInvalid age\n");
            return;
        }
        boolean b = userService.updateUserAgeById(age, session.getUser().getId());
    }

    @Override
    public void removeUserById() {
        writer.write("\nВведите id пользователя, которого хотите удалить: ");
        int id = reader.readInt();
        if (UserValidator.invalidId(id)) {
            writer.write("\nНекорректный id (м.б. < 0)!\n");
            return;
        }
        if (userService.removeUserById(id)) {
            writer.write("\nПользователь успешно удален!\n");
        } else {
            writer.write("\nНе удалось удалить пользователя по id. (м.б. ввели id, которого нет)\n");
        }
    }
    // TODO: 11.04.2020 Реализовать вывод сообщения об успешном удалении

    @Override
    public void removeUserByLogin() {
        writer.write("\nВведите логин пользователя, которого хотите удалить: ");
        String login = reader.readString();
        if (UserValidator.invalidLogin(login)) {
            writer.write("\nНекорректный логин (м.б. слишком короткий) !\n");
            return;
        }
        if (userService.removeUserByLogin(login)) {
            writer.write("\nПользователь успешно удален!\n");
        } else {
            writer.write("\nНе удалось удалить пользователя по логину. (м.б. ввели логин, которого нет)\n");
        }
    }

    @Override
    public void removeUser() {
        writer.write("\nВыберите пользователя, которого хотите удалить: \n");
        User[] allUsers = userService.findAllUsers();
        for (int i = 0; i < allUsers.length; i++) {
            writer.write("Пользователь " + (i + 1) + " - " + allUsers[i].getLogin() + "\n");
        }
        writer.write("Вы выбрали: ");
        int userSelection = reader.readInt() - 1;
        if (userSelection < allUsers.length && userSelection >= 0) {
            User user = allUsers[userSelection];
            if (userService.removeUser(user)) {
                writer.write("\nПользователь успешно удален!\n");
            } else {
                writer.write("\nчто-то пошло не так\n");
            }
        } else {
            writer.write("\nВы выбрали что-то не то (м.б. пункт, которого даже нет в списке)\n");
        }
    }

    @Override
    public void findAboutUser() {
        User user = userService.findUserById(session.getUser().getId());
        writer.write("\nПользователь " + user.getName() + "\n"
                + "Логин -  " + user.getLogin() + "\n"
                + "Пароль - " + user.getPassword() + "\n"
                + "Имя - " + user.getName() + "\n"
                + "Возраст - " + user.getAge() + "\n"
                + "Адрес - " + user.getAddress().getStreet() + "\n");
    }

    @Override
    public void findAllUsers() {
        User[] allUsers = userService.findAllUsers();
        System.out.println();
        for (User user : allUsers) {
            if(user == null) continue;
            writer.write("Пользователь " + user.getId() + "\n"
                    + "Логин -  " + user.getLogin() + "\n"
                    + "Пароль - " + user.getPassword() + "\n"
                    + "Имя - " + user.getName() + "\n"
                    + "Возраст - " + user.getAge() + "\n"
                    + "Адрес - " + user.getAddress().getStreet() + "\n\n");
        }
    }

    @Override
    public void findAllUsersByAge() {
        writer.write("\nВведите возраст пользователя, которого хотите найти: ");
        int age = reader.readInt();
        if (UserValidator.invalidAge(age)) {
            writer.write("\nНекорректный возраст (м.б. < 0) !\n");
            return;
        }
        if (userService.existUserByAge(age)) {
            User[] allUsers = userService.findAllUsersByAge(age);
            System.out.println();
            for (User user : allUsers) {
                writer.write("Пользователь " + user.getId() + "\n"
                        + "Логин -  " + user.getLogin() + "\n"
                        + "Пароль - " + user.getPassword() + "\n"
                        + "Имя - " + user.getName() + "\n"
                        + "Возраст - " + user.getAge() + "\n"
                        + "Адрес - " + user.getAddress().getStreet() + "\n\n");
            }
        } else {
            writer.write("\nВы ввели возраст, пользователей с которым нет\n");
        }
    }

    @Override
    public void findUserByLogin() {
        writer.write("\nВведите логин пользователя, которого хотите найти: ");
        String login = reader.readString();
        if (UserValidator.invalidLogin(login)) {
            writer.write("\nНекорректный логин (м.б. слишком короткий) !\n");
            return;
        }
        if (userService.existUserByLogin(login)) {
            User user = userService.findUserByLogin(login);
            writer.write("\nПользователь " + user.getId() + "\n"
                    + "Логин -  " + user.getLogin() + "\n"
                    + "Пароль - " + user.getPassword() + "\n"
                    + "Имя - " + user.getName() + "\n"
                    + "Возраст - " + user.getAge() + "\n"
                    + "Адрес - " + user.getAddress().getStreet() + "\n");
        } else {
            writer.write("\nВы ввели логин, пользователей с которым нет\n");
        }
    }

    @Override
    public void findAllUsersByName() {
        writer.write("\nВведите имя пользователя, которого хотите найти: ");
        String name = reader.readString();
        if (UserValidator.invalidName(name)) {
            writer.write("\nНекорректное имя (м.б. слишком короткое) !\n");
            return;
        }
        if (userService.existUserByName(name)) {
            User[] allUsers = userService.findAllUsersByName(name);
            System.out.println();
            for (User user : allUsers) {
                writer.write("Пользователь " + user.getId() + "\n"
                        + "Логин -  " + user.getLogin() + "\n"
                        + "Пароль - " + user.getPassword() + "\n"
                        + "Имя - " + user.getName() + "\n"
                        + "Возраст - " + user.getAge() + "\n"
                        + "Адрес - " + user.getAddress().getStreet() + "\n\n");
            }
        } else {
            writer.write("\nВы ввели имя, пользователей с которым нет\n");
        }
    }

    @Override
    public void findUserByAddress() {
        writer.write("\nВведите адрес пользователя, которого хотите найти: ");
        String street = reader.readString();
        if (AddressValidator.invalidStreet(street)) {
            writer.write("\nНекорректное название улицы (слишком короткое) !\n");
            return;
        }
        Address address = new Address(street);
        if (userService.existUserByAddress(address)) {
            User user = userService.findUserByAddress(address);
            writer.write("\nПользователь " + user.getId() + "\n"
                    + "Логин -  " + user.getLogin() + "\n"
                    + "Пароль - " + user.getPassword() + "\n"
                    + "Имя - " + user.getName() + "\n"
                    + "Возраст - " + user.getAge() + "\n"
                    + "Адрес - " + user.getAddress().getStreet() + "\n");
        } else {
            writer.write("\nВы ввели адрес, пользователей с которым нет\n");
        }
    }

    @Override
    public void findUserById() {
        writer.write("\nВведите id пользователя, которого хотите найти: ");
        int id = reader.readInt();
        if (UserValidator.invalidId(id)) {
            writer.write("\nНекорректный id (м.б. < 0)!\n");
            return;
        }
        if (userService.existUserById(id)) {
            User user = userService.findUserById(id);
            writer.write("\nПользователь " + user.getId() + "\n"
                    + "Логин -  " + user.getLogin() + "\n"
                    + "Пароль - " + user.getPassword() + "\n"
                    + "Имя - " + user.getName() + "\n"
                    + "Возраст - " + user.getAge() + "\n"
                    + "Адрес - " + user.getAddress().getStreet() + "\n");
        } else {
            writer.write("\nВы ввели id, пользователей с которым нет\n");
        }
    }

    @Override
    public void registration() {
        writer.write("\n---Регистрация---\n");
        addUser();
    }

    @Override
    public void authorization() {
        writer.write("\n---Авторизация---\n\nВведите логин пользователя: ");
        String login = reader.readString();
        if (UserValidator.invalidLogin(login)) {
            writer.write("\nНекорректно введен логин пользователя\n");
            return;
        }
        User user = userService.findUserByLogin(login);
        if (user == null) {
            writer.write("\nПльзователь с логином " + login + " не найден!\n");
            return;
        }
        writer.write("Введите пароль: ");
        String password = reader.readString();
        if (UserValidator.invalidPassword(password)) {
            writer.write("\nНекорректно введен пароль\n");
            return;
        }
        if (user.getPassword().equals(password)) {
            writer.write("\nВы успешно авторизовались\n");
            session = new Session(user);
        } else {
            writer.write("\nПароль не верный!\n");
        }
    }

    @Override
    public void logout() {
        session = null;
    }
}
