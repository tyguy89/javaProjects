package com.example.sexyscheduler;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class EventGraphic extends HBox {

    String tag;
    String titleString;

    public EventGraphic(EventBase event, double width, String color){
        titleString = event.title;
        Text name;

        if(event instanceof AppointmentEvent){
            if(((AppointmentEvent) event).siblings != null){
                name = new Text(titleString+"*");
            }else{
                name = new Text(titleString);
            }
        }else{
            name = new Text(titleString);
        }

        tag = event.tag;
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
