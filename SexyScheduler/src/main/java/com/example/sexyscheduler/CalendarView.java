package com.example.sexyscheduler;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.Screen;

import java.lang.*;
import java.util.ArrayList;
import java.util.Hashtable;


public class CalendarView extends StackPane implements ModelListener, iModelListener {
    CalendarModel model;
    IModel imodel;
    Filter_View filterView = new Filter_View();

    int numWeeks;
    double cellHeight, cellWidth;
    double width, height;

    HBox[] week = new HBox[5];
    VBox calendar =new VBox();

    HBox dateInfo = new HBox();
    Label year = new Label();
    Label month = new Label();
    Button prevMonth = new Button();
    Button nextMonth = new Button();
    HBox monthBar = new HBox(5);
    DateBar dayNames = new DateBar();

    VBox root = new VBox(5);

    public CalendarView(CalendarModel model, IModel imodel){
        this.model = model;
        this.imodel = imodel;
        this.imodel.addSubscriber(this);
        this.model.addSubscribers(this);

        //initial filters
        filterView.setIModel(imodel);
        filterView.add_filter("School");
        filterView.add_filter("Work");
        filterView.add_filter("Other");
        //year/month bar
        month.setText(imodel.getMonthName());
        year.setText(String.valueOf(imodel.getActualYear()));
        dateInfo.getChildren().addAll(month,new Label(":"),year);
        HBox leftSpacing = new HBox();
        HBox.setHgrow(leftSpacing,Priority.SOMETIMES);
        HBox rightSpacing = new HBox();
        HBox.setHgrow(rightSpacing,Priority.SOMETIMES);
        monthBar.getChildren().addAll(prevMonth,leftSpacing,dateInfo,rightSpacing,nextMonth);
        for (int i = 0; i < week.length; i++) {
            week[i] = new HBox();
        }
        initializeMonth();
        root.setAlignment(Pos.TOP_CENTER);

        Rectangle2D ll = Screen.getPrimary().getBounds();
        this.setMaxSize(ll.getWidth(),ll.getHeight());
        cellWidth = ll.getWidth()/7;
        cellHeight = ll.getHeight()/5;
        root.getChildren().addAll(monthBar,calendar,filterView);

        prevMonth.setOnAction(e->imodel.prevMonth());
        nextMonth.setOnAction(e->imodel.nextMonth());
        this.getChildren().add(root);
        this.setOnMouseClicked(this::mouseClicked);
        this.getStylesheets().add(this.getClass().getResource("styles.css").toExternalForm());

        calendar.setId("calendar");
        monthBar.setId("cal-top");
        nextMonth.setId("right-arrow");
        prevMonth.setId("left-arrow");
        filterView.setId("cal-bot");
    }

    /**
     * @param newWidth: new width the calendar should be.
     * scales the width of the calendar based on the new width
     */
    public void changeWidth(Number newWidth){
        cellWidth = (newWidth.doubleValue()/7);
        for (int i = 0; i < numWeeks; i++) {
            week[i].setPrefWidth(cellWidth*7);
        }
        root.setPrefWidth(cellWidth*7);
        this.setWidth(cellWidth*7+50);
    }

    /**
     * @param newHeight: new height the calendar should be.
     * scales the height of the calendar based on the new height
     */
    public void changeHeight(Number newHeight){
        cellHeight = ((newHeight.doubleValue())/numWeeks);
        calendar.setMaxHeight((cellHeight*numWeeks)- (filterView.getHeight()*2));
        for (int i = 0; i < numWeeks; i++) {
            week[i].setPrefHeight(cellHeight-(filterView.getHeight()/numWeeks - month.getHeight()/numWeeks));
        }
    }

    /**
     * @param event: mouse event
     * handels what to do when a mouse press happens on the calendar
     */
    public void mouseClicked(MouseEvent event){
        if(event.getTarget().getClass().equals(DayGraphic.class)){
            if(event.getButton() == MouseButton.PRIMARY) {
                imodel.weekSelected = false;
                this.selectDay(event);
            }else{
                imodel.weekSelected = true;
                this.selectDay(event);
            }
        }

/*        if(event.getTarget().getClass().equals(EventGraphic.class)){
            imodel.setSelectedEvent(((EventGraphic)event.getTarget()).event);
        }*/
    }

    /**
     * @param event: mouse clicked event
     * sets the background colour of the day and week to
     * corresponding to where the mouse is clicked
     */
    public void selectDay(MouseEvent event){
        if (imodel.getDaySelected() == null) {
            imodel.setDaySelected((DayGraphic) event.getTarget());
            imodel.getDaySelected().getParent().setStyle("-fx-background-color:rgb(114,229,212)");
            for(int day = 0; day < 7; day++){
                ((HBox)imodel.getDaySelected().getParent()).getChildren().get(day).setStyle("-fx-background-color:transparent");
            }
            if(imodel.week != null){
                if (imodel.week.value == 0) {
                    for (int day = 0; day < 7; day++) {
                        if (Integer.parseInt(((DayGraphic) (this.week[0].getChildren().get(day))).day.getText()) > 7) {
                            (this.week[0].getChildren().get(day)).setStyle("-fx-background-color:rgb(220,220,220)");
                        }
                    }
                }
            }
            imodel.getDaySelected().setSelected();
        } else {
            imodel.getDaySelected().unselect();
            imodel.getDaySelected().getParent().setStyle("-fx-background-color:transparent");
            for(int day = 0; day < 7; day++){
                ((HBox)imodel.getDaySelected().getParent()).getChildren().get(day).setStyle("-fx-background-color:transparent");
            }

            for (int day = 0; day < 7; day++) {
                if (Integer.parseInt(((DayGraphic) (this.week[0].getChildren().get(day))).day.getText()) > 7) {
                    (this.week[0].getChildren().get(day)).setStyle("-fx-background-color:rgb(220,220,220)");
                }
            }

            imodel.setDaySelected((DayGraphic) event.getTarget());
            if(!imodel.weekSelected) {
                ((DayGraphic) event.getTarget()).setSelected();
            }else{
                imodel.getDaySelected().unselect();
            }
            imodel.week = model.getWeekHoldingDayByNames(imodel.getActualYear(), imodel.getMonthName(),Integer.parseInt(imodel.getDaySelected().day.getText()));
            if(imodel.week.value == 0){
                for(int day = 0; day < 7; day++){
                    this.week[0].getChildren().get(day).setStyle("-fx-background-color:transparent");
                }
            }
            imodel.getDaySelected().getParent().setStyle("-fx-background-color:rgb(114,229,212);");
            if(!imodel.weekSelected) {
                imodel.getDaySelected().setSelected();
            }

        }
    }

    /**
     * decided which events to show based on
     */
    public void filtersChanged() {
        for (int week = 0; week < numWeeks; week++) {
            for (int day = 0; day < 7; day++) {
                DayGraphic dayGraphic = (DayGraphic)this.week[week].getChildren().get(day);
                dayGraphic.changeFilters(imodel.getSelectedFilters());
            }
        }
    }

    /**
     * changes the colors of the events on the calendar based on the
     * filters selected
     */
    public void colorsChanged() {
        for (int week = 0; week < numWeeks; week++) {
            for (int day = 0; day < 7; day++) {
                DayGraphic dayGraphic = (DayGraphic)this.week[week].getChildren().get(day);
                dayGraphic.changeFilterColor(imodel.getFilterColorByName());
            }
        }
    }

    /**
     * since we are only storing ta most 35 day graphics when the month
     * changes we need to clear all the data of those objects
     */
    public void initializeMonth(){
        this.calendar.getChildren().clear();
        this.calendar.getChildren().add(dayNames);
        ArrayList<MyDay> daysInMonth = model.getDaysInMonthOnly(imodel.year, imodel.month);
        Hashtable<String,String> color = imodel.getFilterColorByName();
        if(imodel.getDaySelected() != null) {
            imodel.getDaySelected().getParent().setStyle("-fx-background-color:transparent");
        }
        numWeeks = daysInMonth.size()/7;
        this.year.setText(""+imodel.getActualYear());
        this.month.setText(imodel.getMonthName());
        for (int week = 0; week < numWeeks; week++) {
            this.week[week].getChildren().clear();
            for (int day = 0; day < 7; day++) {
                this.week[week].getChildren().add(new DayGraphic(Double.MAX_VALUE, Double.MAX_VALUE, daysInMonth.get((week * 7) + day).value));
                for (EventBase event : daysInMonth.get((week * 7) + day).events) {
                    ((DayGraphic) (this.week[week].getChildren().get(day))).addEvent(event, cellWidth, color.get(event.tag));
                    ((DayGraphic) (this.week[week].getChildren().get(day))).changeFilters(imodel.getSelectedFilters());
                }
                if((week == 0) && (Integer.parseInt(((DayGraphic) (this.week[week].getChildren().get(day))).day.getText()) >7)){
                    (this.week[week].getChildren().get(day)).setStyle("-fx-background-color:rgb(220,220,220)");
                }
            }
            this.calendar.getChildren().add(this.week[week]);
        }
        imodel.setDaySelected(null);
        this.setPadding(new Insets(0,0,0,10));
    }

    /**
     * re initilize the months,height and width when the month changes
     */
    public void monthChanged() {
        initializeMonth();
        this.changeHeight(this.getHeight());
        this.changeWidth(this.getWidth());

    }

    /**
     * if the model changes update the events on the calendar
     */
    public void modelChanged(){
        ArrayList<MyDay> daysInMonth = model.getDaysInMonthOnly(imodel.year, imodel.month);
        Hashtable<String,String> color = imodel.getFilterColorByName();
        month.setText(imodel.getMonthName());
        year.setText(""+imodel.getActualYear());
        numWeeks = daysInMonth.size()/7;
        for (int week = 0; week < numWeeks; week++) {
            for (int day = 0; day < 7; day++) {
                for(EventBase event: daysInMonth.get((week*7)+day).events){
                    ((DayGraphic)(this.week[week].getChildren().get(day))).addEvent(event,cellWidth,color.get(event.tag));
                    ((DayGraphic)(this.week[week].getChildren().get(day))).changeFilters(imodel.getSelectedFilters());
                }
            }
        }
        this.changeWidth(this.getWidth());
        this.changeHeight(this.getHeight());
    }

    public void iModelChanged(){}


}
