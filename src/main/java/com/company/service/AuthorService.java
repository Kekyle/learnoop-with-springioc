package com.company.service;

import com.company.entity.Author;

public interface AuthorService {
    boolean addAuthor(Author author);

    void updateAuthorNameById(String newName, int id);

    void removeAuthorById(int id);
    void removeAuthorByName(String name);

    Author[] findAllAuthors();
    Author findAuthorById(int id);
    Author findAuthorByName(String name);

    boolean existAuthorById(int id);
    boolean existAuthorByName(String name);
    boolean existAuthor(Author author);
}
