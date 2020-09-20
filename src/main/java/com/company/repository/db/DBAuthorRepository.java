package com.company.repository.db;

import com.company.entity.Author;
import com.company.repository.AuthorRepository;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Component
public class DBAuthorRepository extends DBAbstractRepository implements AuthorRepository {
    private final String SAVE_AUTHOR = "insert into authors values (default, ?)";
    private final String GET_OLD_NAME = "select * from authors where id=?";
    private final String UPDATE_NAME = "update authors set name=? where id=?";
    private final String GET_ALL_AUTHORS = "select * from authors";
    private final String GET_AUTHOR_BY_ID = "select * from authors where id=?";
    private final String GET_AUTHOR_BY_NAME = "select * from authors where name=?";
    private final String DELETE_AUTHOR_BY_ID = "delete from authors where id=?";
    private final String DELETE_AUTHOR_BY_NAME = "delete from authors where name=?";
    private final String GET_ALL_BY_AUTHORS = "select * from authors where id=?";
    private Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

    public DBAuthorRepository() throws SQLException {
    }

    public static void main(String[] args) throws SQLException {
        DBAuthorRepository dbAuthorRepository = new DBAuthorRepository();
        Author author = new Author(5,"test");
//        dbAuthorRepository.saveAuthor(author);
//        dbAuthorRepository.saveAuthor(new Author("Bulg"));
//        dbAuthorRepository.saveAuthor(new Author("Heming"));
//        String aNew = dbAuthorRepository.updateAuthorName("new", 1);
//        dbAuthorRepository.deleteAuthorById(1);
//        dbAuthorRepository.deleteAuthorByName("Heming");
//        System.out.println(Arrays.toString(dbAuthorRepository.getAllAuthors()));
//        System.out.println(dbAuthorRepository.getAuthorById(3));
//        System.out.println(dbAuthorRepository.getAuthorByName("Heming"));
        System.out.println(dbAuthorRepository.containsAuthorByName("asad"));
        System.out.println(dbAuthorRepository.containsAuthorById(3));
        System.out.println(dbAuthorRepository.containsAuthor(author));
    }
    @Override
    public boolean saveAuthor(Author author) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SAVE_AUTHOR);
            preparedStatement.setString(1, author.getName());
            return preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String updateAuthorName(String newName, int id) {
        try {
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement(GET_OLD_NAME);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String oldName = resultSet.getString(2);

            PreparedStatement preparedStatement1 = connection.prepareStatement(UPDATE_NAME);
            preparedStatement1.setString(1, newName);
            preparedStatement1.setInt(2, id);
            preparedStatement1.executeUpdate();

            connection.commit();
            connection.setAutoCommit(true);

            return oldName;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void deleteAuthorByName(String name) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_AUTHOR_BY_NAME);
            preparedStatement.setString(1, name);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAuthorById(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_AUTHOR_BY_ID);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Author[] getAllAuthors() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_AUTHORS);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Author> list = new ArrayList<>();
            while (resultSet.next()) {
                int authorId = resultSet.getInt(1);
                String name = resultSet.getString(2);
                list.add(new Author(authorId, name));
            }
            return list.toArray(new Author[0]);
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public Author getAuthorByName(String name) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_AUTHOR_BY_NAME);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                return new Author(resultSet.getInt(1), resultSet.getString(2));
            }
        } catch (SQLException e) {
            return null;
        }
        return null;
    }

    @Override
    public Author getAuthorById(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_AUTHOR_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                return new Author(resultSet.getInt(1), resultSet.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean containsAuthor(Author author) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_BY_AUTHORS);
            preparedStatement.setInt(1, author.getId());
            return preparedStatement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean containsAuthorById(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_AUTHOR_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean containsAuthorByName(String name) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_AUTHOR_BY_NAME);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
