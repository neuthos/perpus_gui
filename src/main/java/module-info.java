module com.example.perpus_gui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.perpus_gui to javafx.fxml;
    exports com.example.perpus_gui;
}