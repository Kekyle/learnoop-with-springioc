package com.company.repository.db;

import com.company.entity.Author;
import com.company.entity.Book;
import com.company.repository.BookRepository;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Component
public class DBBookRepository extends DBAbstractRepository implements BookRepository {


    private final String SAVE_BOOK = "insert into books values(default, ?, ?, ?, ?)";
    private final String DELETE_BOOK_BY_ID = "delete from books where id=?";
    private final String DELETE_BOOK_BY_TITLE = "delete from books where title=?";
    private final String GET_OLD_TITLE = "select * from books where id=?";
    private final String UPDATE_TITLE_BY_ID = "update books set title=? where id=?";
    private final String GET_OLD_DESCRIPTION = "select * from books where id=?";
    private final String GET_OLD_AUTHOR = "select * from books where id=?";
    private final String GET_OLD_PRICE = "select * from books where id=?";
    private final String UPDATE_AUTHOR_BY_ID = "update books set author_id=? where id=?";
    private final String UPDATE_PRICE_BY_ID = "update books set price=? where id=?";
    private final String GET_ALL_BOOKS = "select b.id, title, description, a.name, price from books b join authors a on b.author_id = a.id";
    private final String GET_ALL_BY_AUTHOR = "select b.id, title, description, a.name, price from books b join authors a on b.author_id = a.id where a.name=?";
    private final String GET_BOOK_BY_ID = "select * from books where id=?";
    private final String GET_AUTHOR = "select a.id, a.name from books b join authors a on b.author_id=a.id";
    private final String GET_BOOK_BY_TITLE = "select * from books where title=?";
    private final String GET_ALL_BOOKS_BY_PRICE = "select * from books where price=?";
    private final String GET_ALL_FROM_AUTHORS_BY_NAME = "select * from authors where authors.name=?";

    public DBBookRepository() throws SQLException {
    }

    public static void main(String[] args) throws SQLException {
        DBBookRepository dbBookRepository = new DBBookRepository();
//        dbBookRepository.saveBook(new Book("new", "new", new Author(5, "test"), 99));
//        dbBookRepository.saveBook(new Book("bbb", "bbb", new Author(4, "Heming"), 33));
//        System.out.println(Arrays.toString(dbBookRepository.getAllBooks()));
//        System.out.println(dbBookRepository.getBookById(1));
//        System.out.println(Arrays.toString(dbBookRepository.getAllBooksByAuthor(new Author("Bulg"))));
//        System.out.println(Arrays.toString(dbBookRepository.getAllBooksByPrice(22)));
//        System.out.println(dbBookRepository.getBookByTitle("ccc"));
//        dbBookRepository.updateBookTitleById("neeeeeew", 9);
//        dbBookRepository.updateBookDescriptionById("neeeeew", 9);
//        dbBookRepository.updateBookPriceById(999, 9);
//        dbBookRepository.updateBookAuthorById(new Author("Orwell"), 9);
//        dbBookRepository.deleteBookById(10);
//        dbBookRepository.deleteBookByTitle("neeeeeew");
//        System.out.println(dbBookRepository.containsBookById(5));
//        System.out.println(dbBookRepository.containsBookByPrice(22));
//        System.out.println(dbBookRepository.containsBookByTitle("bbb"));
//        System.out.println(dbBookRepository.containsBook(new Book("new", "aaa", new Author( "Bulg"), 22)));
    }

    @Override
    public boolean saveBook(Book book) {
        int authorId = book.getAuthor().getId();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SAVE_BOOK);
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getDescription());
            preparedStatement.setInt(3, authorId);
            preparedStatement.setInt(4, book.getPrice());
            return preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void deleteBookById(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BOOK_BY_ID);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteBookByTitle(String title) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BOOK_BY_TITLE);
            preparedStatement.setString(1, title);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateBookTitleById(String newTitle, int id) {
        try {
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement(GET_OLD_TITLE);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String oldTitle = resultSet.getString(2);

            PreparedStatement preparedStatement1 = connection.prepareStatement(UPDATE_TITLE_BY_ID);
            preparedStatement1.setString(1, newTitle);
            preparedStatement1.setInt(2, id);
            preparedStatement1.executeUpdate();

            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateBookDescriptionById(String newDescription, int id) {
        try {
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement(GET_OLD_DESCRIPTION);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String oldDescription = resultSet.getString(3);

            PreparedStatement preparedStatement1 = connection.prepareStatement("update books set description=? where id=?");
            preparedStatement1.setString(1, newDescription);
            preparedStatement1.setInt(2, id);
            preparedStatement1.executeUpdate();

            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateBookAuthorById(Author newAuthor, int id) {
        try {
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement(GET_OLD_AUTHOR);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int authorId = resultSet.getInt(4);

            PreparedStatement preparedStatement2 = connection.prepareStatement(GET_ALL_FROM_AUTHORS_BY_NAME);
            preparedStatement2.setString(1, newAuthor.getName());
            ResultSet resultSet1 = preparedStatement2.executeQuery();
            resultSet1.next();
            int newAuthorId = resultSet1.getInt(1);
            PreparedStatement preparedStatement1 = connection.prepareStatement(UPDATE_AUTHOR_BY_ID);
            preparedStatement1.setInt(1, newAuthorId);
            preparedStatement1.setInt(2, id);
            preparedStatement1.executeUpdate();

            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateBookPriceById(int price, int id) {
        try {
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement(GET_OLD_PRICE);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int oldPrice = resultSet.getInt(5);

            PreparedStatement preparedStatement1 = connection.prepareStatement(UPDATE_PRICE_BY_ID);
            preparedStatement1.setInt(1, price);
            preparedStatement1.setInt(2, id);
            preparedStatement1.executeUpdate();

            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Book[] getAllBooks() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_BOOKS);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Book> list = getAllBooks(resultSet);
            return list.toArray(new Book[0]);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Book[] getAllBooksByAuthor(Author author) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_BY_AUTHOR);
            preparedStatement.setString(1, author.getName());
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Book> allBooks = getAllBooks(resultSet);
            return allBooks.toArray(new Book[0]);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Book> getAllBooks(ResultSet resultSet) throws SQLException {
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
        return list;
    }

    @Override
    public Book getBookById(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_BOOK_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            PreparedStatement preparedStatement1 = connection.prepareStatement(GET_AUTHOR);
            ResultSet resultSet1 = preparedStatement1.executeQuery();
            resultSet1.next();
            int authorId = resultSet1.getInt(1);
            String authorName = resultSet1.getString(2);
            Author author = new Author(authorId, authorName);
            resultSet.next();
            return new Book(resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    author,
                    resultSet.getInt(5));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Book getBookByTitle(String title) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_BOOK_BY_TITLE);
            preparedStatement.setString(1, title);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int bookId = resultSet.getInt(1);
            String bookTitle = resultSet.getString(2);
            String bookDescription = resultSet.getString(3);
            int bookPrice = resultSet.getInt(5);
            PreparedStatement preparedStatement1 = connection.prepareStatement(GET_AUTHOR);
            ResultSet resultSet1 = preparedStatement1.executeQuery();
            resultSet1.next();
            int authorId = resultSet1.getInt(1);
            String authorName = resultSet1.getString(2);
            Author author = new Author(authorId, authorName);
            return new Book(bookId,
                    bookTitle,
                    bookDescription,
                    author,
                    bookPrice);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Book[] getAllBooksByPrice(int price) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_BOOKS_BY_PRICE);
            preparedStatement.setInt(1, price);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Book> list = getAllBooks(resultSet);
            return list.toArray(new Book[0]);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean containsBook(Book book) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_BOOK_BY_TITLE);
            preparedStatement.setString(1, book.getTitle());
            return preparedStatement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean containsBookById(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_BOOK_BY_ID);
            preparedStatement.setInt(1, id);
            return preparedStatement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean containsBookByTitle(String title) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_BOOK_BY_TITLE);
            preparedStatement.setString(1, title);
            return preparedStatement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean containsBookByPrice(int price) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_BOOKS_BY_PRICE);
            preparedStatement.setInt(1, price);
            return preparedStatement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
