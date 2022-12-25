package com.example.sexyscheduler;

import javafx.geometry.Pos;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class AddAutoEvent extends CreateEventViewItems{

    private HBox spaceDurationHBox;
    private HBox timeDurationHBox;
    private RadioButton hour;
    private RadioButton day;
    private RadioButton none;

    public AddAutoEvent(Filter_View filters) {
        super(filters);

        BorderPane root = new BorderPane();

        this.spaceDurationHBox = new HBox();
        this.spaceDurationHBox.setVisible(false);
        this.spaceDurationHBox.setAlignment(Pos.CENTER);

        this.timeDurationHBox = new HBox();
        this.timeDurationHBox.setVisible(false);
        this.timeDurationHBox.setAlignment(Pos.CENTER);

        this.hour = hourRadio;
        //this.day = dayRadio;
        this.none = noneRadio;

        Separator separator1 = new Separator();
        Separator separator2 = new Separator();
        Separator separator3 = new Separator();
        Separator separator4 = new Separator();
        Separator separator5 = new Separator();

        VBox vBox1 = new VBox(dayConstraintsLabel, super.startEndDaySelection());
        vBox1.setSpacing(5);
        VBox vBox2 = new VBox(timeConstraintsLabel, super.startEndTimeSelection());
        vBox2.setSpacing(5);
        VBox vBox4 = new VBox(durationLabel, hoursHBox, minutesHBox);
        vBox4.setSpacing(5);
        VBox vBox5 = new VBox(durationChoiceHBox, durationExplainLabel);
        vBox5.setAlignment(Pos.CENTER);
        VBox vBox3 = new VBox(occurrencesHBox, vBox5, this.spaceDurationHBox);
        vBox3.setSpacing(5);

        VBox autoEventVBox = new VBox(titleHBox, separator1, vBox1, separator2, vBox2,
                separator3, vBox4, separator5, vBox3, separator4, createEventButton);
        autoEventVBox.setAlignment(Pos.CENTER);
        autoEventVBox.setSpacing(8);

        root.setCenter(autoEventVBox);
        this.getChildren().add(root);
    }

    public void setController(Controller controller) {

        this.hour.setOnAction(e -> {
            this.spaceDurationHBox.getChildren().clear();
            this.spaceDurationHBox.getChildren().add(hoursDurationHBox);
            HBox.setHgrow(hoursDurationHBox, Priority.ALWAYS);
            this.spaceDurationHBox.setVisible(true);
        });

/*        this.day.setOnAction(e -> {
            this.spaceDurationHBox.getChildren().clear();
            this.spaceDurationHBox.getChildren().add(daysDurationHBox);
            HBox.setHgrow(daysDurationHBox, Priority.ALWAYS);
            this.spaceDurationHBox.setVisible(true);
        });*/

        this.none.setOnAction(e -> {
            this.spaceDurationHBox.setVisible(false);
        });

/*        this.durationMinuteRadio.setOnAction(e -> {
            this.timeDurationHBox.getChildren().clear();
            this.timeDurationHBox.getChildren().add(minutesHBox);
            HBox.setHgrow(minutesHBox, Priority.ALWAYS);
            this.timeDurationHBox.setVisible(true);
        });

        this.durationHourRadio.setOnAction(e -> {
            this.timeDurationHBox.getChildren().clear();
            this.timeDurationHBox.getChildren().add(hoursHBox);
            HBox.setHgrow(hoursHBox, Priority.ALWAYS);
            this.timeDurationHBox.setVisible(true);
        });*/


        createEventButton.setOnAction(e -> {
            if (titleField.getText() != null && startDayPick.getValue() != null && endDayPick.getValue()
                    != null && startTimeField.getText() != null && endTimeField != null &&
                    this.startDayPick.getValue().isBefore(endDayPick.getValue())) {
                try {
                    ArrayList<String> start = new ArrayList<>(List.of(startTimeField.getText().split(":")));
                    int startHour = Integer.parseInt(start.get(0));
                    int startMin = Integer.parseInt(start.get(1));
                    ArrayList<String> end = new ArrayList<>(List.of(endTimeField.getText().split(":")));
                    int endHour = Integer.parseInt(end.get(0));
                    int endMin = Integer.parseInt(end.get(1));
                    int durationMin = Integer.parseInt(minuteDurationField.getText());
                    if ((!(startHour == endHour && startMin == endMin)) && (0 <= startHour && startHour <= 23 && 0 <= endHour && endHour <= 24 && startHour <= endHour) &&
                            (0 <= startMin && startMin <= 59 && 0 <= endMin && endMin <= 59) && (durationMin < 61)) {
                        if (validSpace(startHour, endHour, startMin, endMin)) {
                            System.out.println("Add auto event");
                        }
                        else {controller.handleErrorFound();}
                    }
                    else {controller.handleErrorFound();}
                }
                catch (Exception exception) {
                    controller.handleErrorFound();
                }
            }
            else {controller.handleErrorFound();}
        });
    }

    public void clearFields() {
        titleField.clear();
        startDayPick.getEditor().clear();
        startDayPick.setValue(null);
        endDayPick.getEditor().clear();
        endDayPick.setValue(null);
        startTimeField.clear();
        startTimeField.setPromptText("e.g. 11:15");
        endTimeField.clear();
        endTimeField.setPromptText("e.g. 16:30");
        tagCombo.getSelectionModel().clearSelection();
        occurrencesField.clear();
        occurrencesField.setPromptText("e.g. 4");
        this.day.setSelected(false);
        this.hour.setSelected(false);
        this.none.setSelected(false);
        hoursField.clear();
        hoursField.setPromptText("e.g. 5");
        daysField.clear();
        daysField.setPromptText("e.g. 1");
    }

    public boolean validSpace(int sHour, int eHour, int sMin, int eMin) {
        if (noneRadio.isSelected()) {
            System.out.println();
            return true;
        }
        else {
            int days = (int) startDayPick.getValue().datesUntil(endDayPick.getValue()).count() + 1;
            sHour = sHour + sMin/60;
            eHour = eHour + eMin/60;
            int dayHours = eHour - sHour;
            int totalHours = days*dayHours;

            int requiredTime = Integer.parseInt(occurrencesField.getText()) * (Integer.parseInt(hourDurationField.getText()) +
                    Integer.parseInt(minuteDurationField.getText())/60);

            if (requiredTime <= totalHours) {
                return true;
            }
        }
        return false;
    }
}
