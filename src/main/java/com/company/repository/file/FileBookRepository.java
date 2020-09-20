package com.company.repository.file;

import com.company.entity.Author;
import com.company.entity.Book;
import com.company.repository.BookRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileBookRepository extends FileAbstractRepository implements BookRepository {
    private static final int TITLE = 1;
    private static final int DESCRIPTION = 2;
    private static final int AUTHOR_ID = 3;
    private static final int PRICE = 4;

    public FileBookRepository(File file) {
        super(file);
    }

    @Override
    public boolean saveBook(Book book) {
        try {
            FileWriter fileWriter = new FileWriter(file, true);
            fileWriter.append("\n");
            int newId = getLastId() + 1;
            book.setId(newId);
            fileWriter.write(book.getId() + SPLITTER
                    + book.getTitle() + SPLITTER
                    + book.getDescription() + SPLITTER
                    + book.getAuthor().getId() + SPLITTER
                    + book.getPrice());
            fileWriter.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

//    private int getLastId() {
//        try {
//            FileReader fileReader = new FileReader(file);
//            BufferedReader bufferedReader = new BufferedReader(fileReader);
//            String line = null;
//            while (true) {
//                String s = bufferedReader.readLine();
//                if (s == null) {
//                    return Integer.parseInt(line.split(SPLITTER)[0]);
//                }
//                line = s;
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return 0;
//    }

    private void writeAll(List<Book> listStore, List<String> comments) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            int count = 0;
            int nullCount = 0;
            for (Book book : listStore) {
                if (book == null) {
                    bufferedWriter.write(comments.get(nullCount++));
                } else {
                    bufferedWriter.write(book.getId() + SPLITTER
                            + book.getTitle() + SPLITTER
                            + book.getDescription() + SPLITTER
                            + book.getAuthor().getId() + SPLITTER
                            + book.getPrice());
                }
                if (count != listStore.size() - 1) {
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

    private void addFromFileToBookList(List<Book> list, List<String> comments) {
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                String[] split = s.split(SPLITTER);
                if (split[ID].equals("#")) {
                    list.add(null);
                    comments.add(s);
                    continue;
                }
                Book book = new Book(Integer.parseInt(split[ID]),
                        split[TITLE],
                        split[DESCRIPTION],
                        new Author(Integer.parseInt(split[AUTHOR_ID]), null),
                        Integer.parseInt(split[PRICE]));
                list.add(book);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteBookById(int id) {
        List<Book> list = new ArrayList<>();
        List<String> listComment = new ArrayList<>();
        addFromFileToBookList(list, listComment);
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
    public void deleteBookByTitle(String title) {
        List<Book> list = new ArrayList<>();
        List<String> listComment = new ArrayList<>();
        addFromFileToBookList(list, listComment);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == null) continue;
            if (list.get(i).getTitle().equals(title)) {
                list.remove(i);
                writeAll(list, listComment);
                break;
            }
        }
    }

    @Override
    public void updateBookTitleById(String newTitle, int id) {
        List<Book> list = new ArrayList<>();
        List<String> listComment = new ArrayList<>();
        addFromFileToBookList(list, listComment);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == null) continue;
            if (list.get(i).getId() == id) {
                list.get(i).setTitle(newTitle);
                writeAll(list, listComment);
            }
        }
    }

    @Override
    public void updateBookDescriptionById(String newDescription, int id) {
        List<Book> list = new ArrayList<>();
        List<String> listComment = new ArrayList<>();
        addFromFileToBookList(list, listComment);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == null) continue;
            if (list.get(i).getId() == id) {
                list.get(i).setDescription(newDescription);
                writeAll(list, listComment);
            }
        }
    }

    @Override
    public void updateBookAuthorById(Author newAuthor, int id) {
        List<Book> list = new ArrayList<>();
        List<String> listComment = new ArrayList<>();
        addFromFileToBookList(list, listComment);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == null) continue;
            if (list.get(i).getId() == id) {
                list.get(i).setAuthor(newAuthor);
                writeAll(list, listComment);
            }
        }
    }

    @Override
    public void updateBookPriceById(int price, int id) {
        List<Book> list = new ArrayList<>();
        List<String> listComment = new ArrayList<>();
        addFromFileToBookList(list, listComment);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == null) continue;
            if (list.get(i).getId() == id) {
                list.get(i).setPrice(price);
                writeAll(list, listComment);
            }
        }
    }

    @Override
    public Book[] getAllBooks() {
        List<Book> list = new ArrayList<>();
        List<String> listComment = new ArrayList<>();
        addFromFileToBookList(list, listComment);
        return list.toArray(new Book[0]);
    }

    @Override
    public Book[] getAllBooksByAuthor(Author author) {
        List<Book> list = new ArrayList<>();
        List<String> comments = new ArrayList<>();
        addFromFileToBookList(list, comments);
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                String[] split = s.split(SPLITTER);
                if (split[ID].equals("#")) {
                    list.add(null);
                    comments.add(s);
                    continue;
                }
                if (Integer.parseInt(split[AUTHOR_ID]) == author.getId()) {
                    Book book = new Book(Integer.parseInt(split[ID]),
                            split[TITLE],
                            split[DESCRIPTION],
                            new Author(Integer.parseInt(split[AUTHOR_ID]), null),
                            Integer.parseInt(split[PRICE]));
                    list.add(book);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list.toArray(new Book[0]);
    }

    @Override
    public Book getBookById(int id) {
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                String[] split = s.split(SPLITTER);
                if (split[ID].equals("#")) continue;
                if (Integer.parseInt(split[ID]) == id) {
                    return new Book(Integer.parseInt(split[ID]),
                            split[TITLE],
                            split[DESCRIPTION],
                            new Author(Integer.parseInt(split[AUTHOR_ID]), null),
                            Integer.parseInt(split[PRICE]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Book getBookByTitle(String title) {
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String s;
            while ((s = bufferedReader.readLine()) != null){
                String[] split = s.split(SPLITTER);
                if (split[ID].equals("#")) continue;
                if (split[TITLE].equals(title)) {
                    return new Book(Integer.parseInt(split[ID]),
                            split[TITLE],
                            split[DESCRIPTION],
                            new Author(Integer.parseInt(split[AUTHOR_ID]), null),
                            Integer.parseInt(split[PRICE]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Book[] getAllBooksByPrice(int price) {
        List<Book> list = new ArrayList<>();
        List<String> comments = new ArrayList<>();
        addFromFileToBookList(list, comments);
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                String[] split = s.split(SPLITTER);
                if (split[ID].equals("#")) {
                    list.add(null);
                    comments.add(s);
                    continue;
                }
                if (Integer.parseInt(split[PRICE]) == price) {
                    Book book = new Book(Integer.parseInt(split[ID]),
                            split[TITLE], split[DESCRIPTION],
                            new Author(Integer.parseInt(split[AUTHOR_ID]), null),
                            Integer.parseInt(split[PRICE]));
                    list.add(book);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list.toArray(new Book[0]);
    }

    @Override
    public boolean containsBook(Book book) {
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                String[] split = s.split(SPLITTER);
                if (split[ID].equals("#")) continue;
                if (split[TITLE].equals(book.getTitle()) &&
                        split[DESCRIPTION].equals(book.getDescription()) &&
                        Integer.parseInt(split[AUTHOR_ID]) == book.getAuthor().getId() &&
                        Integer.parseInt(split[PRICE]) == book.getPrice()) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean containsBookById(int id) {
        return super.containsByIdOrPrice(id, ID);
//        try {
//            FileReader fileReader = new FileReader(file);
//            BufferedReader bufferedReader = new BufferedReader(fileReader);
//            String s;
//            while ((s = bufferedReader.readLine()) != null) {
//                String[] split = s.split(SPLITTER);
//                if (split[ID].equals("#")) continue;
//                if (Integer.parseInt(split[ID]) == id) {
//                    return true;
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return false;
    }

    @Override
    public boolean containsBookByTitle(String title) {
        return super.containsByName(title, TITLE);
//        try {
//            FileReader fileReader = new FileReader(file);
//            BufferedReader bufferedReader = new BufferedReader(fileReader);
//            String s;
//            while ((s = bufferedReader.readLine()) != null) {
//                String[] split = s.split(SPLITTER);
//                if (split[ID].equals("#")) continue;
//                if (split[TITLE].equals(title)) {
//                    return true;
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return false;
    }

    @Override
    public boolean containsBookByPrice(int price) {
        return super.containsByIdOrPrice(price, PRICE);
//        try {
//            FileReader fileReader = new FileReader(file);
//            BufferedReader bufferedReader = new BufferedReader(fileReader);
//            String s;
//            while ((s = bufferedReader.readLine()) != null) {
//                String[] split = s.split(SPLITTER);
//                if (split[ID].equals("#")) continue;
//                if (Integer.parseInt(split[PRICE]) == price) {
//                    return true;
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return false;
    }
}
