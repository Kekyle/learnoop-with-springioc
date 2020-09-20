package com.company.repository.inmemory;

import com.company.entity.Author;
import com.company.repository.AuthorRepository;

import java.util.Arrays;

public class InMemoryAuthorRepository implements AuthorRepository {
    private static Author[] authors = new Author[10];

    public static void main(String[] args) {
        AuthorRepository authorRepository = new InMemoryAuthorRepository();
        System.out.println(Arrays.toString(authorRepository.getAllAuthors()));
    }

    @Override
    public boolean saveAuthor(Author author) {
        for (int i = 0; i < authors.length; i++) {
            if (authors[i] == null) {
                authors[i] = author;
                return true;
            }
        }
        return false;
    }

    @Override
    public String updateAuthorName(String newName, int id) {
        for (int i = 0; i < authors.length; i++) {
            if (authors[i] == null) break;
            if (authors[i].getId() == id) {
                authors[i].setName(newName);
                break;
            }
        }
        return null;
    }

    @Override
    public void deleteAuthorByName(String name) {
        for (int i = 0; i < authors.length; i++) {
            if (authors[i] == null) break;
            if (authors[i].getName().equals(name)) {
                for (int j = i; j < authors.length - 1; j++) {
                    authors[j] = authors[j + 1];
                }
                break;
            }
        }
    }

    @Override
    public void deleteAuthorById(int id) {
        for (int i = 0; i < authors.length; i++) {
            if (authors[i] == null) break;
            if (authors[i].getId() == id) {
                for (int j = i; j < authors.length - 1; j++) {
                    authors[j] = authors[j + 1];
                }
                break;
            }
        }
    }

    @Override
    public Author[] getAllAuthors() {
        int count = 0;
        for (int i = 0; i < authors.length; i++) {
            if (authors[i] == null) {
                count = i;
                break;
            }
        }
        Author[] newAuthors = new Author[count];
        for (int i = 0; i < newAuthors.length; i++) {
            newAuthors[i] = authors[i];
        }
        return newAuthors;
    }

    @Override
    public Author getAuthorByName(String name) {
        for (int i = 0; i < authors.length; i++) {
            if (authors[i] == null) break;
            if (authors[i].getName().equals(name)) {
                return authors[i];
            }
        }
        return null;
    }

    @Override
    public Author getAuthorById(int id) {
        for (int i = 0; i < authors.length; i++) {
            if (authors[i] == null) break;
            if (authors[i].getId() == id) {
                return authors[i];
            }
        }
        return null;
    }

    @Override
    public boolean containsAuthor(Author author) {
        for (int i = 0; i < authors.length; i++) {
            if (authors[i] == null) break;
            if (authors[i].equals(author)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAuthorById(int id) {
        for (int i = 0; i < authors.length; i++) {
            if (authors[i] == null) break;
            if (authors[i].getId() == id) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAuthorByName(String name) {
        for (int i = 0; i < authors.length; i++) {
            if (authors[i] == null) break;
            if (authors[i].getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
}
