package com.example.perpus_gui;
import com.example.perpus_gui.controllers.BookLoanController;
import com.example.perpus_gui.controllers.BookManagementController;
import com.example.perpus_gui.controllers.MemberManagementController;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import com.example.perpus_gui.models.*;

import java.time.LocalDate;

public class HelloApplication extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    private ObservableList<Book> bookList = FXCollections.observableArrayList();
    private ObservableList<LibraryMember> memberList = FXCollections.observableArrayList();
    private ObservableList<BookLoan> bookLoanList = FXCollections.observableArrayList();

    private BookManagementController bookController = new BookManagementController(bookList);
    private MemberManagementController memberController = new MemberManagementController(memberList);
    private BookLoanController bookLoanController = new BookLoanController(bookLoanList);

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Aplikasi Perpustakaan");

        BorderPane root = new BorderPane();
        TabPane tabPane = new TabPane();

        Tab bookTab = new Tab("Manajemen Buku");
        bookTab.setClosable(false);
        bookTab.setContent(createBookManagementView());

        Tab memberTab = new Tab("Manajemen Anggota");
        memberTab.setClosable(false);
        memberTab.setContent(createMemberManagementView());

        Tab loanTab = new Tab("Peminjaman Buku");
        loanTab.setClosable(false);
        loanTab.setContent(createBookLoanView());

        tabPane.getTabs().addAll(bookTab, memberTab, loanTab);
        root.setCenter(tabPane);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Pane createBookManagementView() {
        VBox bookManagementLayout = new VBox(10);
        bookManagementLayout.setPadding(new Insets(20, 20, 20, 20));

        Text title = new Text("Manajemen Buku");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        TableView<Book> bookTable = new TableView<>();

        TableColumn<Book, String> titleColumn = new TableColumn<>("Judul");
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());

        TableColumn<Book, String> authorColumn = new TableColumn<>("Pengarang");
        authorColumn.setCellValueFactory(cellData -> cellData.getValue().authorProperty());

        TableColumn<Book, String> isbnColumn = new TableColumn<>("ISBN");
        isbnColumn.setCellValueFactory(cellData -> cellData.getValue().isbnProperty());

        TableColumn<Book, String> genreColumn = new TableColumn<>("Genre");
        genreColumn.setCellValueFactory(cellData -> cellData.getValue().genreProperty());

        bookTable.getColumns().addAll(titleColumn, authorColumn, isbnColumn, genreColumn);

        TextField titleField = new TextField();
        titleField.setPromptText("Judul");
        TextField authorField = new TextField();
        authorField.setPromptText("Pengarang");
        TextField isbnField = new TextField();
        isbnField.setPromptText("ISBN");
        TextField genreField = new TextField();
        genreField.setPromptText("Genre");

        Button addButton = new Button("Tambah Buku");
        addButton.setOnAction(event -> {
            String titleText = titleField.getText();
            String authorText = authorField.getText();
            String isbnText = isbnField.getText();
            String genreText = genreField.getText();

            Book newBook = new Book(titleText, authorText, isbnText, genreText);
            bookController.addBook(newBook);

            titleField.clear();
            authorField.clear();
            isbnField.clear();
            genreField.clear();
        });

        Button updateButton = new Button("Perbarui Buku");
        updateButton.setDisable(true);

        Button deleteButton = new Button("Hapus Buku");
        deleteButton.setDisable(true);

        TextField searchField = new TextField();
        searchField.setPromptText("Cari Buku");

        bookTable.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Book> observable, Book oldValue, Book newValue) -> {
            if (newValue != null) {
                updateButton.setDisable(false);
                titleField.setText(newValue.getTitle());
                authorField.setText(newValue.getAuthor());
                isbnField.setText(String.valueOf(newValue.getIsbn()));
                genreField.setText(newValue.getGenre());
                deleteButton.setDisable(false);
            } else {
                updateButton.setDisable(true);
                deleteButton.setDisable(true);
            }
        });

        updateButton.setOnAction(event -> {
            Book selectedBook = bookTable.getSelectionModel().getSelectedItem();
            if (selectedBook != null) {
                String newTitle = titleField.getText();
                String newAuthor = authorField.getText();
                String newIsbn = isbnField.getText();
                String newGenre = genreField.getText();
                selectedBook.setTitle(newTitle);
                selectedBook.setAuthor(newAuthor);
                selectedBook.setIsbn(newIsbn);
                selectedBook.setGenre(newGenre);
                bookTable.refresh();


                titleField.clear();
                authorField.clear();
                isbnField.clear();
                genreField.clear();
            }
        });

        deleteButton.setOnAction(event -> {
            Book selectedBook = bookTable.getSelectionModel().getSelectedItem();
            if (selectedBook != null) {
                bookController.deleteBook(selectedBook);
            }
        });

        FilteredList<Book> filteredData = new FilteredList<>(bookList, p -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(book -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                return book.getTitle().toLowerCase().contains(lowerCaseFilter)
                        || book.getAuthor().toLowerCase().contains(lowerCaseFilter)
                        || book.getIsbn().toLowerCase().contains(lowerCaseFilter);
            });
        });

        SortedList<Book> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(bookTable.comparatorProperty());

        bookTable.setItems(sortedData);


        Text title2 = new Text("Tambah dan Perbarui Buku");
        title2.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        bookManagementLayout.getChildren().addAll(title, searchField, bookTable, title2, titleField, authorField, isbnField, genreField, addButton, updateButton, deleteButton);

        return bookManagementLayout;
    }

    private Pane createMemberManagementView() {
        VBox memberManagementLayout = new VBox(10);
        memberManagementLayout.setPadding(new Insets(20, 20, 20, 20));

        Text title = new Text("Manajemen Anggota Perpustakaan");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        TableView<LibraryMember> memberTable = new TableView<>();

        TableColumn<LibraryMember, String> nameColumn = new TableColumn<>("Nama");
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        TableColumn<LibraryMember, String> contactColumn = new TableColumn<>("Kontak");
        contactColumn.setCellValueFactory(cellData -> cellData.getValue().contactDetailsProperty());

        TableColumn<LibraryMember, Boolean> statusColumn = new TableColumn<>("Status Keanggotaan");
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().memberStatusProperty().asObject());

        memberTable.getColumns().addAll(nameColumn, contactColumn, statusColumn);

        TextField nameField = new TextField();
        nameField.setPromptText("Nama");
        TextField contactField = new TextField();
        contactField.setPromptText("Kontak");

        CheckBox statusCheckBox = new CheckBox("Anggota Aktif");

        Button addButton = new Button("Tambah Anggota");
        addButton.setOnAction(event -> {
            String nameText = nameField.getText();
            String contactText = contactField.getText();
            boolean status = statusCheckBox.isSelected();

            LibraryMember newMember = new LibraryMember(nameText, contactText, status);
            memberController.addMember(newMember);

            nameField.clear();
            contactField.clear();
            statusCheckBox.setSelected(false);
        });

        Button updateButton = new Button("Perbarui Anggota");
        updateButton.setDisable(true);

        Button deleteButton = new Button("Hapus Anggota");
        deleteButton.setDisable(true);

        TextField searchField = new TextField();
        searchField.setPromptText("Cari Anggota");

        memberTable.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends LibraryMember> observable, LibraryMember oldValue, LibraryMember newValue) -> {
            if (newValue != null) {
                updateButton.setDisable(false);
                nameField.setText(newValue.getName());
                contactField.setText(newValue.getContactDetails());
                statusCheckBox.setSelected(newValue.isMemberStatus());
                deleteButton.setDisable(false);
            } else {
                updateButton.setDisable(true);
                deleteButton.setDisable(true);
            }
        });

        updateButton.setOnAction(event -> {
            LibraryMember selectedMember = memberTable.getSelectionModel().getSelectedItem();
            if (selectedMember != null) {
                String newName = nameField.getText();
                String newContact = contactField.getText();
                boolean newStatus = statusCheckBox.isSelected();
                selectedMember.setName(newName);
                selectedMember.setContactDetails(newContact);
                selectedMember.setMemberStatus(newStatus);
                memberTable.refresh();

                nameField.clear();
                contactField.clear();
                statusCheckBox.setSelected(false);
                updateButton.setDisable(true);
                deleteButton.setDisable(true);
            }
        });

        deleteButton.setOnAction(event -> {
            LibraryMember selectedMember = memberTable.getSelectionModel().getSelectedItem();
            if (selectedMember != null) {
                memberController.deleteMember(selectedMember);
                deleteButton.setDisable(true);
                updateButton.setDisable(true);
            }
        });

        FilteredList<LibraryMember> filteredData = new FilteredList<>(memberList, p -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(member -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                return member.getName().toLowerCase().contains(lowerCaseFilter)
                        || member.getContactDetails().toLowerCase().contains(lowerCaseFilter);
            });
        });

        SortedList<LibraryMember> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(memberTable.comparatorProperty());

        memberTable.setItems(sortedData);

        Text title2 = new Text("Tambah dan Perbarui Anggota");
        title2.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        memberManagementLayout.getChildren().addAll(title, searchField, memberTable, title2, nameField, contactField, statusCheckBox, addButton, updateButton, deleteButton);

        return memberManagementLayout;
    }

    private Pane createBookLoanView() {
        VBox bookLoanLayout = new VBox(10);
        bookLoanLayout.setPadding(new Insets(20, 20, 20, 20));

        Text title = new Text("Peminjaman dan Pengembalian Buku");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        TableView<BookLoan> bookLoanTable = new TableView<>();

        TableColumn<BookLoan, String> memberNameColumn = new TableColumn<>("Nama Anggota");
        memberNameColumn.setCellValueFactory(cellData -> cellData.getValue().getLibraryMember().nameProperty());

        TableColumn<BookLoan, String> bookTitleColumn = new TableColumn<>("Judul Buku");
        bookTitleColumn.setCellValueFactory(cellData -> cellData.getValue().getBook().titleProperty());

        TableColumn<BookLoan, LocalDate> checkOutDateColumn = new TableColumn<>("Tgl. Pinjam");
        checkOutDateColumn.setCellValueFactory(cellData -> cellData.getValue().checkOutDateProperty());

        TableColumn<BookLoan, LocalDate> dueDateColumn = new TableColumn<>("Tgl. Jatuh Tempo");
        dueDateColumn.setCellValueFactory(cellData -> cellData.getValue().getDueDateProperty());

        bookLoanTable.getColumns().addAll(memberNameColumn, bookTitleColumn, checkOutDateColumn, dueDateColumn);

        Button checkOutButton = new Button("Pinjam Buku");
        checkOutButton.setDisable(true);

        Button checkInButton = new Button("Kembalikan Buku");
        checkInButton.setDisable(true);

        TextField searchMemberField = new TextField();
        searchMemberField.setPromptText("Cari Anggota");

        TextField searchBookField = new TextField();
        searchBookField.setPromptText("Cari Buku");

        bookLoanTable.setOnMouseClicked((MouseEvent event) -> {
            BookLoan selectedLoan = bookLoanTable.getSelectionModel().getSelectedItem();
            if (selectedLoan != null) {
                checkOutButton.setDisable(true);
                checkInButton.setDisable(false);
            } else {
                checkOutButton.setDisable(false);
                checkInButton.setDisable(true);
            }
        });

        checkOutButton.setOnAction(event -> {
            LibraryMember selectedMember = findMember(searchMemberField.getText());
            Book selectedBook = findBook(searchBookField.getText());

            if (selectedMember != null && selectedBook != null) {
                LocalDate dueDate = LocalDate.now().plusDays(14);

                bookLoanController.checkOutBook(selectedMember, selectedBook, dueDate);

                bookLoanTable.setItems(bookLoanList);
            }
        });

        checkInButton.setOnAction(event -> {
            BookLoan selectedLoan = bookLoanTable.getSelectionModel().getSelectedItem();
            if (selectedLoan != null) {
                bookLoanController.checkInBook(selectedLoan);

                bookLoanTable.setItems(bookLoanList);
            }
        });

        FilteredList<BookLoan> filteredData = new FilteredList<>(bookLoanList, p -> true);

        searchMemberField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(loan -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                return loan.getLibraryMember().getName().toLowerCase().contains(lowerCaseFilter);
            });
        });

        searchBookField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(loan -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                return loan.getBook().getTitle().toLowerCase().contains(lowerCaseFilter);
            });
        });

        SortedList<BookLoan> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(bookLoanTable.comparatorProperty());

        bookLoanTable.setItems(sortedData);

        bookLoanLayout.getChildren().addAll(title, searchMemberField, searchBookField, bookLoanTable, checkOutButton, checkInButton);

        return bookLoanLayout;
    }

    private LibraryMember findMember(String keyword) {
        for (LibraryMember member : memberList) {
            if (member.getName().equalsIgnoreCase(keyword)) {
                return member;
            }
        }
        return null;
    }

    private Book findBook(String keyword) {
        for (Book book : bookList) {
            if (book.getTitle().equalsIgnoreCase(keyword)) {
                return book;
            }
        }
        return null;
    }
}




