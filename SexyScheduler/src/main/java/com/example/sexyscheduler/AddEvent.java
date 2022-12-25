package com.example.sexyscheduler;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * View for adding a regular event
 */
public class AddEvent extends StackPane  {

    private TextField titleField;
    private DatePicker dayPick;
    private String dayChosen;
    private TextField startField;
    private TextField endField;
    private ComboBox<String> tagCombo;
    private RadioButton yesRepeatRadio;
    private RadioButton noRepeatRadio;
    private ComboBox<String> repeatCombo;
    private Label occurrencesLabel;
    private Button createEventButton;
    private Boolean isEditing = false;


    /**
     * Constructor for add event view
     * @param filters view class of current filters
     */
    public AddEvent(Filter_View filters) {

        // initial window set up
        BorderPane root = new BorderPane();
        root.setPrefSize(400, 430);

        // field for user to enter event name/title
        Label titleLabel = new Label("Title:  ");
        this.titleField = new TextField();

        // field for user to enter day of event - a pop-up calendar is displayed
        Label dayLabel = new Label("Day:  ");
        this.dayPick = new DatePicker();
        this.dayPick.setPrefWidth(Double.MAX_VALUE);

        // field for user to enter the starting time of the event - better way to implement?
        // uses 24hr clock - input is expected in XX:XX format
        Label startLabel = new Label("Start:  ");
        this.startField = new TextField();
        this.startField.setPromptText("e.g. 11:15");

        // field for user to enter the ending time of the event - better way to implement?
        // uses 24hr clock - input is expected in XX:XX format
        Label endLabel = new Label("End:  ");
        this.endField = new TextField();
        this.endField.setPromptText("e.g. 16:30");

        // field for user to selected whether the event is repeatable - not yet implemented
        Label repeatLabel = new Label("Repeat:  ");
        //repeatLabel.setFont(labelFont);

        final ToggleGroup repeatGroup = new ToggleGroup();
        this.yesRepeatRadio = new RadioButton("Yes ");
        this.yesRepeatRadio.setToggleGroup(repeatGroup);
        this.noRepeatRadio = new RadioButton("No ");
        this.noRepeatRadio.setToggleGroup(repeatGroup);
        HBox repeatHBox = new HBox(this.yesRepeatRadio, this.noRepeatRadio);
        repeatHBox.setSpacing(15);

        // field for user to select event tag from the current filters available
        Label tagLabel = new Label("Tag:  ");
        //tagLabel.setFont(labelFont);
        this.tagCombo = new ComboBox<>();
        ArrayList<String> tags = filters.filterList;
        ObservableList<String> tagList = FXCollections.observableList(tags);
        this.tagCombo.setItems(tagList);
        this.tagCombo.getSelectionModel().selectFirst();
        this.tagCombo.setPrefWidth(Double.MAX_VALUE);

        this.occurrencesLabel = new Label("Occurrences:  ");
        this.occurrencesLabel.setStyle("-fx-font-size: 14");
        //this.occurrencesLabel.setFont(smallLabelFont);
        this.occurrencesLabel.setVisible(false);
        this.repeatCombo = new ComboBox<>();
        ArrayList<String> repeatList = new ArrayList<>(List.of("Every Day", "Every Week", "Every Month"));
        ObservableList<String> repeatItems = FXCollections.observableList(repeatList);
        this.repeatCombo.setItems(repeatItems);
        this.repeatCombo.setPrefWidth(Double.MAX_VALUE);
        this.repeatCombo.setVisible(false);

        // create button to submit event to be scheduled
        this.createEventButton = new Button("Create Event");
        this.createEventButton.setAlignment(Pos.CENTER);

        // set alignment of widgets in center of screen
        VBox labelVBox = new VBox(titleLabel, dayLabel, startLabel, endLabel, tagLabel, repeatLabel, this.occurrencesLabel);
        VBox fieldVBox = new VBox(this.titleField, this.dayPick, this.startField, this.endField, this.tagCombo, repeatHBox, this.repeatCombo);
        labelVBox.setPrefWidth(100);
        labelVBox.setSpacing(20);
        fieldVBox.setPrefWidth(400);
        fieldVBox.setSpacing(15);
        HBox eventHBox = new HBox(labelVBox, fieldVBox);
        HBox.setHgrow(fieldVBox, Priority.ALWAYS);
        eventHBox.setPrefWidth(450);
        eventHBox.setPrefHeight(200);
        eventHBox.setAlignment(Pos.CENTER);

        VBox eventVBox = new VBox(eventHBox, this.createEventButton);
        eventVBox.setSpacing(20);
        eventVBox.setPrefWidth(450);
        eventVBox.setPrefHeight(200);
        eventVBox.setAlignment(Pos.CENTER);

        // enable window resizing
        StackPane left = new StackPane();
        left.setPrefSize(30,30);
        StackPane right = new StackPane();
        right.setPrefSize(30,30);
        root.setLeft(left);
        root.setRight(right);

        // display add event view
        root.setCenter(eventVBox);
        this.getChildren().add(root);
    }

    /**
     * Clear all input fields - ex. when an event is successfully created
     */
    public void clearFields() {
        this.titleField.clear();
        this.dayPick.getEditor().clear();
        this.dayPick.setValue(null);
        this.startField.clear();
        this.startField.setPromptText("e.g. 11:15");
        this.endField.clear();
        this.endField.setPromptText("e.g. 16:30");
        this.tagCombo.getSelectionModel().clearSelection();
        this.tagCombo.getSelectionModel().selectFirst();
        this.yesRepeatRadio.setSelected(false);
        this.noRepeatRadio.setSelected(false);
        this.repeatCombo.getSelectionModel().clearSelection();
        this.occurrencesLabel.setVisible(false);
        this.repeatCombo.setVisible(false);
    }

    /**
     * Set controller for the view and handle action events
     * @param controller application controller class
     */
    public void setController(Controller controller) {
        this.yesRepeatRadio.setOnAction(e -> {
            this.occurrencesLabel.setVisible(true);
            this.repeatCombo.setVisible(true);
        });
        this.noRepeatRadio.setOnAction(e -> {
            this.occurrencesLabel.setVisible(false);
            this.repeatCombo.setVisible(false);
        });

        // when button is the new event to be scheduled is processed - check input and conflicts before scheduling
        this.createEventButton.setOnAction(e -> {
            // proceed if all necessary fields contain input
            if (this.titleField.getText() != null && this.dayPick.getValue() != null && this.startField.getText() != null
                    && this.endField.getText() != null && this.tagCombo.getSelectionModel().getSelectedItem() != null) {
                // obtain the start hour time, start minutes time, end hour time, and end minutes time separately for comparison check
                try {
                    ArrayList<String> start = new ArrayList<>(List.of(this.startField.getText().split(":")));
                    int startHour = Integer.parseInt(start.get(0));
                    int startMin = Integer.parseInt(start.get(1));
                    ArrayList<String> end = new ArrayList<>(List.of(this.endField.getText().split(":")));
                    int endHour = Integer.parseInt(end.get(0));
                    int endMin = Integer.parseInt(end.get(1));
                    // proceed if the entered start time and end time are not the same, the start hour time is before the end hour time, and the start minutes time is before the end minutes time
                    if ((!(startHour == endHour && startMin == endMin)) && (0 <= startHour && startHour <= 23 && 0 <= endHour && endHour <= 24 && startHour <= endHour) &&
                            (0 <= startMin && startMin <= 59 && 0 <= endMin && endMin <= 59)) {
                        // attempt to schedule event
                        try {
                            dayChosen = dayPick.getValue().toString();
                            ArrayList<String> dayString = new ArrayList<>(List.of(dayChosen.split("-")));
                            controller.handleOkayEventButton(titleField.getText(), dayString.get(0), dayString.get(1), dayString.get(2), startField.getText(), endField.getText(), tagCombo.getSelectionModel().getSelectedItem());
                        }
                        // if a check is unsuccessful display error pop-up
                        catch (Exception exception) {
                            controller.handleErrorFound();
                        }
                    }
                    else {controller.handleErrorFound();}
                } catch (Exception exception) {
                    controller.handleErrorFound();
                }
            }
            else {controller.handleErrorFound();}
        });
    }
}
