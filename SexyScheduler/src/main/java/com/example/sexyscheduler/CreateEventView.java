package com.example.sexyscheduler;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class CreateEventView extends StackPane implements ModelListener{

    private Stage popUp;
    private EventTabsView tabViews;

    public CreateEventView(EventTabsView addEventTabs) {
        this.tabViews = addEventTabs;
        this.popUp = new Stage();
        this.popUp.setTitle("Add Event");

        Scene addEventView = new Scene(this.tabViews);
        addEventView.getStylesheets().add(this.getClass().getResource("styles.css").toExternalForm());
        this.popUp.setScene(addEventView);
    }

    public void modelChanged() {
        if (!this.popUp.isShowing()) {
            this.popUp.show();
        }
        else {
            this.popUp.hide();
            this.tabViews.clearAddView();
        }
    }

}
