package com.example.sexyscheduler;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class CreateEventView extends StackPane implements ModelListener{

    private Stage popUp;
    private EventTabsView tabViews;
    CalendarModel model;

    public CreateEventView(EventTabsView addEventTabs,CalendarModel model) {
        this.model = model;
        this.tabViews = addEventTabs;
        this.popUp = new Stage();
        this.popUp.setTitle("Add Event");

        Scene addEventView = new Scene(this.tabViews);
        addEventView.getStylesheets().add(this.getClass().getResource("styles.css").toExternalForm());
        this.popUp.setScene(addEventView);
        //this.popUp.setOnCloseRequest(e->closethis());
    }

    public void modelChanged() {
        if(model.addView){
            this.popUp.show();
        }else{
            this.popUp.hide();
            this.tabViews.clearAddView();
        }
    }

/*    public void closethis(){
        this.popUp.close();
        model.addView = false;
        modelChanged();
    }*/

}
