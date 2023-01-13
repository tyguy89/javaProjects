package com.example.sexyscheduler;

import javafx.scene.layout.HBox;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;

public class IModel {
    int month = LocalDate.now().getMonthValue() - 1;
    int day = LocalDate.now().getDayOfMonth();
    int year = 0;
    DayGraphic daySelected = null;
    boolean weekSelected = false;
    MyWeek week = null;
    EventBase selectedEvent;
    int incr = 0;

    ArrayList<String> selectedFilters = new ArrayList<String>();

    String[] monthNames = {"January","February","March","April","May","June","July","August","September","October","November","December"};

    ArrayList<iModelListener> subscribers = new ArrayList<>();

    public IModel(){
    }

    public void setSelectedEvent(EventBase selectedEvent) {
        this.selectedEvent = selectedEvent;
    }

    public EventBase getSelectedEvent() {
        return selectedEvent;
    }

    /**
     * @return the index of the current year; formated for the model
     */
    public int getYearIdx() {
        return year;
    }

    /**
     * @return the corresponding year based on how far in the future we are
     */
    public int getActualYear(){return LocalDate.now().getYear() + year;}

    /**
     * @return the index of the current month formatted for the model
     */
    public int getMonthIndx() {
        return month;
    }

    /**
     * @return the month name based on the month index
     */
    public String getMonthName(){
        return monthNames[month];
    }

    /**
     * @return: the current day
     */
    public int getDay() {
        return day;
    }

    /**
     * @return the selected day
     */
    public DayGraphic getDaySelected() {
        if (daySelected != null) {
        }
        return daySelected;
    }

    /**
     * @param day: new selected day
     * sets the selected day to be the new DayGraphic
     */
    public void setDaySelected(DayGraphic day){
        daySelected = day;
        if(daySelected != null) {
            day.setSelected();
        }
        notifySubscribers();
    }


    /**
     * increments the month by 1
     */
    public void nextMonth(){
        if (month == 11) {
            if (year != 2) {
                year++;
                month = 0;
            }
        }
        else {
            if (!(year == 2 && month == 10)) {
                month++;
            }
        }

        notifyMonthChanged();
    }

    /**
     * decrements the month by 1
     */
    public void prevMonth(){
        if(month == 0) {
            if (year != 0) {
                year--;
                month = 11;
            }
        }
        else {
            month--;
        }


        notifyMonthChanged();
    }

    /**
     * @param day: new day
     * changes the current day to be the day parameter
     */
    public void setDay(int day) {
        this.day = day;
        notifySubscribers();
    }

    /**
     * @param filter: name of the filter clicked
     * updates which filters are selected in selected filters
     */
    public void addSelectedFilter(String filter) {
        selectedFilters.add(filter);
        notifyFiltersChanged();

    }

    /**
     * @param filter: name of the filter clicked
     * updates which filters are now unselected in selected filters
     */
    public void removeSelectedFilter(String filter) {
        selectedFilters.remove(filter);
        notifyFiltersChanged();
    }

    /**
     * @return the filters that are currently selected
     */
    public ArrayList<String> getSelectedFilters(){
        return selectedFilters;
    }


    /**
     * tells the views when a filter is selected of unselected
     */
    public void notifyFiltersChanged(){
        subscribers.forEach(iModelListener::filtersChanged);
    }

    /**
     * tells the view when the month has been changed
     */
    public void notifyMonthChanged(){
        subscribers.forEach(iModelListener::monthChanged);
    }

    public void notifySubscribers() {
        subscribers.forEach(s -> s.iModelChanged());
    }
    public void addSubscriber(iModelListener sub){subscribers.add(sub);}

}