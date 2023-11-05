package com.example.perpus_gui.controllers;
import com.example.perpus_gui.models.Book;
import com.example.perpus_gui.models.BookLoan;
import com.example.perpus_gui.models.LibraryMember;
import javafx.collections.ObservableList;
import java.time.LocalDate;

public class BookLoanController {
    private ObservableList<BookLoan> bookLoanList;

    public BookLoanController(ObservableList<BookLoan> bookLoanList) {
        this.bookLoanList = bookLoanList;
    }

    public void checkOutBook(LibraryMember libraryMember, Book book, LocalDate dueDate) {
        BookLoan bookLoan = new BookLoan(libraryMember, book, LocalDate.now(), dueDate);
        bookLoanList.add(bookLoan);
    }

    public void checkInBook(BookLoan bookLoan) {
        bookLoanList.remove(bookLoan);
    }
}
