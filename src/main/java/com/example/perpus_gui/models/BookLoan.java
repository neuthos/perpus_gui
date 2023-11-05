package com.example.perpus_gui.models;
import javafx.beans.property.*;
import java.time.LocalDate;

public class BookLoan {
    private final ObjectProperty<LibraryMember> libraryMember;
    private final ObjectProperty<Book> book;
    private final ObjectProperty<LocalDate> checkOutDate;
    private final ObjectProperty<LocalDate> dueDate;

    public BookLoan(LibraryMember libraryMember, Book book, LocalDate checkOutDate, LocalDate dueDate) {
        this.libraryMember = new SimpleObjectProperty<>(libraryMember);
        this.book = new SimpleObjectProperty<>(book);
        this.checkOutDate = new SimpleObjectProperty<>(checkOutDate);
        this.dueDate = new SimpleObjectProperty<>(dueDate);
    }

    public ObjectProperty<LibraryMember> libraryMemberProperty() {
        return libraryMember;
    }

    public LibraryMember getLibraryMember() {
        return libraryMember.get();
    }

    public ObjectProperty<Book> bookProperty() {
        return book;
    }

    public Book getBook() {
        return book.get();
    }

    public ObjectProperty<LocalDate> checkOutDateProperty() {
        return checkOutDate;
    }

    public ObjectProperty<LocalDate> getCheckOutDate() {
        return checkOutDate;
    }

    public ObjectProperty<LocalDate> getDueDateProperty() {
        return dueDate;
    }

    public LocalDate getDueDate() {
        return dueDate.get();
    }
}
