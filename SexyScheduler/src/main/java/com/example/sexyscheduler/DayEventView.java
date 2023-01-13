package com.example.sexyscheduler;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Hashtable;

public class DayEventView extends ScrollPane implements iModelListener, ModelListener {

    Color mainColor;
    VBox root;
    VBox eventVisualContainer;
    VBox[] eventVisualContainers;
    HBox[] timesAndEventsContainers;
    CalendarModel model;
    IModel iModel;

    ArrayList<EventBase> selectedDayEvents;

    Controller controller;


    private String[] times;

    public DayEventView() {
        times = new String[]{"0:00", "1:00", "2:00", "3:00", "4:00", "5:00", "6:00", "7:00", "8:00", "9:00",
        "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00",
        "23:00"};
        mainColor = Color.rgb(193, 255, 199);

        root = new VBox();
/*        root.setMinSize(280,1860);
        root.setPrefSize(280,1860);
        root.setMaxSize(400,1860);*/
        VBox.setVgrow(root,Priority.ALWAYS);
        timesAndEventsContainers = new HBox[]{new HBox(), new HBox(), new HBox(), new HBox(), new HBox(), new HBox(), new HBox(),
                new HBox(), new HBox(), new HBox(), new HBox(), new HBox(), new HBox(), new HBox(), new HBox(), new HBox(), new HBox(),
                new HBox(), new HBox(), new HBox(), new HBox(), new HBox(), new HBox(), new HBox()};

        eventVisualContainer = new VBox();

        eventVisualContainers = new VBox[]{new VBox(), new VBox(), new VBox(), new VBox(), new VBox(), new VBox(), new VBox(),
                new VBox(), new VBox(), new VBox(), new VBox(), new VBox(), new VBox(), new VBox(), new VBox(), new VBox(),
                new VBox(), new VBox(), new VBox(), new VBox(), new VBox(), new VBox(), new VBox(), new VBox()};

        for (int i = 0; i < timesAndEventsContainers.length; i = i + 1) {
            Label timeLabel = new Label(times[i]);
            timesAndEventsContainers[i].getChildren().addAll(timeLabel, eventVisualContainers[i]);
            timeLabel.setMinWidth(20);
            timeLabel.setPadding(new Insets(0, 5, 60, 0));
            eventVisualContainers[i].setMinSize(180, 60);
            root.getChildren().add(timesAndEventsContainers[i]);

        }

        this.setContent(root);
        this.setHbarPolicy(ScrollBarPolicy.NEVER);
        this.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
/*        this.setMinSize(Double.MIN_VALUE, Double.MIN_VALUE);
        this.setPrefSize(200,700);
        this.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);*/
        this.getStylesheets().add(this.getClass().getResource("styles.css").toExternalForm());
        this.setId("event-views");
    }

    public void setModel(CalendarModel model) {
        this.model = model;
    }

    public void setIModel(IModel iModel) {
        this.iModel = iModel;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setEventControllerLink(EventGraphic eventGraphic, EventBase event, MyDay day) {
        eventGraphic.setOnMouseClicked(e -> controller.handleEventClicked(event, day));
    }


    @Override
    public void iModelChanged() {
        for (VBox container: eventVisualContainers) {
            container.getChildren().clear();
        }
        if (iModel.daySelected != null) {
            selectedDayEvents = iModel.getDaySelected().events;
            MyDay clickedDay = model.getDayByNames(iModel.getActualYear(), iModel.getMonthName(),Integer.parseInt(iModel.getDaySelected().day.getText()));

            // beginning event - to combat duplication issues that happen for some reason

            for (EventBase event: selectedDayEvents) {
                    if(event instanceof AppointmentEvent) {
                        if (iModel.selectedFilters.contains(event.tag)) {
                            String[] time = ((AppointmentEvent) event).start.split(":");
                            Hashtable<String, String> colors = model.getFilterColorByName();
                            EventGraphic newEventGraphic = new EventGraphic(event, 230, colors.get(event.tag));
                            this.setEventControllerLink(newEventGraphic, event, clickedDay);
                            eventVisualContainers[Integer.parseInt(time[0])].getChildren().
                                    add(newEventGraphic);
                        }
                    }

                if(event instanceof DeadlineEvent){
                    if(iModel.selectedFilters.contains(event.tag)) {
                        String[] time = ((DeadlineEvent) event).time.split(":");
                        Hashtable<String, String> colors = model.getFilterColorByName();
                        EventGraphic newEventGraphic = new EventGraphic(event, 230, colors.get(event.tag));
                        this.setEventControllerLink(newEventGraphic, event, clickedDay);
                        eventVisualContainers[Integer.parseInt(time[0])].getChildren().
                                add(newEventGraphic);
                    }
                }
                }
            }

        }

    public void monthChanged(){}
    public void filtersChanged(){
        iModelChanged();
    }

    @Override
    public void modelChanged() {
        if (model.hasDeletedEvent) {
        for (VBox container : eventVisualContainers) {
            container.getChildren().clear();
        }
        MyDay clickedDay = model.clickedDay;
        // beginning event - to combat duplication issues that happen for some reason
        //Event previousEvent = new Event("null", "null", "null", 0, "null", "null");
            selectedDayEvents = clickedDay.events;
        for (EventBase event: selectedDayEvents) {
            if(event instanceof AppointmentEvent) {
                if(iModel.selectedFilters.contains(event.tag)) {
                    String[] time = ((AppointmentEvent) event).start.split(":");
                    Hashtable<String, String> colors = model.getFilterColorByName();
                    EventGraphic newEventGraphic = new EventGraphic(event, 230, colors.get(event.tag));
                    this.setEventControllerLink(newEventGraphic, event, clickedDay);
                    eventVisualContainers[Integer.parseInt(time[0])].getChildren().
                            add(newEventGraphic);
                }
            }else if(event instanceof DeadlineEvent) {
                if (iModel.selectedFilters.contains(event.tag)) {
                    String[] time = ((DeadlineEvent) event).time.split(":");
                    Hashtable<String, String> colors = model.getFilterColorByName();
                    EventGraphic newEventGraphic = new EventGraphic(event, 230, colors.get(event.tag));
                    this.setEventControllerLink(newEventGraphic, event, clickedDay);
                    eventVisualContainers[Integer.parseInt(time[0])].getChildren().
                            add(newEventGraphic);
                }
            }
            }
        }
//    }
    }

    public void changeHeight(double newVal){
        root.setPrefHeight(newVal);
        eventVisualContainer.setPrefHeight(newVal);

    }
}
