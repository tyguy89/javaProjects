package com.example.sexyscheduler;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class WeekEventGraphic extends VBox{
    public EventBase event;
    public VBox root = new VBox(10);

    public WeekEventGraphic(EventBase evnt,String color){
        event = evnt;
        if(event instanceof AppointmentEvent){
            if(((AppointmentEvent) event).siblings == null) {
                root.getChildren().addAll(new Label(event.title), new Label(((AppointmentEvent) event).start + " - " + ((AppointmentEvent) event).end));
            }else{
                root.getChildren().addAll(new Label(event.title+"*"), new Label(((AppointmentEvent) event).start + " - " + ((AppointmentEvent) event).end));
            }
        }if( event instanceof DeadlineEvent){
            root.getChildren().addAll(new Label(event.title), new Label(((DeadlineEvent) event).time));

        }
        root.setAlignment(Pos.CENTER);
        this.getStylesheets().add(this.getClass().getResource("styles.css").toExternalForm());
        this.getChildren().add(root);
        this.setAlignment(Pos.CENTER);
        this.setStyle("-fx-background-color:"+color);
        this.setId("event-views");

    }
}
