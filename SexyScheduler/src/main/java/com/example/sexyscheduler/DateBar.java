package com.example.sexyscheduler;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;

public class DateBar extends StackPane {
    int dayWidth = 300;
    public DateBar(){
        HBox sunday =  new HBox(new Label("Sun"));
        HBox monday =  new HBox(new Label("Mon"));
        HBox tuesday =  new HBox(new Label("Tue"));
        HBox wednesday =  new HBox(new Label("Wed"));
        HBox thursday =  new HBox(new Label("Thu"));
        HBox friday =  new HBox(new Label("Fri"));
        HBox saturday =  new HBox(new Label("Sat"));
        sunday.setId("day");
        monday.setId("day");
        tuesday.setId("day");
        wednesday.setId("day");
        thursday.setId("day");
        friday.setId("day");
        saturday.setId("day");

        HBox allDays = new HBox(sunday,monday,tuesday,wednesday,thursday,friday,saturday);
        HBox.setHgrow(allDays, Priority.ALWAYS);
        this.getChildren().add(allDays);

    }
}
