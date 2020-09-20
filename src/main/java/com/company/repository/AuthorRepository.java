package com.company.repository;

import com.company.entity.Author;

public interface AuthorRepository {
    boolean saveAuthor(Author author);

    String updateAuthorName(String newName, int id);

    void deleteAuthorByName(String name);

    void deleteAuthorById(int id);

    Author[] getAllAuthors();

    Author getAuthorByName(String name);

    Author getAuthorById(int id);

    boolean containsAuthor(Author author);

    boolean containsAuthorById(int id);

    boolean containsAuthorByName(String name);
}
