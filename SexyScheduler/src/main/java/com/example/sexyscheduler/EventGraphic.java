package com.example.sexyscheduler;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class EventGraphic extends HBox {

    String tag;
    String titleString;

    public EventGraphic(EventBase event, double width, String color){
        titleString = event.title;
        Label name;

        tag = event.tag;
        name = new Label(titleString);
        name.setAlignment(Pos.CENTER);
        this.getChildren().add(name);
        this.getStylesheets().add(this.getClass().getResource("styles.css").toExternalForm());
        this.setId("eventGraphic");
        this.setStyle("-fx-background-color:" + color + ";");


    }

    /**
     * @param color the color the event should be
     * changes the color of the event to be the param color
     */
    public void changeColor(String color){
        this.setStyle("-fx-background-color:"+color+";");
    }
}
