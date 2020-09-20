package com.company.repository.file;

import com.company.entity.Address;
import com.company.entity.Role;
import com.company.entity.User;
import com.company.repository.UserRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileUserRepository extends FileAbstractRepository implements UserRepository {
    private static final int LOGIN = 1;
    private static final int PASSWORD = 2;
    private static final int AGE = 3;
    private static final int NAME = 4;
    private static final int ADDRESS = 5;
    private static final int ROLE_ID = 6;
    private File role;

    public FileUserRepository(File file, File role) {
        super(file);
        this.role = role;
    }

    public static void main(String[] args) {
        FileUserRepository fileUserRepository = new FileUserRepository(new File("users.txt"), new File("role.txt"));
//        fileUserRepository.saveUser(new User("test4", "test4", 55, "Test4", new Address("aaa"), Role.ADMIN)); // TODO: 25.06.2020
        System.out.println(fileUserRepository.updateUserNameById("New", 4)); // TODO: 25.06.2020
//        System.out.println(fileUserRepository.updateUserAddressById(new Address("new"), 2)); // TODO: 25.06.2020
//        System.out.println(fileUserRepository.updateUserAgeById(22, 2)); // TODO: 25.06.2020
//        System.out.println(fileUserRepository.updateUserLoginById("new", 1)); // TODO: 25.06.2020
//        System.out.println(fileUserRepository.updateUserPasswordById("new", 2)); // TODO: 25.06.2020
//        System.out.println(Arrays.toString(fileUserRepository.getAllUsers()));
//        System.out.println(fileUserRepository.getUserById(2)); // TODO: 25.06.2020
//        fileUserRepository.deleteUserById(1); // TODO: 25.06.2020
//        fileUserRepository.deleteUserByLogin("new");
    }

    @Override
    public boolean saveUser(User user) {
        try {
            FileWriter fileWriter = new FileWriter(file, true);
            fileWriter.append("\n");
            int userRoleId = getUserRoleId(user);
            int newId = getLastId() + 1;
            user.setId(newId);
            fileWriter.write(user.getId() + SPLITTER
                    + user.getLogin() + SPLITTER
                    + user.getPassword() + SPLITTER
                    + user.getAge() + SPLITTER
                    + user.getName() + SPLITTER
                    + user.getAddress().getStreet() + SPLITTER
                    + userRoleId);
            fileWriter.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private int getUserRoleId(User user) {
        int userRoleId;
        Role role = user.getRole();
        if (role == Role.USER) {
            userRoleId = 2;
        } else {
            userRoleId = 1;
        }
        return userRoleId;
    }

    private void writeAll(List<User> list, List<String> comments) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            int count = 0;
            int nullCount = 0;
            for (User user : list) {
                if (user == null) {
                    bufferedWriter.write(comments.get(nullCount++));
                } else {
                    int userRoleId = getUserRoleId(user);
                    bufferedWriter.write(user.getId() + SPLITTER
                            + user.getLogin() + SPLITTER
                            + user.getPassword() + SPLITTER
                            + user.getAge() + SPLITTER
                            + user.getName() + SPLITTER
                            + user.getAddress().getStreet() + SPLITTER
                            + userRoleId);
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

    private void addFromFileToUserList(List<User> list, List<String> comments) {
        try {
            FileReader fileReader = new FileReader(file);
            FileReader roleReader = new FileReader(role);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            BufferedReader roleBuffer = new BufferedReader(roleReader);
            String line;
            while ((line = bufferedReader.readLine()) != null){
                String[] s = line.split(SPLITTER);
                if (s[ID].equals(COMMENT_TAG)) {
                    list.add(null);
                    comments.add(line);
                    continue;
                }
                String roleLine;
                Role role = Role.USER;
                while ((roleLine = roleBuffer.readLine()) != null){
                    String[] s1 = roleLine.split(SPLITTER);
                    String s2 = s1[0];
                    String s3 = s[ROLE_ID];
                    if (s2.equals(s3)){
                        role = Role.valueOf(s1[1]);
                    }
                }
                User user = new User(Integer.parseInt(s[ID]),
                        s[LOGIN],
                        s[PASSWORD],
                        Integer.parseInt(s[AGE]),
                        s[NAME],
                        new Address(s[ADDRESS]),
                        role);
                list.add(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String updateUserLoginById(String login, int id) {
        List<User> list = new ArrayList<>();
        List<String> listComment = new ArrayList<>();
        addFromFileToUserList(list, listComment);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == null) continue;
            if (list.get(i).getId() == id) {
                String oldLogin = list.get(i).getLogin();
                list.get(i).setLogin(login);
                writeAll(list, listComment);
                return oldLogin;
            }
        }
        return null;
    }

    @Override
    public String updateUserPasswordById(String password, int id) {
        List<User> list = new ArrayList<>();
        List<String> listComment = new ArrayList<>();
        addFromFileToUserList(list, listComment);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == null) continue;
            if (list.get(i).getId() == id) {
                String oldPassword = list.get(i).getPassword();
                list.get(i).setPassword(password);
                writeAll(list, listComment);
                return oldPassword;
            }
        }
        return null;
    }

    @Override
    public String updateUserNameById(String name, int id) {
        List<User> list = new ArrayList<>();
        List<String> listComment = new ArrayList<>();
        addFromFileToUserList(list, listComment);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == null) continue;
            if (list.get(i).getId() == id) {
                String oldName = list.get(i).getName();
                list.get(i).setName(name);
                writeAll(list, listComment);
                return oldName;
            }
        }
        return null;
    }

    @Override
    public Address updateUserAddressById(Address address, int id) {
        List<User> list = new ArrayList<>();
        List<String> listComment = new ArrayList<>();
        addFromFileToUserList(list, listComment);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == null) continue;
            if (list.get(i).getId() == id) {
                Address old = list.get(i).getAddress();
                list.get(i).setAddress(address);
                writeAll(list, listComment);
                return old;
            }
        }
        return null;
    }

    @Override
    public int updateUserAgeById(int age, int id) {
        List<User> list = new ArrayList<>();
        List<String> listComment = new ArrayList<>();
        addFromFileToUserList(list, listComment);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == null) continue;
            if (list.get(i).getId() == id) {
                int old = list.get(i).getAge();
                list.get(i).setAge(age);
                writeAll(list, listComment);
                return old;
            }
        }
        return 0;
    }

    @Override
    public void deleteUserById(int id) {
        List<User> list = new ArrayList<>();
        List<String> listComment = new ArrayList<>();
        addFromFileToUserList(list, listComment);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == null) continue;
            if (list.get(i).getId() == id) {
                list.remove(i);
                writeAll(list, listComment);
                break;
            }
        }
    }

    @Override
    public void deleteUserByLogin(String login) {
        List<User> list = new ArrayList<>();
        List<String> listComment = new ArrayList<>();
        addFromFileToUserList(list, listComment);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == null) continue;
            if (list.get(i).getLogin().equals(login)) {
                list.remove(i);
                writeAll(list, listComment);
                break;
            }
        }
    }

    @Override
    public void deleteUser(User user) {
        List<User> list = new ArrayList<>();
        List<String> listComment = new ArrayList<>();
        addFromFileToUserList(list, listComment);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == null) continue;
            User currentUser = list.get(i);
            if (currentUser.equals(user)) {
                list.remove(i);
                writeAll(list, listComment);
                break;
            }
        }
    }

    @Override
    public User[] getAllUsers() {
        List<User> list = new ArrayList<>();
        List<String> listComment = new ArrayList<>();
        addFromFileToUserList(list, listComment);
        return list.toArray(new User[0]);
    }

    @Override
    public User[] getAllUsersByAge(int age) {
        List<User> list = new ArrayList<>();
        List<String> comments = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(file);
            FileReader roleReader = new FileReader(role);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            BufferedReader roleBuffer = new BufferedReader(roleReader);
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                String[] split = s.split(SPLITTER);
                if (split[ID].equals(COMMENT_TAG)) {
                    list.add(null);
                    comments.add(s);
                    continue;
                }
                if (Integer.parseInt(split[AGE]) == age) {
                    String r;
                    Role role = Role.USER;
                    while ((r = roleBuffer.readLine()) != null) {
                        String[] s1 = r.split(SPLITTER);
                        if (Integer.parseInt(s1[ID]) == Integer.parseInt(split[ROLE_ID])) {
                            String s2 = s1[1];
                            role = Role.valueOf(s2);
                        }
                    }
                    User user = new User(
                            Integer.parseInt(split[ID]),
                            split[LOGIN],
                            split[PASSWORD],
                            Integer.parseInt(split[AGE]),
                            split[NAME],
                            new Address(split[ADDRESS]),
                            role);
                    list.add(user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list.toArray(new User[0]);
    }


    @Override
    public User getUserByLogin(String login) {
        try {
            FileReader fileReader = new FileReader(file);
            FileReader roleReader = new FileReader(role);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            BufferedReader roleBuffer = new BufferedReader(roleReader);
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                String[] split = s.split(SPLITTER);
                if (split[ID].equals(COMMENT_TAG)) continue;
                if (split[LOGIN].equals(login)) {
                    String r;
                    Role role = Role.USER;
                    while ((r = roleBuffer.readLine()) != null) {
                        String[] s1 = r.split(SPLITTER);
                        if (Integer.parseInt(s1[ID]) == Integer.parseInt(split[ROLE_ID])) {
                            String s2 = s1[1];
                            role = Role.valueOf(s2);
                        }
                    }
                    return new User(
                            Integer.parseInt(split[ID]),
                            split[LOGIN],
                            split[PASSWORD],
                            Integer.parseInt(split[AGE]),
                            split[NAME],
                            new Address(split[ADDRESS]),
                            role);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User[] getAllUsersByName(String name) {
        List<User> list = new ArrayList<>();
        List<String> comments = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(file);
            FileReader roleReader = new FileReader(role);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            BufferedReader roleBuffer = new BufferedReader(roleReader);
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                String[] split = s.split(SPLITTER);
                if (split[ID].equals(COMMENT_TAG)) {
                    list.add(null);
                    comments.add(s);
                    continue;
                }
                if (split[NAME].equals(name)) {
                    String r;
                    Role role = Role.USER;
                    while ((r = roleBuffer.readLine()) != null) {
                        String[] s1 = r.split(SPLITTER);
                        if (Integer.parseInt(s1[ID]) == Integer.parseInt(split[ROLE_ID])) {
                            String s2 = s1[1];
                            role = Role.valueOf(s2);
                        }
                    }
                    User user = new User(
                            Integer.parseInt(split[ID]),
                            split[LOGIN],
                            split[PASSWORD],
                            Integer.parseInt(split[AGE]),
                            split[NAME],
                            new Address(split[ADDRESS]),
                            role);
                    list.add(user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list.toArray(new User[0]);
    }

    @Override
    public User getUserByAddress(Address address) {
        try {
            FileReader fileReader = new FileReader(file);
            FileReader roleReader = new FileReader(role);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            BufferedReader roleBuffer = new BufferedReader(roleReader);
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                String[] split = s.split(SPLITTER);
                if (split[ID].equals(COMMENT_TAG)) continue;
                if (split[ADDRESS].equals(address.getStreet())) {
                    String r;
                    Role role = Role.USER;
                    while ((r = roleBuffer.readLine()) != null) {
                        String[] s1 = r.split(SPLITTER);
                        if (Integer.parseInt(s1[ID]) == Integer.parseInt(split[ROLE_ID])) {
                            String s2 = s1[1];
                            role = Role.valueOf(s2);
                        }
                    }
                    return new User(
                            Integer.parseInt(split[ID]),
                            split[LOGIN],
                            split[PASSWORD],
                            Integer.parseInt(split[AGE]),
                            split[NAME],
                            new Address(split[ADDRESS]),
                            role);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User getUserById(int id) {
        try {
            FileReader fileReader = new FileReader(file);
            FileReader roleReader = new FileReader(role);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            BufferedReader roleBuffer = new BufferedReader(roleReader);
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                String[] split = s.split(SPLITTER);
                if (split[ID].equals(COMMENT_TAG)) continue;
                if (Integer.parseInt(split[ID]) == id) {
                    String r;
                    Role role = Role.USER;
                    while ((r = roleBuffer.readLine()) != null) {
                        String[] s1 = r.split(SPLITTER);
                        if (Integer.parseInt(s1[ID]) == Integer.parseInt(split[ROLE_ID])) {
                            String s2 = s1[1];
                            role = Role.valueOf(s2);
                        }
                    }
                    return new User(
                            Integer.parseInt(split[ID]),
                            split[LOGIN],
                            split[PASSWORD],
                            Integer.parseInt(split[AGE]),
                            split[NAME],
                            new Address(split[ADDRESS]),
                            role);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean containsUserById(int id) {
        List<User> list = new ArrayList<>();
        List<String> listComment = new ArrayList<>();
        addFromFileToUserList(list, listComment);
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
    public boolean containsUserByLogin(String login) {
        List<User> list = new ArrayList<>();
        List<String> listComment = new ArrayList<>();
        addFromFileToUserList(list, listComment);
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                String[] split = s.split(SPLITTER);
                if (split[LOGIN].equals(login)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean containsUserByName(String name) {
        List<User> list = new ArrayList<>();
        List<String> listComment = new ArrayList<>();
        addFromFileToUserList(list, listComment);
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                String[] split = s.split(SPLITTER);
                if (split[NAME].equals(name)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean containsUserByAge(int age) {
        List<User> list = new ArrayList<>();
        List<String> listComment = new ArrayList<>();
        addFromFileToUserList(list, listComment);
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                String[] split = s.split(SPLITTER);
                if (Integer.parseInt(split[AGE]) == age) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean containsUserByAddress(Address address) {
        List<User> list = new ArrayList<>();
        List<String> listComment = new ArrayList<>();
        addFromFileToUserList(list, listComment);
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                String[] split = s.split(SPLITTER);
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
    public boolean containsByUser(User user) {
        List<User> list = new ArrayList<>();
        List<String> listComment = new ArrayList<>();
        addFromFileToUserList(list, listComment);
        try {
            FileReader fileReader = new FileReader(file);
            FileReader roleReader = new FileReader(role);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            BufferedReader roleBuffer = new BufferedReader(roleReader);
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                String[] split = s.split(SPLITTER);
                if (split[ID].equals(COMMENT_TAG)) continue;
                String r;
                Role role = Role.USER;
                while ((r = roleBuffer.readLine()) != null) {
                    String[] s1 = r.split(SPLITTER);
                    if (Integer.parseInt(s1[ID]) == Integer.parseInt(split[ROLE_ID])) {
                        String s2 = s1[1];
                        role = Role.valueOf(s2);
                    }
                }
                User currentUser = new User(Integer.parseInt(split[ID]),
                        split[LOGIN],
                        split[PASSWORD],
                        Integer.parseInt(split[AGE]),
                        split[NAME],
                        new Address(split[ADDRESS]),
                        role);
                if (currentUser.equals(user)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
