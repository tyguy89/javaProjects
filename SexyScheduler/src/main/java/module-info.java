module com.example.sexyscheduler {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;


    opens com.example.sexyscheduler to javafx.fxml;
    exports com.example.sexyscheduler;
}