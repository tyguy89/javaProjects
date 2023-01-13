module com.example.sexyscheduler {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.sexyscheduler to javafx.fxml;
    exports com.example.sexyscheduler;
}