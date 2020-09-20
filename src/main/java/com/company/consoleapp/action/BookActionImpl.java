package com.company.consoleapp.action;

import com.company.consoleapp.util.ConsoleReader;
import com.company.consoleapp.util.ConsoleWriter;
import com.company.consoleapp.util.Reader;
import com.company.consoleapp.util.Writer;
import com.company.consoleapp.validator.BookValidator;
import com.company.entity.Author;
import com.company.entity.Book;
import com.company.service.AuthorService;
import com.company.service.AuthorServiceImpl;
import com.company.service.BookService;
import com.company.service.BookServiceImpl;
import org.springframework.stereotype.Component;

@Component
public class BookActionImpl implements BookAction {
    private BookService bookService;
    private Writer writer;
    private Reader reader;
    private AuthorService authorService;

    @Override
    public void addBook() {
        writer.write("\nВведите название книги: ");
        String title = reader.readString();
        if (BookValidator.invalidTitle(title)) {
            writer.write("\nНекорректное название книги (м.б. слишком короткое)!\n");
            return;
        }
        writer.write("Введите описание книги: ");
        String description = reader.readString();
        if (BookValidator.invalidDescription(description)) {
            writer.write("\nНекорректное описание книги (м.б. слишком короткое)!\n");
            return;
        }
        writer.write("Выберите автора из списка: \n");
        Author[] allAuthors = authorService.findAllAuthors();
        for (int i = 0; i < allAuthors.length; i++) {
            writer.write("Автор - " + (i + 1) + ": " + allAuthors[i].getName() + "\n");
        }
        writer.write("Вы выбрали: ");
        int i = reader.readInt() - 1;
        if (i < allAuthors.length && i >= 0) {
            Author author = allAuthors[i];
            writer.write("Введите цену книги: ");
            int price = reader.readInt();
            if (BookValidator.invalidPrice(price)) {
                writer.write("\nНекорректная цена книги (м.б. < 0)!\n");
                return;
            }
            Book book = new Book(title, description, author, price);
            bookService.addBook(book);
        } else {
            writer.write("\nВы выбрали автора, которого нет в списке\n");
        }
    }

    @Override
    public void removeBookById() {
        writer.write("\nВведите id книги для удаления: ");
        int id = reader.readInt();
        if (BookValidator.invalidId(id)) {
            writer.write("\nНекорректный id книги (м.б. < 0)!\n");
            return;
        }
        if (bookService.existBookById(id)) {
            bookService.removeBookById(id);
            writer.write("\nКнига успешно удалена по id!\n");
        } else {
            writer.write("\nВы ввели id, с которым что-то не то (такого id вообще нет или м.б. < 0)\n");
        }
    }

    @Override
    public void removeBookByTitle() {
        writer.write("\nВведите название книги, которую хотите удалить: ");
        String removeTitle = reader.readString();
        if (BookValidator.invalidTitle(removeTitle)) {
            writer.write("\nНекорректное название книги (м.б. слишком короткое)!\n");
            return;
        }
        if (bookService.existBookByTitle(removeTitle)) {
            bookService.removeBookByTitle(removeTitle);
            writer.write("\nКнига успешно удалена по названию!\n");
        } else {
            writer.write("\nНе удалось удалить книгу. Вы ввели название книги, которой у нас нет\n");
        }
    }

    @Override
    public void updateBookTitleById() {
        writer.write("\nВведите id книги, у котрой хотите изменить название: ");
        int id = reader.readInt();
        if (BookValidator.invalidId(id)) {
            writer.write("\nНекорректный id книги (м.б. < 0)!\n");
            return;
        }
        if (bookService.existBookById(id)) {
            writer.write("\nВведите новое название книги: ");
            String newTitle = reader.readString();
            if (BookValidator.invalidTitle(newTitle)) {
                writer.write("\nНекорректное название книги (м.б. слишком короткое)!\n");
                return;
            }
            bookService.updateBookTitleById(newTitle, id);
            writer.write("\nНазвание книги было успешно заменено!\n");
        } else {
            writer.write("\nВы ввели id, с которым что-то не то (м.б. такого id вообще нет или id < 0)\n");
        }
    }

    @Override
    public void updateBookDescriptionById() {
        writer.write("\nВведите id книги, у котрой хотите изменить описание: ");
        int id = reader.readInt();
        if (BookValidator.invalidId(id)) {
            writer.write("\nНекорректный id книги (м.б. < 0)!\n");
            return;
        }
        if (bookService.existBookById(id)) {
            writer.write("\nВведите новое описание книги: ");
            String newDescription = reader.readString();
            if (BookValidator.invalidDescription(newDescription)) {
                writer.write("\nНекорректное описание книги (м.б. слишком короткое)!\n");
                return;
            }
            bookService.updateBookDescriptionById(newDescription, id);
            writer.write("\nОписание книги успешно заменено!\n");
        } else {
            writer.write("\nВы ввели id, с которым что-то не то (м.б. такого id вообще нет или id < 0)\n");
        }
    }

    @Override
    public void updateBookAuthorById() {
        writer.write("\nВведите id книги, у котрой хотите изменить автора: ");
        int id = reader.readInt();
        if (BookValidator.invalidId(id)) {
            writer.write("\nНекорректный id книги (м.б. < 0)!\n");
            return;
        }
        if (bookService.existBookById(id)) {
            Book book = bookService.findBookById(id);
            writer.write("Выберите нового автора книги из списка: \n");
            Author[] all = getAuthors();
            writer.write("Вы выбрали: ");
            int i = reader.readInt() - 1;
            if (i < all.length && i >= 0) {
                Author author = all[i];
                book.setAuthor(author);
            } else {
                writer.write("\nВы выбрали автора, которого нет в списке\n");
            }
        } else {
            writer.write("\nВы ввели id, с которым что-то не то (м.б. такого id вообще нет или id < 0)\n");
        }
    }

    private Author[] getAuthors() {
        Author[] all = authorService.findAllAuthors();
        for (int i = 0; i < all.length; i++) {
            if(all[i] == null) continue;
            writer.write("Автор " + (i + 1) + " - \"" + all[i].getName() + "\"\n");
        }
        return all;
    }

    @Override
    public void updateBookPriceById() {
        writer.write("\nВведите id книги, у котрой хотите изменить цену: ");
        int id = reader.readInt();
        if (BookValidator.invalidId(id)) {
            writer.write("\nНекорректный id книги (м.б. < 0)!\n");
            return;
        }
        if (bookService.existBookById(id)) {
            writer.write("\nВведите новую цену книги: ");
            int newPrice = reader.readInt();
            if (BookValidator.invalidPrice(newPrice)) {
                writer.write("\nНекорректная цена книги (м.б. < 0)!\n");
                return;
            }
            bookService.updateBookPriceById(newPrice, id);
            writer.write("\nЦена книги была успешно обновлена!\n");
        } else {
            writer.write("\nВы ввели id, с которым что-то не то (м.б. такого id вообще нет или id < 0)\n");
        }
    }

    @Override
    public void findAllBooks() {
        Book[] allBooks = bookService.findAllBooks();
        for (int i = 0; i < allBooks.length; i++) {
            Book book = allBooks[i];
            writer.write("\nКнига " + book.getId()
                    + "\nназвание: " + book.getTitle()
                    + "\nавтор: " + book.getAuthor().getName()
                    + "\nописание: " + book.getDescription()
                    + "\nцена = " + book.getPrice() + "\n");
        }
        selectBook("\nВыберите книгу: ");
    }

    private void selectBook(String s) {
        writer.write(s);
        int i = reader.readInt();
        if (i > 0) {
            writer.write("Вы выбрали:\n");
            Book bookById = bookService.findBookById(i);
            writer.write("\nКнига " + bookById.getId()
                    + "\nназвание: " + bookById.getTitle()
                    + "\nавтор: " + bookById.getAuthor().getName()
                    + "\nописание: " + bookById.getDescription()
                    + "\nцена = " + bookById.getPrice() + "\n");
            writer.write("\n1 - Положить в корзину");
            writer.write("\n2 - Продолжить поиск\n");
            writer.write("Вы выбрали: ");
            switch (reader.readInt()) {
                case 1:
                    addBookToBasket(bookById);
                    break;
                case 2:
                    break;
            }
        }else {
            writer.write("\nВы выбрали что-то не то\n");
        }
    }

    @Override
    public void findAllBooksByAuthor() {
        writer.write("\nВыберите автора из списка, книги которого хотите найти: \n");
        Author[] all = getAuthors();
        writer.write("Вы выбрали: ");
        int i = reader.readInt() - 1;
        if (i < all.length && i >= 0) {
            Author author = all[i];
            Book[] allBooksByAuthor = bookService.findAllBooksByAuthor(author);
            for (Book book : allBooksByAuthor) {
                writer.write("\nКнига " + book.getId()
                        + "\nназвание: " + book.getTitle()
                        + "\nавтор: " + book.getAuthor().getName()
                        + "\nописание: " + book.getDescription()
                        + "\nцена = " + book.getPrice() + "\n");
            }
            selectBook("\nВыберите книгу: ");
        } else {
            writer.write("\nВы выбрали автора, которого нет в списке\n");
        }
    }

    @Override
    public void findBookById() {
        writer.write("\nВведите id книги, которую хотите найти: ");
        int id = reader.readInt();
        if (BookValidator.invalidId(id)) {
            writer.write("\nНекорректный id книги (м.б. < 0)!\n");
            return;
        }
        if (bookService.existBookById(id)) {
            Book bookById = bookService.findBookById(id);
            writer.write("\nКнига " + bookById.getId()
                    + "\nназвание: " + bookById.getTitle()
                    + "\nавтор: " + bookById.getAuthor().getName()
                    + "\nописание: " + bookById.getDescription()
                    + "\nцена = " + bookById.getPrice() + "\n");
        } else {
            writer.write("\nВы выбрали id, которого нет\n");
        }
    }

    @Override
    public void findBookByTitle() {
        writer.write("\nВведите название книги, которую хотите найти: ");
        String title = reader.readString();
        if (BookValidator.invalidTitle(title)) {
            writer.write("\nНекорректное название книги (м.б. слишком короткое)!\n");
            return;
        }
        if (bookService.existBookByTitle(title)) {
            Book book = bookService.findBookByTitle(title);
            writer.write("\nКнига " + book.getId()
                    + "\nназвание: " + book.getTitle()
                    + "\nавтор: " + book.getAuthor().getName()
                    + "\nописание: " + book.getDescription()
                    + "\nцена = " + book.getPrice() + "\n");
            writer.write("\n1 - Положить в корзину");
            writer.write("\n2 - Продолжить поиск\n");
            writer.write("Вы выбрали: ");
            switch (reader.readInt()) {
                case 1:
                    addBookToBasket(book);
                    break;
                case 2:
                    break;
            }
        } else {
            writer.write("\nВы ввели название книги, которой у нас нет\n");
        }
    }

    @Override
    public void findAllBooksInBasket() {
        Book[] books = UserActionImpl.session.getBasket().getBooks();
        for (int i = 0; i < books.length; i++) {
            Book book = books[i];
            if (book == null) break;
            writer.write("\nКнига " + book.getId()
                    + "\nназвание: " + book.getTitle()
                    + "\nавтор: " + book.getAuthor().getName()
                    + "\nописание: " + book.getDescription()
                    + "\nцена = " + book.getPrice() + "\n");
        }
    }

    private void addBookToBasket(Book book) {
        Book[] books = UserActionImpl.session.getBasket().getBooks();
        boolean isFull = true;
        for (int i = 0; i < books.length; i++) {
            if (books[i] == null) {
                isFull = false;
                books[i] = book;
                break;
            }
        }
        if (isFull) writer.write("\nКорзина полная\n");
    }


    @Override
    public void findAllBooksByPrice() {
        writer.write("\nВведите цену книги, которую хотите найти: ");
        int price = reader.readInt();
        if (BookValidator.invalidPrice(price)) {
            writer.write("\nНекорректная цена книги (м.б. < 0)!\n");
            return;
        }
        if (bookService.existBookByPrice(price)) {
            Book[] allBooksByPrice = bookService.findAllBooksByPrice(price);
            for (int i = 0; i < allBooksByPrice.length; i++) {
                Book book = allBooksByPrice[i];
                writer.write("\nКнига " + book.getId()
                        + "\nназвание: " + book.getTitle()
                        + "\nавтор: " + book.getAuthor().getName()
                        + "\nописание: " + book.getDescription()
                        + "\nцена = " + book.getPrice() + "\n");
            }
            selectBook("Выберите книгу\n");
        } else {
            writer.write("\nВы ввели цену книгу, которой у нас нет\n");
        }
    }
}
