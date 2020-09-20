package com.company.consoleapp.action;

import com.company.consoleapp.util.ConsoleReader;
import com.company.consoleapp.util.ConsoleWriter;
import com.company.consoleapp.util.Reader;
import com.company.consoleapp.util.Writer;
import com.company.consoleapp.validator.AuthorValidator;
import com.company.entity.Author;
import com.company.service.AuthorService;
import com.company.service.AuthorServiceImpl;
import org.springframework.stereotype.Component;

@Component
public class AuthorActionImpl implements AuthorAction {
    private Writer writer;
    private Reader reader;
    private AuthorService authorService;

    @Override
    public void addAuthor() {
        writer.write("\nВведите имя автора: ");
        String name = reader.readString();
        if (AuthorValidator.invalidName(name)) {
            writer.write("\nНекорректное имя автора (слишком короткое) !\n");
            return;
        }
        Author author = new Author(name);
        authorService.addAuthor(author);
    }

    @Override
    public void removeAuthorById() {
        writer.write("\nВведите id автора для удаления: ");
        int id = reader.readInt();
        if (AuthorValidator.invalidId(id)) {
            writer.write("\nНекорректный id автора (м.б. < 0)!\n");
            return;
        }
        if (authorService.existAuthorById(id)) {
            authorService.removeAuthorById(id);
            writer.write("\nАвтор был успешно удален !\n");
        } else {
            writer.write("\nВы ввели id, с которым что-то не то (такого id вообще нет или м.б. < 0)\n");
        }
    }

    @Override
    public void removeAuthorByName() {
        writer.write("\nВведите имя автора, которого хотите удалить: ");
        String name = reader.readString();
        if (AuthorValidator.invalidName(name)) {
            writer.write("\nНекорректное имя автора (слишком короткое) !\n");
            return;
        }
        if (authorService.existAuthorByName(name)) {
            authorService.removeAuthorByName(name);
            writer.write("\nАвтор был успешно удален !\n");
        } else {
            writer.write("\nВы ввели имя автора, которого у нас нет\n");
        }
    }

    @Override
    public void updateAuthorNameById() {
        writer.write("\nВведите id автора, у которого хотите изменить имя: ");
        int id = reader.readInt();
        if (AuthorValidator.invalidId(id)) {
            writer.write("\nНекорректный id автора (м.б. < 0)!\n");
            return;
        }
        if (authorService.existAuthorById(id)) {
            writer.write("\nВведите новое имя автора: ");
            String newName = reader.readString();
            if (AuthorValidator.invalidName(newName)) {
                writer.write("\nНекорректное имя автора (слишком короткое) !\n");
                return;
            }
            authorService.updateAuthorNameById(newName, id);
        } else {
            writer.write("\nВы ввели id, с которым что-то не то (такого id вообще нет или м.б. < 0)\n");
        }
    }

    @Override
    public void findAllAuthors() {
        Author[] all = authorService.findAllAuthors();
        System.out.println();
        for (Author author : all) {
            if(author == null) continue;
            writer.write("Автор " + author.getId() + " - \"" + author.getName() + "\"\n");
        }
    }

    @Override
    public void findAuthorById() {
        writer.write("\nВведите id автора, которого хотите найти: ");
        int id = reader.readInt();
        if (AuthorValidator.invalidId(id)) {
            writer.write("\nНекорректный id автора (м.б. < 0)!\n");
            return;
        }
        if (authorService.existAuthorById(id)) {
            Author authorById = authorService.findAuthorById(id);
            writer.write("\nАвтор с id = " + id + " : " + authorById.getName() + "\n");
        } else {
            writer.write("\nВы ввели id, с которым что-то не то (такого id вообще нет или м.б. < 0)\n");
        }
    }

    @Override
    public void findAuthorByName() {
        writer.write("\nВведите имя автора, которого хотите найти: ");
        String name = reader.readString();
        if (AuthorValidator.invalidName(name)) {
            writer.write("\nНекорректное имя автора (слишком короткое) !\n");
            return;
        }
        if (authorService.existAuthorByName(name)) {
            Author authorByName = authorService.findAuthorByName(name);
            writer.write("\nАвтор с именем - \" " + name + " \" : " + authorByName.getName() + "\n");
        } else {
            writer.write("\nВы ввели имя автора, которого у нас нет\n");
        }
    }
}
