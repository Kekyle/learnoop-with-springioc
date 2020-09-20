package com.company.entity;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class Book implements Comparable<Book>{
    private static int incId = 1;
    private int id = incId++;

    private String title;
    private String description;
    private Author author;
    private int price;

    public Book(String title, String description, Author author, int price) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.price = price;
    }

    public Book(int id, String title, String description, Author author, int price) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.author = author;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return price == book.price &&
                Objects.equals(title, book.title) &&
                Objects.equals(description, book.description) &&
                Objects.equals(author, book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, author, price);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", author=" + author +
                ", price=" + price +
                '}';
    }

    @Override
    public int compareTo(Book o) {
        return title.compareTo(o.title);
    }
}
