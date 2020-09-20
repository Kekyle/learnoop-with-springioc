package com.company.service;

import com.company.entity.Author;
import com.company.entity.Book;

public interface BookService {
    boolean addBook(Book book);

    void removeBookById(int id);
    void removeBookByTitle(String title);

    void updateBookTitleById(String newTitle, int id);
    void updateBookDescriptionById(String newDescription, int id);
    void updateBookAuthorById(Author newAuthor, int id);
    void updateBookPriceById(int price, int id);

    Book[] findAllBooks();
    Book[] findAllBooksByAuthor(Author author);
    Book findBookById(int id);
    Book findBookByTitle(String title);
    Book[] findAllBooksByPrice(int price);

    boolean existBookById(int id);
    boolean existBookByTitle(String title);
    boolean existBookByPrice(int price);
    boolean existBook(Book book);
}
