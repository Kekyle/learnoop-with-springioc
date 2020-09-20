package com.company.consoleapp.action;

public interface BookAction {
    void addBook();

    void removeBookById();
    void removeBookByTitle();

    void updateBookTitleById();
    void updateBookDescriptionById();
    void updateBookAuthorById();
    void updateBookPriceById();

    void findAllBooks();
    void findAllBooksByAuthor();
    void findBookById();
    void findBookByTitle();
    void findAllBooksByPrice();
    void findAllBooksInBasket();
}
