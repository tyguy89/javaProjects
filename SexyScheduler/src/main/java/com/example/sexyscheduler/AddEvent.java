package com.example.sexyscheduler;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;

/**
 * View to add an event
 */
public class AddEvent extends CreateEventViewItems {
    public AddEvent(Filter_View filters) {
        super(filters);

        // initial window set up
        BorderPane root = new BorderPane();
        root.setMinSize(530, 600);

        VBox eventVBox = new VBox(titleHBox, super.singleDaySelection(), super.startEndTimeSelection(), tagHBox, createEventButton);
        eventVBox.setPrefSize(550, 420);
        eventVBox.setAlignment(Pos.CENTER);
        eventVBox.setSpacing(30);
        eventVBox.setPadding(new Insets(20, 20, 20, 20));
        root.setCenter(eventVBox);
        this.getChildren().add(root);
        this.setPadding(new Insets(20));
        this.getStylesheets().add(this.getClass().getResource("styles.css").toExternalForm());

        createEventButton.setId("cust-button");


    }

    /**
     * Set controller for the view and handle action events
     *
     * @param controller application controller class
     */
    public void setController(Controller controller) {
        // when button is the new event to be scheduled is processed - check input and conflicts before scheduling
        createEventButton.setOnAction(e -> {
            // proceed if all necessary fields contain input
            if (this.titleField.getText() != null && this.startDayPick.getValue() != null && this.startTimeField.getText() != null
                    && this.endTimeField.getText() != null && this.tagCombo.getSelectionModel().getSelectedItem() != null) {
                // obtain the start hour time, start minutes time, end hour time, and end minutes time separately for comparison check
                try {
                    ArrayList<String> start = new ArrayList<>(List.of(this.startTimeField.getText().split(":")));
                    int startHour = Integer.parseInt(start.get(0));
                    int startMin = Integer.parseInt(start.get(1));
                    ArrayList<String> end = new ArrayList<>(List.of(this.endTimeField.getText().split(":")));
                    int endHour = Integer.parseInt(end.get(0));
                    int endMin = Integer.parseInt(end.get(1));
                    // proceed if the entered start time and end time are not the same, the start hour time is before the end hour time, and the start minutes time is before the end minutes time
                    if ((!(startHour == endHour && startMin == endMin)) && (0 <= startHour && startHour <= 23 && 0 <= endHour && endHour <= 24 && startHour <= endHour) &&
                            (0 <= startMin && startMin <= 59 && 0 <= endMin && endMin <= 59)) {
                        // attempt to schedule event
                        try {
                            String dayChosen = startDayPick.getValue().toString();
                            ArrayList<String> dayString = new ArrayList<>(List.of(dayChosen.split("-")));
                            controller.handleOkayEventButton(titleField.getText(), dayString.get(0), dayString.get(1), dayString.get(2), startTimeField.getText(), endTimeField.getText(), tagCombo.getSelectionModel().getSelectedItem());
                        }
                        // if a check is unsuccessful display error pop-up
                        catch (Exception exception) {
                            controller.handleErrorFound();
                        }
                    } else {
                        controller.handleErrorFound();
                    }
                } catch (Exception exception) {
                    controller.handleErrorFound();
                }
            } else {
                controller.handleErrorFound();
            }
        });
    }

    /**
     * Clear past input
     */
    public void clearFields() {
        titleField.clear();
        startDayPick.getEditor().clear();
        startDayPick.setValue(null);
        startTimeField.clear();
        startTimeField.setPromptText("e.g. 11:15");
        endTimeField.clear();
        endTimeField.setPromptText("e.g. 16:30");
        tagCombo.getSelectionModel().clearSelection();
    }
}