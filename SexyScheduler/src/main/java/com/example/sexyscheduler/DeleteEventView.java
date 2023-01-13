package com.example.sexyscheduler;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DeleteEventView extends StackPane{
    private Label warningMessage;
    private Button confirmCancel;
    private Button deleteCancel;
    private VBox root;
    private Stage deleteEventViewStage;
    private Scene deleteEventViewScene;

    private CalendarModel model;
    private Controller controller;

    public DeleteEventView() {
        // setting up the view
        root = new VBox(5);
        root.setPadding(new Insets(5));
        root.setAlignment(Pos.CENTER);
        warningMessage = new Label("Are you sure you want to delete this event?");
        warningMessage.setWrapText(true);
        warningMessage.setAlignment(Pos.CENTER);

        HBox buttonContainer = new HBox(5);
        buttonContainer.setMinSize(200,100);
        buttonContainer.setMaxSize(200,100);
        buttonContainer.setPrefSize(200,100);
        confirmCancel = new Button("Yes, delete it!");
        deleteCancel = new Button("Keep my event!");

        buttonContainer.getChildren().addAll(deleteCancel, confirmCancel);
        root.getChildren().addAll(warningMessage, buttonContainer);

        this.getChildren().add(root);
        this.setMinSize(300,300);
        this.setPrefSize(300,300);
        this.setMaxSize(300,300);

        // setting up the stage
        deleteEventViewStage = new Stage();
        deleteEventViewStage.setTitle("Delete Event?");
        deleteEventViewScene = new Scene(this);
        deleteEventViewScene.getStylesheets().add(this.getClass().getResource("styles.css").toExternalForm());
        this.getStylesheets().add(this.getClass().getResource("styles.css").toExternalForm());
        root.setId("cust-background");
        deleteEventViewStage.setScene(deleteEventViewScene);
        deleteEventViewStage.hide();
    }

    public void setController(Controller controller) {
        deleteCancel.setOnAction(controller::handleDeleteCancelButtonClicked);
        confirmCancel.setOnAction(controller::handleConfirmCancelButtonClicked);
    }

    public void setModel(CalendarModel model) {
        this.model = model;
    }

/*    public void modelChanged() {
        if (model.showDeleteEventView) {
            deleteEventViewStage.show();
        }
        else {
            deleteEventViewStage.hide();
        }
    }*/
}
