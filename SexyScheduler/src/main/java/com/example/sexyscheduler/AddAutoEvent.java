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

    public AddAutoEvent(Filter_View filters) {
        super(filters);

        BorderPane root = new BorderPane();

        this.spaceDurationHBox = new HBox();
        this.spaceDurationHBox.setVisible(false);
        this.spaceDurationHBox.setAlignment(Pos.CENTER);

        this.timeDurationHBox = new HBox();
        this.timeDurationHBox.setVisible(false);
        this.timeDurationHBox.setAlignment(Pos.CENTER);

        Separator separator1 = new Separator();
        Separator separator2 = new Separator();
        Separator separator3 = new Separator();
        Separator separator4 = new Separator();
        Separator separator5 = new Separator();

        VBox vBox1 = new VBox(dayConstraintsLabel, super.startEndDaySelection());
        vBox1.setSpacing(2);
        VBox vBox2 = new VBox(timeConstraintsLabel, super.startEndTimeSelection());
        vBox2.setSpacing(2);
        VBox vBox4 = new VBox(durationLabel, hoursHBox, minutesHBox);
        vBox4.setSpacing(2);
        VBox vBox5 = new VBox(durationChoiceHBox, durationExplainLabel);
        vBox5.setAlignment(Pos.CENTER);
        VBox vBox3 = new VBox(occurrencesHBox, vBox5, this.spaceDurationHBox);
        vBox3.setSpacing(2);

        VBox autoEventVBox = new VBox(titleHBox, separator1, vBox1, separator2, vBox2,
                separator3, vBox4, separator5, vBox3, separator4, tagHBox, createEventButton);
        autoEventVBox.setAlignment(Pos.CENTER);
        autoEventVBox.setSpacing(4);
        tagCombo.getSelectionModel().selectFirst();
        root.setCenter(autoEventVBox);
        this.getChildren().add(root);
    }

    public void setController(Controller controller) {

        hourRadio.setOnAction(e -> {
            this.spaceDurationHBox.getChildren().clear();
            this.spaceDurationHBox.getChildren().add(hoursDurationHBox);
            HBox.setHgrow(hoursDurationHBox, Priority.ALWAYS);
            this.spaceDurationHBox.setVisible(true);

        });

        noneRadio.setOnAction(e -> {
            this.spaceDurationHBox.setVisible(false);
        });

        createEventButton.setOnAction(e -> {
            if (titleField.getText() != null && startDayPick.getValue() != null && endDayPick.getValue()
                    != null && startTimeField.getText() != null && endTimeField != null &&
                    this.startDayPick.getValue().isBefore(endDayPick.getValue())) {
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
                            String startDayChosen = startDayPick.getValue().toString();
                            ArrayList<String> startDayString = new ArrayList<>(List.of(startDayChosen.split("-")));
                            String endDayChosen = endDayPick.getValue().toString();
                            ArrayList<String> endDayString = new ArrayList<>(List.of(endDayChosen.split("-")));
                            int days = (int) startDayPick.getValue().datesUntil(endDayPick.getValue()).count() + 1;

                            if (hoursField.getText().equals("")) {
                                hoursField.setText("0");
                            }

                            controller.handleAutoEvent(Integer.parseInt(startDayString.get(0)), ModelTranslator.monthsNameByInt.get(Integer.parseInt(startDayString.get(1)) - 1),
                                    Integer.parseInt(startDayString.get(2)), Integer.parseInt(endDayString.get(0)),
                                    ModelTranslator.monthsNameByInt.get(Integer.parseInt(endDayString.get(1)) - 1), Integer.parseInt(endDayString.get(2)), days, startTimeField.getText(),
                                    endTimeField.getText(), Double.parseDouble(hoursField.getText()),
                                    Integer.parseInt(occurrencesField.getText()),
                                    hourDurationField.getText() + "," + minuteDurationField.getText(), titleField.getText(),
                                    "blue", tagCombo.getSelectionModel().getSelectedItem());
                        }
                        else {controller.handleErrorFound();}
                    }
                    else {controller.handleErrorFound();}
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
        hourRadio.setSelected(false);
        noneRadio.setSelected(false);
        hoursField.clear();
        hoursField.setPromptText("e.g. 5");
        daysField.clear();
        daysField.setPromptText("e.g. 1");
    }

    public boolean validSpace(int sHour, int eHour, int sMin, int eMin) {
        int days = (int) startDayPick.getValue().datesUntil(endDayPick.getValue()).count() + 1;
        double startTime = sHour + (double) sMin/60;
        double endTime  = eHour + (double) eMin/60;
        double dayHours = endTime - startTime; // available hours in a day
        double totalHours = days*dayHours; // total available hours given constraints

        if (hoursField.getText().equals("")) {
            hoursField.setText("0");
        }

/*        if (dayHours < Integer.parseInt(hoursField.getText())) {
            return false;
        }*/

        double coolDown = 0;
        if (hourRadio.isSelected()) {
            if (Double.parseDouble(hoursField.getText()) > 24) {
                coolDown = Math.ceil(Double.parseDouble(hoursField.getText()) / 24.0)*24;
            } else {
                coolDown = Math.ceil(Double.parseDouble(hoursField.getText()));
            }
        }

        double requiredTime = Double.parseDouble(occurrencesField.getText()) * (Double.parseDouble(hourDurationField.getText()) +
                Double.parseDouble(minuteDurationField.getText())/60 + coolDown);

        if (requiredTime <= totalHours) {
            return true;
        }
        return false;
    }
}
