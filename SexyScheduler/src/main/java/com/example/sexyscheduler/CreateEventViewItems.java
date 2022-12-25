package com.example.sexyscheduler;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;

public class CreateEventViewItems extends StackPane {

    HBox titleHBox;
    HBox singleTimeHBox;
    HBox tagHBox;
    HBox repeatHBox;
    HBox occurrencesHBox;
    HBox minutesHBox;
    HBox hoursHBox;
    HBox durationChoiceHBox;
    HBox hoursDurationHBox;
    HBox daysDurationHBox;
    HBox linkedChoiceHBox;

    Label dayLabel;
    Label dayConstraintsLabel;
    Label fromLabel;
    Label toLabel;
    Label startLabel;
    Label endLabel;
    Label timeConstraintsLabel;
    Label durationLabel;
    Label durationExplainLabel;
    Label linkExplainLabel;
    Label unlinkExplainLabel;

    TextField titleField;
    DatePicker startDayPick;
    DatePicker endDayPick;
    TextField startTimeField;
    TextField endTimeField;
    ComboBox<String> tagCombo;
    ComboBox<String> repeatCombo;
    TextField occurrencesField;
    RadioButton hourRadio;
    RadioButton noneRadio;
    TextField minuteDurationField;
    TextField hourDurationField;
    TextField hoursField;
    TextField daysField;
    RadioButton linkedRadio;
    RadioButton unlinkedRadio;
    Button createEventButton;
    Button createAllEventsButton;
    Button createDeadlineButton;
    Button submitChangesButton;

    public CreateEventViewItems(Filter_View filters) {

        // LABELS

        // title label
        Label titleLabel = new Label("Title:  ");

        // day labels
        this.dayLabel = new Label("Day:  ");
        this.dayConstraintsLabel = new Label("Day Constraints:");
        this.dayConstraintsLabel.setPadding(new Insets(0,0,0,20));
        this.dayConstraintsLabel.setAlignment(Pos.CENTER_LEFT);
        this.fromLabel = new Label("From:  ");
        this.toLabel = new Label("To:  ");

        // time labels
        Label timeLabel = new Label("Time:  ");
        this.startLabel = new Label("Start:  ");
        this.endLabel = new Label("End:  ");
        this.timeConstraintsLabel = new Label("Time Constraints:");
        this.timeConstraintsLabel.setPadding(new Insets(0,0,0,20));
        this.timeConstraintsLabel.setStyle("-fx-font-size: 21");

        // occurrences labels
        Label occurrencesLabel = new Label("Occurrences:  ");

        // tag labels
        Label tagLabel = new Label("Tag:  ");

        // repeat labels
        Label repeatLabel =  new Label("Repeat:  ");

        // duration labels
        this.durationLabel = new Label("Duration:  ");
        this.durationLabel.setPadding(new Insets(0,0,0,20));
        this.durationLabel.setStyle("-fx-font-size: 21");
        Label spacerTimeLabel = new Label("Spacer Time:");
        this.durationExplainLabel = new Label("*required amount of time between occurrences of the event");
        this.durationExplainLabel.setStyle("-fx-font-size: 12");

        Label minutesLabel = new Label("Minutes: ");
        Label hourLabel = new Label("Hours:  ");
        Label hoursLabel = new Label("Hours:  ");
        Label daysLabel = new Label("Days:  ");

        // link labels
        Label linkLabel = new Label("Link:  ");
        this.unlinkExplainLabel = new Label("*unlinked allows daily events to be customized");
        this.unlinkExplainLabel.setStyle("-fx-font-size: 12");
        this.linkExplainLabel = new Label("*linked events create the same event for each day of the week");
        this.linkExplainLabel.setStyle("-fx-font-size: 12");

        // FIELDS

        // title HBOX field
        this.titleField = new TextField();
        this.titleField.setMaxWidth(400);
        this.titleHBox = new HBox(titleLabel, this.titleField);
        this.titleHBox.setSpacing(15);
        HBox.setHgrow(this.titleField, Priority.ALWAYS);
        this.titleHBox.setAlignment(Pos.CENTER);

        // day fields
        this.startDayPick = new DatePicker();
        this.startDayPick.setPrefWidth(Double.MAX_VALUE);
        this.endDayPick = new DatePicker();
        this.endDayPick.setPrefWidth(Double.MAX_VALUE);

        // time fields
        this.startTimeField = new TextField();
        this.startTimeField.setPromptText("e.g. 11:15");
        startTimeField.setMaxWidth(400);
        this.endTimeField = new TextField();
        this.endTimeField.setPromptText("e.g. 16:30");

        this.singleTimeHBox = new HBox(timeLabel, startTimeField);
        this.singleTimeHBox.setSpacing(8);
        HBox.setHgrow(startTimeField, Priority.ALWAYS);
        this.singleTimeHBox.setAlignment(Pos.CENTER);

        // occurrences fields
        this.occurrencesField = new TextField();
        occurrencesField.setPromptText("e.g. 4");
        occurrencesField.setMaxWidth(330);
        this.occurrencesHBox = new HBox(occurrencesLabel, occurrencesField);
        this.occurrencesHBox.setSpacing(15);
        HBox.setHgrow(occurrencesField, Priority.ALWAYS);
        this.occurrencesHBox.setAlignment(Pos.CENTER);


        // tag fields
        this.tagCombo = new ComboBox<>();
        ArrayList<String> tags = filters.filterList;
        ObservableList<String> tagList = FXCollections.observableList(tags);
        this.tagCombo.setItems(tagList);
        this.tagCombo.setPrefWidth(Double.MAX_VALUE);
        this.tagCombo.setMaxWidth(400);
        this.tagHBox = new HBox(tagLabel, tagCombo);
        this.tagHBox.setSpacing(19);
        this.tagHBox.setAlignment(Pos.CENTER);
        tagCombo.getSelectionModel().selectFirst();

        // repeat fields
        this.repeatCombo = new ComboBox<>();
        ArrayList<String> repeatString = new ArrayList<>(List.of("Every Day", "Every Week", "Every Month"));
        ObservableList<String> repeatList = FXCollections.observableList(repeatString);
        this.repeatCombo.setItems(repeatList);
        this.repeatCombo.setPrefWidth(Double.MAX_VALUE);
        this.repeatCombo.setMaxWidth(395);
        this.repeatHBox = new HBox(repeatLabel, repeatCombo);
        repeatCombo.getSelectionModel().selectFirst();
        //this.repeatHBox.setSpacing();
        this.repeatHBox.setAlignment(Pos.CENTER);

        // duration fields
/*
        final ToggleGroup durationGroup = new ToggleGroup();
        this.durationMinuteRadio = new RadioButton("Minute(s)");
        this.durationMinuteRadio.setToggleGroup(durationGroup);
        this.durationHourRadio =  new RadioButton("Hour(s)");
        this.durationHourRadio.setToggleGroup(durationGroup);
        HBox durationRadioHBox = new HBox(this.durationMinuteRadio, this.durationHourRadio);
        durationRadioHBox.setSpacing(15);
        durationRadioHBox.setPrefHeight(30);
        durationRadioHBox.setAlignment(Pos.CENTER);

        this.durationHBox = new HBox(durationLabel, durationRadioHBox);
        this.durationHBox.setSpacing(10);
        this.durationHBox.setAlignment(Pos.CENTER);*/

/*        this.durationMinutesField = new TextField();
        this.durationMinutesField.setPromptText("e.g. 25");
        this.durationMinutesField.setAlignment(Pos.CENTER);
        this.durationMinutesField.setMaxWidth(390);

        this.durationHoursField = new TextField();
        this.durationHoursField.setPromptText("e.g. 5");
        this.durationHoursField.setAlignment(Pos.CENTER);
        this.durationHoursField.setMaxWidth(400);*/

/*
        this.minutesHBox = new HBox(minutesLabel, this.durationMinutesField);
        this.minutesHBox.setSpacing(6);
        HBox.setHgrow(durationMinutesField, Priority.ALWAYS);
        this.minutesHBox.setAlignment(Pos.CENTER);

        this.hoursHBox = new HBox(hoursLabel, this.durationHoursField);
        this.hoursHBox.setSpacing(6);
        HBox.setHgrow(durationHoursField, Priority.ALWAYS);
        this.hoursHBox.setAlignment(Pos.CENTER);
*/

        hourDurationField = new TextField();
        hourDurationField.setPromptText("e.g. 5");
        hourDurationField.setMaxWidth(400);

        minuteDurationField = new TextField();
        minuteDurationField.setPromptText("e.g. 45");
        minuteDurationField.setMaxWidth(390);

        this.hoursHBox = new HBox(hourLabel, hourDurationField);
        this.hoursHBox.setSpacing(3);
        HBox.setHgrow(hourDurationField, Priority.ALWAYS);
        this.hoursHBox.setAlignment(Pos.CENTER);

        this.minutesHBox = new HBox(minutesLabel, minuteDurationField);
        HBox.setHgrow(minuteDurationField, Priority.ALWAYS);
        this.minutesHBox.setAlignment(Pos.CENTER);

        final ToggleGroup spaceDurationGroup = new ToggleGroup();
        //this.dayRadio = new RadioButton("Day(s)");
        //this.dayRadio.setToggleGroup(spaceDurationGroup);
        this.hourRadio = new RadioButton("Hour(s)");
        this.hourRadio.setToggleGroup(spaceDurationGroup);
        this.noneRadio = new RadioButton("None");
        this.noneRadio.setToggleGroup(spaceDurationGroup);
        HBox spaceDurationHBox = new HBox(this.hourRadio, this.noneRadio);
        spaceDurationHBox.setSpacing(15);
        spaceDurationHBox.setPrefHeight(30);
        spaceDurationHBox.setAlignment(Pos.CENTER);

        this.durationChoiceHBox = new HBox(spacerTimeLabel, spaceDurationHBox);
        this.durationChoiceHBox.setSpacing(10);;
        this.durationChoiceHBox.setAlignment(Pos.CENTER);

        this.hoursField = new TextField();
        hoursField.setPromptText("e.g. 5");
        hoursField.setMaxWidth(400);

        this.daysField = new TextField();
        daysField.setPromptText("e.g. 1");
        daysField.setMaxWidth(400);

        this.hoursDurationHBox = new HBox(hoursLabel, hoursField);
        this.hoursDurationHBox.setSpacing(6);
        HBox.setHgrow(hoursField, Priority.ALWAYS);
        this.hoursDurationHBox.setAlignment(Pos.CENTER);

        this.daysDurationHBox = new HBox(daysLabel, daysField);
        this.daysDurationHBox.setSpacing(14);
        HBox.setHgrow(daysField, Priority.ALWAYS);
        this.daysDurationHBox.setAlignment(Pos.CENTER);

        // linked fields
        final ToggleGroup linkedGroup = new ToggleGroup();
        this.linkedRadio = new RadioButton("Linked");
        this.linkedRadio.setToggleGroup(linkedGroup);
        this.unlinkedRadio = new RadioButton("Unlinked");
        this.unlinkedRadio.setToggleGroup(linkedGroup);
        HBox linkedHBox = new HBox(this.linkedRadio, this.unlinkedRadio);
        linkedHBox.setSpacing(15);
        linkedHBox.setMaxWidth(400);
        linkedHBox.setPrefHeight(20);

        HBox spaceHBox = new HBox();
        this.linkedChoiceHBox = new HBox(linkLabel, linkedHBox, spaceHBox);
        this.linkedChoiceHBox.setSpacing(10);
        this.linkedChoiceHBox.setAlignment(Pos.CENTER);
        
        // create button to submit event to be scheduled
        this.createEventButton = new Button("Create Event");
        this.createEventButton.setAlignment(Pos.CENTER);

        this.createAllEventsButton = new Button("Create All Events");
        this.createAllEventsButton.setAlignment(Pos.CENTER);

        this.createDeadlineButton = new Button("Create Deadline");
        this.createDeadlineButton.setAlignment(Pos.CENTER);

        this.submitChangesButton = new Button("Submit Changes");
        this.submitChangesButton.setAlignment(Pos.CENTER);
    }

    public HBox singleDaySelection() {
        HBox dayHBox = new HBox(this.dayLabel, this.startDayPick);
        dayHBox.setSpacing(18);
        startDayPick.setMaxWidth(400);
        dayHBox.setAlignment(Pos.CENTER);
        return dayHBox;
    }

    public HBox startEndDaySelection() {
        VBox dayLabelVBox = new VBox(this.fromLabel, this.toLabel);
        dayLabelVBox.setMaxWidth(120);
        dayLabelVBox.setSpacing(13);
        dayLabelVBox.setAlignment(Pos.CENTER_LEFT);
        VBox dayFieldVBox = new VBox(this.startDayPick, this.endDayPick);
        dayFieldVBox.setMaxWidth(400);
        dayFieldVBox.setSpacing(5);
        HBox dayHBox = new HBox(dayLabelVBox, dayFieldVBox);
        dayHBox.setSpacing(8);
        HBox.setHgrow(dayFieldVBox, Priority.ALWAYS);
        dayHBox.setAlignment(Pos.CENTER);
        return dayHBox;
    }

    public HBox startEndTimeSelection() {
        VBox timeLabelVBox = new VBox(this.startLabel, this.endLabel);
        timeLabelVBox.setMaxWidth(120);
        timeLabelVBox.setAlignment(Pos.CENTER_LEFT);
        timeLabelVBox.setSpacing(13);
        VBox timeFieldVBox = new VBox(this.startTimeField, this.endTimeField);
        timeFieldVBox.setMaxWidth(400);
        timeFieldVBox.setSpacing(5);
        HBox timeHBox = new HBox(timeLabelVBox, timeFieldVBox);
        timeHBox.setSpacing(10);
        HBox.setHgrow(timeFieldVBox, Priority.ALWAYS);
        timeHBox.setAlignment(Pos.CENTER);
        return timeHBox;
    }
}
