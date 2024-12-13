module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires org.apache.logging.log4j;

    opens com.example to javafx.fxml;
    exports com.example;
}
