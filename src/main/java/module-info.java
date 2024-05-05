module com.example.demo1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.junit.jupiter.api;

    opens com.program to javafx.fxml;
    exports com.program;
}
