package com.example.sexyscheduler;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

/**
 * Error view used when an error is encountered while adding events
 */
public class ErrorView extends StackPane {

    Button okayButton;

    public ErrorView() {
        BorderPane root = new BorderPane();
        setPrefSize(400,200);
        setStyle("-fx-background-color: white");
        Font labelFont = Font.font("Helvetica", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 20);
        Label errorLabel = new Label("Sorry! There was a error while\nwe were trying to schedule that.");
        errorLabel.setFont(labelFont);

        this.okayButton = new Button("Okay");
        this.okayButton.setStyle("-fx-base: white; -fx-border-color: black; -fx-font-family: Helvetica;  -fx-focus-color: transparent; -fx-faint-focus-color: transparent");

        VBox errorVBox = new VBox(errorLabel, this.okayButton);
        errorVBox.setAlignment(Pos.CENTER);
        errorVBox.setSpacing(20);

        root.setCenter(errorVBox);
        this.getChildren().add(root);
    }
}
