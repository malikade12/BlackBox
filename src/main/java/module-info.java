module com.example.demo1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.program to javafx.fxml;
    exports com.program;
}