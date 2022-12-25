package com.example.sexyscheduler;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class RepeatEvent extends CreateEventViewItems {
    Hashtable<String,Integer> mapModes = new Hashtable<>();
    public RepeatEvent(Filter_View filters) {
        super(filters);

        BorderPane root = new BorderPane();
        VBox linked = new VBox(linkedChoiceHBox, unlinkExplainLabel, linkExplainLabel);
        linked.setAlignment(Pos.CENTER);
/*        VBox vBox1 = new VBox(dayConstraintsLabel, super.startEndDaySelection());
        vBox1.setSpacing(5);*/

        VBox repeatVBox = new VBox(titleHBox, super.startEndTimeSelection(), tagHBox,
                super.startEndDaySelection(), repeatHBox, createAllEventsButton);
        repeatVBox.setPrefSize(520, 420);
        repeatVBox.setAlignment(Pos.CENTER);
        repeatVBox.setSpacing(30);
        repeatVBox.setPadding(new Insets(20, 20, 20, 20));

        mapModes.put("Every Day",0);
        mapModes.put("Every Week",1);
        mapModes.put("Every Month",2);
        tagCombo.getSelectionModel().selectFirst();
        root.setCenter(repeatVBox);
        this.getChildren().add(root);
    }

    public void setController(Controller controller) {

        createAllEventsButton.setOnAction(e -> {
            if (this.titleField.getText() != null && this.startTimeField.getText() != null && this.endTimeField != null
                && tagCombo.getSelectionModel().getSelectedItem() != null && startDayPick.getValue() != null && endDayPick.getValue() != null) {

                ArrayList<String> startDayString = new ArrayList<>(List.of(startDayPick.getValue().toString().split("-")));
                ArrayList<String> endDayString = new ArrayList<>(List.of(endDayPick.getValue().toString().split("-")));

                controller.handleRepeatEvent(titleField.getText(), startTimeField.getText(), endTimeField.getText(), tagCombo.getSelectionModel().getSelectedItem(),mapModes.get(repeatCombo.getSelectionModel().getSelectedItem()),
                        Integer.parseInt(startDayString.get(0)), ModelTranslator.monthsNameByInt.get(Integer.parseInt(startDayString.get(1))-1),Integer.parseInt(startDayString.get(2)),
                        Integer.parseInt(endDayString.get(0)), ModelTranslator.monthsNameByInt.get(Integer.parseInt(endDayString.get(1))-1), Integer.parseInt(endDayString.get(2)));
            }else{
                controller.handleErrorFound();

            }
        });
    }

    public void clearFields() {

    }
}
