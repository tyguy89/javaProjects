package com.example.sexyscheduler;

import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class DeadlineEventView extends CreateEventViewItems{
    public DeadlineEventView(Filter_View filters) {
        super(filters);

        BorderPane root = new BorderPane();

        VBox deadlineVBox = new VBox(titleHBox, super.singleDaySelection(), singleTimeHBox, tagHBox, createDeadlineButton);
        deadlineVBox.setAlignment(Pos.CENTER);
        deadlineVBox.setSpacing(30);

        root.setCenter(deadlineVBox);
        this.getChildren().add(root);

    }
    public void setController(Controller controller) {

        createDeadlineButton.setOnAction(e -> {
            if (this.titleField.getText() != null && this.startDayPick.getValue() != null && this.startTimeField.getText() != null
            && tagCombo.getSelectionModel().getSelectedItem() != null) {
                //try {
                    String dayChosen = startDayPick.getValue().toString();
                    ArrayList<String> dayString = new ArrayList<>(List.of(dayChosen.split("-")));
                    controller.handleDeadlineEvent(Integer.parseInt(dayString.get(0)), ModelTranslator.monthsNameByInt.get(Integer.parseInt(dayString.get(1))-1),
                            Integer.parseInt(dayString.get(2)), titleField.getText(), startTimeField.getText(),tagCombo.getSelectionModel().getSelectedItem());
                    clearFields();

/*                } catch (Exception exception) {
                    controller.handleErrorFound();
                }*/
            } else {
                controller.handleErrorFound();
            }
        });
    }

    public void clearFields() {
        titleField.clear();
        startDayPick.getEditor().clear();
        startDayPick.setValue(null);
        startTimeField.clear();
        startTimeField.setPromptText("e.g. 11:15");
        tagCombo.getSelectionModel().clearSelection();
    }

}
