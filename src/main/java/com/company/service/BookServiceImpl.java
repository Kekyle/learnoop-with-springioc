package com.company.service;

import com.company.entity.Author;
import com.company.entity.Book;
import com.company.repository.AuthorRepository;
import com.company.repository.inmemory.InMemoryAuthorRepository;
import com.company.repository.BookRepository;
import com.company.repository.inmemory.InMemoryBookRepository;
import org.springframework.stereotype.Component;

@Component
public class BookServiceImpl implements BookService {
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public boolean addBook(Book book) {
        if (bookRepository.containsBook(book)) {
            return false;
        }
//        book.setTitle(book.getTitle().toUpperCase());
        return bookRepository.saveBook(book);
    }

    @Override
    public void removeBookById(int id) {
        if (bookRepository.containsBookById(id)) {
            bookRepository.deleteBookById(id);
        }
    }

    @Override
    public void removeBookByTitle(String title) {
        if (bookRepository.containsBookByTitle(title)) {
            bookRepository.deleteBookByTitle(title);
        }
    }

    @Override
    public void updateBookTitleById(String newTitle, int id) {
        if (bookRepository.containsBookById(id)) {
            bookRepository.updateBookTitleById(newTitle, id);
        }
    }

    @Override
    public void updateBookDescriptionById(String newDescription, int id) {
        if (bookRepository.containsBookById(id)) {
            bookRepository.updateBookDescriptionById(newDescription, id);
        }
    }

    @Override
    public void updateBookAuthorById(Author newAuthor, int id) {
        if (bookRepository.containsBookById(id)) {
            bookRepository.updateBookAuthorById(newAuthor, id);
        }
    }

    @Override
    public void updateBookPriceById(int price, int id) {
        if (bookRepository.containsBookById(id)) {
            bookRepository.updateBookPriceById(price, id);
        }
    }

    @Override
    public Book[] findAllBooks() {
        return bookRepository.getAllBooks();
    }

    @Override
    public Book[] findAllBooksByAuthor(Author author) {
        if (authorRepository.containsAuthor(author)) {
            return bookRepository.getAllBooksByAuthor(author);
        }
        return null;
    }

    @Override
    public Book findBookById(int id) {
        if (bookRepository.containsBookById(id)) {
            return bookRepository.getBookById(id);
        }
        return null;
    }

    @Override
    public Book findBookByTitle(String title) {
        if (bookRepository.containsBookByTitle(title)) {
            return bookRepository.getBookByTitle(title);
        }
        return null;
    }

    @Override
    public Book[] findAllBooksByPrice(int price) {
        return bookRepository.getAllBooksByPrice(price);
    }

    @Override
    public boolean existBookById(int id) {
        return bookRepository.containsBookById(id);
    }

    @Override
    public boolean existBookByTitle(String title) {
        return bookRepository.containsBookByTitle(title);
    }

    @Override
    public boolean existBookByPrice(int price) {
        return bookRepository.containsBookByPrice(price);
    }

    @Override
    public boolean existBook(Book book) {
        return bookRepository.containsBook(book);
    }
}
