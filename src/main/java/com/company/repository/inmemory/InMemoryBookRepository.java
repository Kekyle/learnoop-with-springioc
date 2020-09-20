package com.company.repository.inmemory;

import com.company.entity.Author;
import com.company.entity.Book;
import com.company.repository.BookRepository;

public class InMemoryBookRepository implements BookRepository {
    private static Book[] books = new Book[10];

    @Override
    public boolean saveBook(Book book) {
        if (book == null) throw new NullPointerException();
        for (int i = 0; i < books.length; i++) {
            if (books[i] == null) {
                books[i] = book;
                return true;
            }
        }
        return false;
    }

    @Override
    public void deleteBookById(int id) {
        for (int i = 0; i < books.length; i++) {
            if (books[i] == null) break;
            if (books[i].getId() == id) {
                for (int j = i; j < books.length - 1; j++) {
                    books[j] = books[j + 1];
                }
                break;
            }
        }
    }

    @Override
    public void deleteBookByTitle(String title) {
        for (int i = 0; i < books.length; i++) {
            if (books[i] == null) break;
            if (books[i].getTitle().equals(title)) {
                for (int j = i; j < books.length - 1; j++) {
                    books[j] = books[j + 1];
                }
                break;
            }
        }
    }

    @Override
    public void updateBookTitleById(String newTitle, int id) {
        for (int i = 0; i < books.length; i++) {
            if (books[i] == null) break;
            if (books[i].getId() == id) {
                books[i].setTitle(newTitle);
                break;
            }
        }
    }

    @Override
    public void updateBookDescriptionById(String newDescription, int id) {
        for (int i = 0; i < books.length; i++) {
            if (books[i] == null) break;
            if (books[i].getId() == id) {
                books[i].setDescription(newDescription);
                break;
            }
        }
    }

    @Override
    public void updateBookAuthorById(Author newAuthor, int id) {
        for (int i = 0; i < books.length; i++) {
            if (books[i] == null) break;
            if (books[i].getId() == id) {
                books[i].setAuthor(newAuthor);
                break;
            }
        }
    }

    @Override
    public void updateBookPriceById(int price, int id) {
        for (int i = 0; i < books.length; i++) {
            if (books[i] == null) break;
            if (books[i].getId() == id) {
                books[i].setPrice(price);
                break;
            }
        }
    }

    @Override
    public Book[] getAllBooks() {
        int count = 0;
        for (int i = 0; i < books.length; i++) {
            if (books[i] != null) {
                count++;
            }
        }
        Book[] newBooks = new Book[count];
        for (int i = 0; i < newBooks.length; i++) {
            if (books[i] == null) break;
            newBooks[i] = books[i];
        }
        return newBooks;
    }

    @Override
    public Book[] getAllBooksByAuthor(Author author) {
        int count = 0;
        for (int i = 0; i < books.length; i++) {
            if (books[i] == null) break;
            if (books[i].getAuthor().equals(author)) {
                count++;
            }
        }
        Book[] newBooks = new Book[count];
        for (int i = 0, j = 0; i < books.length; i++) {
            if (books[i] == null) break;
            if (books[i].getAuthor().equals(author)) {
                newBooks[j++] = books[i];
            }
        }
        return newBooks;
    }

    @Override
    public Book getBookById(int id) {
        for (int i = 0; i < books.length; i++) {
            if (books[i] == null) break;
            if (books[i].getId() == id) {
                return books[i];
            }
        }
        return null;
    }

    @Override
    public Book getBookByTitle(String title) {
        for (int i = 0; i < books.length; i++) {
            if (books[i] == null) break;
            if (books[i].getTitle().equals(title)) {
                return books[i];
            }
        }
        return null;
    }

    @Override
    public Book[] getAllBooksByPrice(int price) {
        int count = 0;
        for (int i = 0; i < books.length; i++) {
            if (books[i] == null) break;
            if (books[i].getPrice() == price) {
                count++;
            }
        }
        Book[] newBooks = new Book[count];
        for (int i = 0, j = 0; i < books.length; i++) {
            if (books[i] == null) break;
            if (books[i].getPrice() == price) {
                newBooks[j++] = books[i];
            }
        }
        return newBooks;
    }

    @Override
    public boolean containsBook(Book book) {
        for (int i = 0; i < books.length; i++) {
            if (books[i] == null) break;
            if (books[i].equals(book)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsBookById(int id) {
        for (int i = 0; i < books.length; i++) {
            if (books[i] == null) break;
            if (books[i].getId() == id) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsBookByTitle(String title) {
        for (int i = 0; i < books.length; i++) {
            if (books[i] == null) break;
            if (books[i].getTitle().equals(title)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsBookByPrice(int price) {
        for (int i = 0; i < books.length; i++) {
            if (books[i] == null) break;
            if (books[i].getPrice() == price){
                return true;
            }
        }
        return false;
    }
}
