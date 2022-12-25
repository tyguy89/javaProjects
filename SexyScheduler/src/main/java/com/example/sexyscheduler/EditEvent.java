package com.example.sexyscheduler;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class EditEvent extends StackPane {
    private Stage popUp;
    private EditEventView editView;
    public EditEvent(EditEventView edit) {
        this.editView = edit;

        this.popUp = new Stage();
        this.popUp.setTitle("Edit Event");

        Scene editEventView = new Scene(this.editView);
        editEventView.getStylesheets().add(this.getClass().getResource("styles.css").toExternalForm());
        this.popUp.setScene(editEventView);
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
}
