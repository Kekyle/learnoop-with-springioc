package com.company.consoleapp.action;

public interface AuthorAction {
    void addAuthor();

    void removeAuthorById();

    void removeAuthorByName();

    void updateAuthorNameById();

    void findAllAuthors();

    void findAuthorById();

    void findAuthorByName();
}
