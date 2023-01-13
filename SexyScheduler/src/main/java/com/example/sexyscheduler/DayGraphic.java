package com.example.sexyscheduler;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

public class DayGraphic extends VBox {
        Text day;
        ArrayList<EventBase> events = new ArrayList<>();
        ArrayList<EventGraphic> eventGraphics = new ArrayList<>();
        double heightOfItems;
    public DayGraphic(double width, double height, int day){
        this.day = new Text(""+day);
        this.getChildren().add(this.day);
       // this.setMaxSize(width,height);
        this.setPrefSize(Double.MAX_VALUE,Double.MAX_VALUE);
        this.setMinSize(10,10);
        this.getStylesheets().add(this.getClass().getResource("styles.css").toExternalForm());
        this.setId("day");
        StackPane.setAlignment(this.day, Pos.TOP_LEFT);
        StackPane.setMargin(this.day, new Insets(5,0,0,8));
    }

    /**
     * sets background color of day if its been clicked on
     */
    public void setSelected(){
        this.setStyle("-fx-border-color:rgb(0,0,0);\n-fx-border-width:1.5;\n-fx-background-color:rgb(115,161,98);");
    }

    /**
     * removes background color of day if its not currently
     * selected
     */
    public void unselect(){
        this.setStyle("-fx-border-color:rgb(0,0,0);\n-fx-border-width:1;\n-fx-background-color:transparent;");
    }

    /**
     * @param event: to be added
     * @param width: initial event width
     * @param color: color of the event
     */
    public void addEvent(EventBase event,double width, String color) {
        if (events == null) {
            EventGraphic curEvent = new EventGraphic(event,width,color);
            eventGraphics.add(curEvent);
            this.getChildren().add(curEvent);
            events.add(event);

        } else {
            if (!events.contains(event)) {
                EventGraphic curEvent = new EventGraphic(event,width,color);
                eventGraphics.add(curEvent);
                this.getChildren().add(curEvent);
                events.add(event);

            }

        }
    }

    /**
     * @param tags: the currently selected filters
     * removes all the events from the day graphic that do not
     * mathc the selected filter
     */
    public void changeFilters(ArrayList<String> tags){
        this.getChildren().clear();
        this.getChildren().add(day);

        for(EventGraphic event: eventGraphics){
            if(tags.contains(event.tag)){
                this.getChildren().add(event);
            }
        }
    }

    /**
     * @param colors: colors the events should be
     * changes the colors of events basesd on their imodel
     * colors
     */
    public void changeFilterColor(Hashtable<String,String> colors){
        for (EventGraphic event: eventGraphics){
            event.changeColor(colors.get(event.tag));
        }
    }

}
