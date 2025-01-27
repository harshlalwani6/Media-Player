module com.example.mp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.web;


    opens com.example.mp to javafx.fxml;
    exports com.example.mp;
}