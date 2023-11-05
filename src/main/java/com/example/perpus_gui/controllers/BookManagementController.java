package com.example.perpus_gui.controllers;

import com.example.perpus_gui.models.Book;
import javafx.collections.ObservableList;

public class BookManagementController {
    private final ObservableList<Book> bookList;

    public BookManagementController(ObservableList<Book> bookList) {
        this.bookList = bookList;
    }

    public void addBook(Book book) {
        bookList.add(book);
    }

    public void updateBook(Book book, int index) {
        bookList.set(index, book);
    }

    public void deleteBook(Book book) {
        bookList.remove(book);
    }

    public Book searchBook(String keyword) {
        for (Book book : bookList) {
            if (book.getTitle().equalsIgnoreCase(keyword)
                    || book.getAuthor().equalsIgnoreCase(keyword)
                    || book.getIsbn().equalsIgnoreCase(keyword)) {
                return book;
            }
        }
        return null;
    }
}

