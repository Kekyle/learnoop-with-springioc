package com.company.service;

import com.company.entity.Author;
import com.company.entity.Book;
import com.company.repository.AuthorRepository;
import com.company.repository.inmemory.InMemoryAuthorRepository;
import org.springframework.stereotype.Component;

import java.util.Arrays;
@Component
public class AuthorServiceImpl implements AuthorService {
    private AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public boolean addAuthor(Author author) {
        if (authorRepository.containsAuthor(author)) {
            return false;
        }
        return authorRepository.saveAuthor(author);
    }

    @Override
    public void updateAuthorNameById(String newName, int id) {
        if (authorRepository.containsAuthorById(id)) {
            authorRepository.updateAuthorName(newName, id);
        }
    }

    @Override
    public void removeAuthorById(int id) {
        if (authorRepository.containsAuthorById(id)) {
            authorRepository.deleteAuthorById(id);
        }
    }

    @Override
    public void removeAuthorByName(String name) {
        if (authorRepository.containsAuthorByName(name)) {
            authorRepository.deleteAuthorByName(name);
        }
    }

    @Override
    public Author[] findAllAuthors() {
//        Author[] all = authorRepository.getAll();
        return authorRepository.getAllAuthors();
    }

    @Override
    public Author findAuthorById(int id) {
        if (authorRepository.containsAuthorById(id)) {
            return authorRepository.getAuthorById(id);
        }
        return null;
    }

    @Override
    public Author findAuthorByName(String name) {
        if (authorRepository.containsAuthorByName(name)) {
            return authorRepository.getAuthorByName(name);
        }
        return null;
    }

    @Override
    public boolean existAuthorById(int id) {
        return authorRepository.containsAuthorById(id);
    }

    @Override
    public boolean existAuthorByName(String name) {
        return authorRepository.containsAuthorByName(name);
    }

    @Override
    public boolean existAuthor(Author author) {
        return authorRepository.containsAuthor(author);
    }
}
