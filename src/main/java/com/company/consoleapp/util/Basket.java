package com.company.consoleapp.util;

import com.company.entity.Book;

import java.util.Arrays;

public class Basket {
    private static int incId = 1;
    private int id = incId++;
    private Book[] books = new Book[10];

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Book[] getBooks() {
        return books;
    }

    public void setBooks(Book[] books) {
        this.books = books;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Basket basket = (Basket) o;
        return Arrays.equals(books, basket.books);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(books);
    }

    @Override
    public String toString() {
        return "Basket{" +
                "id=" + id +
                ", books=" + Arrays.toString(books) +
                '}';
    }
}
