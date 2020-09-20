package com.company.repository;

import com.company.entity.Author;
import com.company.entity.Book;

public interface BookRepository {
    boolean saveBook(Book book);

    void deleteBookById(int id);
    void deleteBookByTitle(String title);

    void updateBookTitleById(String newTitle, int id);
    void updateBookDescriptionById(String newDescription, int id);
    void updateBookAuthorById(Author newAuthor, int id);
    void updateBookPriceById(int price, int id);

    Book[] getAllBooks();
    Book[] getAllBooksByAuthor(Author author);
    Book getBookById(int id);
    Book getBookByTitle(String title);
    Book[] getAllBooksByPrice(int price);

    boolean containsBook(Book book);
    boolean containsBookById(int id);
    boolean containsBookByTitle(String title);
    boolean containsBookByPrice(int price);
}
