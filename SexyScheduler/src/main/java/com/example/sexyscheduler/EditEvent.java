package com.example.sexyscheduler;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class EditEvent extends StackPane implements ModelListener {
    private Stage popUp;
    private EditEventView editView;
    private CalendarModel model;
    public EditEvent(EditEventView edit) {
        this.editView = edit;

        this.popUp = new Stage();
        this.popUp.setTitle("Edit Event");

        Scene editEventView = new Scene(this.editView);
        editEventView.getStylesheets().add(this.getClass().getResource("styles.css").toExternalForm());
        editView.setId("cust-background");
        this.popUp.setScene(editEventView);
    }

    public void setModel(CalendarModel model) {
        this.model = model;
    }

    public void show() {
        this.popUp.show();
    }
    public void hide() {
        this.popUp.hide();
    }

    public void setFields(EventBase e, MyDay day, MyMonth month, MyYear year) {
        this.editView.initiateEdit(e, day, month, year);
    }

    public void modelChanged() {
        if (model.showEditEventView) {
            this.show();
        }
        else {
            this.hide();
        }
    }
}
