package com.example.sexyscheduler;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Hashtable;

public class WeekEventView extends ScrollPane implements iModelListener, ModelListener {

    Color mainColor;
    VBox[] vBoxes;
    Label[] labels;
    VBox root;
    VBox weekOverview;
    CalendarModel model;
    IModel iModel;

    MyWeek selectedWeek;

    Controller controller;

    /* These ArrayLists will eventually hold Events. This is so that we can have a variable amount of
     * events in each day */
    private ArrayList<EventBase> durationEvents;
    private ArrayList<EventBase> deadlineEvents;

    public WeekEventView() {
        mainColor = Color.rgb(193, 255, 199);
        root = new VBox(5);
        root.setMinSize(200,500);

        weekOverview = new VBox(5);

        VBox day1 = new VBox(5);
        day1.setMinHeight(120);
        Label day1Label = new Label("Sunday");
        day1.getChildren().add(day1Label);

        VBox day2 = new VBox(5);
        day2.setMinHeight(120);
        Label day2Label = new Label("Monday");
        day2.getChildren().add(day2Label);

        VBox day3 = new VBox(5);
        day3.setMinHeight(120);
        Label day3Label = new Label("Tuesday");
        day3.getChildren().add(day3Label);

        VBox day4 = new VBox(5);
        day4.setMinHeight(120);
        Label day4Label = new Label("Wednesday");
        day4.getChildren().add(day4Label);

        VBox day5 = new VBox(5);
        day5.setMinHeight(120);
        Label day5Label = new Label("Thursday");
        day5.getChildren().add(day5Label);

        VBox day6 = new VBox(5);
        day6.setMinHeight(120);
        Label day6Label = new Label("Friday");
        day6.getChildren().add(day6Label);

        VBox day7 = new VBox(5);
        day7.setMinHeight(120);
        Label day7Label = new Label("Saturday");
        day7.getChildren().add(day7Label);

        weekOverview.getChildren().addAll(day1, day2, day3, day4, day5, day6, day7);
        vBoxes = new VBox[]{day1, day2, day3, day4, day5, day6, day7};
        labels = new Label[]{day1Label, day2Label, day3Label, day4Label, day5Label, day6Label, day7Label};

        root.getChildren().addAll(weekOverview);
        this.setContent(root);
        this.setHbarPolicy(ScrollBarPolicy.NEVER);
        this.setVbarPolicy(ScrollBarPolicy.ALWAYS);
        this.setId("event-views");
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    /**
     * Links an event in the view, when clicked, to EventOverview.
     */
    private void linkControllerEvents(WeekEventGraphic graphic, EventBase event, MyDay day) {
        graphic.setOnMouseClicked(e -> controller.handleEventClicked(event, day));
    }

    public void setModel(CalendarModel model) {
        this.model = model;
    }

    public void setiModel(IModel iModel) {
        this.iModel = iModel;
        iModelChanged();
    }

    @Override
    public void iModelChanged() {
        Hashtable<String,String> colors = model.getFilterColorByName();
        selectedWeek = iModel.week;
        MyDay day;
        weekOverview.getChildren().clear();
        for (int i = 0; i < labels.length; i = i + 1) {
            vBoxes[i].getChildren().clear();
            vBoxes[i].getChildren().add(labels[i]);
            weekOverview.getChildren().add(vBoxes[i]);
        }
        if (iModel.weekSelected && iModel.week != null) {
            for (int i = 0; i < vBoxes.length; i++) {
                day = iModel.week.days[i];
                for (EventBase event : day.events) {
                    if(iModel.selectedFilters.contains(event.tag)) {
                        WeekEventGraphic graphic = new WeekEventGraphic(event, colors.get(event.tag));
                        this.linkControllerEvents(graphic, event, day);
                        vBoxes[i].getChildren().add(graphic);
                    }
                }
            }
       }

    }

    @Override
    public void monthChanged() {

    }

    @Override
    public void filtersChanged() {
        iModelChanged();
    }



    @Override
    public void modelChanged() {
        Hashtable<String,String> colors = model.getFilterColorByName();
        if (model.hasDeletedEvent) {
            weekOverview.getChildren().clear();
            for (int i = 0; i < labels.length; i = i + 1) {
                vBoxes[i].getChildren().clear();
                vBoxes[i].getChildren().add(labels[i]);
                weekOverview.getChildren().add(vBoxes[i]);
            }
            if (selectedWeek != null) {
                for (int i = 0; i < vBoxes.length; i++) {
                    MyDay day = selectedWeek.days[i];
                    for (EventBase event : day.events) {
                        if(iModel.selectedFilters.contains(event.tag)) {
                            WeekEventGraphic graphic = new WeekEventGraphic(event, colors.get(event.tag));
                            this.linkControllerEvents(graphic, event, day);
                            vBoxes[i].getChildren().add(graphic);
                        }
                    }
                }
            }
        }
    }

    public void changeHeight(double newVal){
        weekOverview.setPrefHeight(newVal);
        this.setPrefHeight(newVal);
    }
}
