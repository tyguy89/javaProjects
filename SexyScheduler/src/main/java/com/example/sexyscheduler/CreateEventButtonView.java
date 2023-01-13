package com.example.sexyscheduler;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;

/**
 * View displayed on main screen that contains "Add Event" button
 */
public class CreateEventButtonView extends HBox {
    private Button createButton;

    public CreateEventButtonView() {
        HBox root = new HBox();
        this.createButton = new Button("Add Event");
        this.createButton.setPrefSize(450, 60);
        HBox.setHgrow(this.createButton, Priority.ALWAYS);

        root.getChildren().add(this.createButton);
        this.getChildren().add(root);
        this.setAlignment(Pos.CENTER);
        this.getStylesheets().add(this.getClass().getResource("styles.css").toExternalForm());
        createButton.setId("cust-button");
    }

    public void setController(Controller controller) {
        // when button clicked display pop-up window for adding events
        this.createButton.setOnAction(controller::handleAddEventButton);
    }
}
