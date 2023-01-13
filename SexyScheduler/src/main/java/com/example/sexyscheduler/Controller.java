package com.example.sexyscheduler;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventDispatchChain;
import javafx.stage.WindowEvent;

import java.util.ArrayList;

public class Controller {
    private CalendarModel model;
    private IModel imodel;
    public Controller() {

    }

    public void setModel(CalendarModel newModel) {
        this.model = newModel;
    }

    public void setIModel(IModel newIModel) {
        this.imodel = newIModel;
    }

    public ObservableList<String> getDays() {
        ArrayList<MyDay> list = model.getDaysInMonthOnly(imodel.getYearIdx(), imodel.getMonthIndx());
        ArrayList<String> listString = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            listString.add(list.get(i).toString());
        }
        return FXCollections.observableList(listString);
    }

    /**
     * When "Add Event" button is clicked on main screen display pop-up screen to add an event
     * @param actionEvent
     */
    public void handleAddEventButton(ActionEvent actionEvent) {
        if (model.addView) {
            model.notifySubscribers();
        }
        else {
            this.model.createEvent();

        }
    }

    public void closeEvents(WindowEvent e) {
        model.addView = false;
        model.notifySubscribers();
    }

    /**
     * Display error pop-up if an error is encountered during event scheduling
     */
    public void handleErrorFound() {
        EventErrorView error = new EventErrorView();
        model.createErrorEvent(error);
    }

    public void handleDeadlineEvent(int year, String month, int day, String title, String time, String tag){
        if (!model.scheduler.createDeadlineEventByDay(model.getDayByNames(year, month, day),title,time,tag)) {
            handleErrorFound();
        }
        else {
            this.model.createEvent();
        }

    }

    public void handleRepeatEvent(String title,String startTime, String endTime, String tag,int mode, int startYear, String startMonth, int startDay, int endYear, String endMonth, int endDay ){
        if (!model.scheduler.createRepeatableEvent(mode,startYear,startMonth,startDay,endYear,endMonth,endDay, title, tag, startTime, endTime)) {
            handleErrorFound();
        }
        else {
            this.model.createEvent();
        }
        model.notifySubscribers();
    }

    /**
     * When "Create Event" button is clicked on AddEvent view, schedule the event
     * @param title name of the event
     * @param year year
     * @param month month
     * @param day day
     * @param start starting time
     * @param end ending time
     * @param tag filter tag to attach to event (type of event)
     */
    public void handleOkayEventButton(String title, String year, String month, String day, String start, String end, String tag) {
        ModelTranslator mt = ModelTranslator.getInstance(false);

        //MyDay dayObject = model.getDayByNames(Integer.parseInt(year), mt.monthsNameByInt.get(Integer.parseInt(month) - 1), Integer.parseInt(day));
        if (!model.scheduler.createAppointmentEventByMyDay(model.getDayByNames(Integer.parseInt(year),mt.monthsNameByInt.get(Integer.parseInt(month)-1), Integer.parseInt(day)), title, start, end, 0, "blue", tag)) {
            handleErrorFound();
        }
        else{
            this.model.createEvent();
        }
        model.notifySubscribers();
    }
    public void handleSubmitEventButton(String title, String year, String month, String day, String start, String end, String tag) {
        ModelTranslator mt = ModelTranslator.getInstance(false);

        //MyDay dayObject = model.getDayByNames(Integer.parseInt(year), mt.monthsNameByInt.get(Integer.parseInt(month) - 1), Integer.parseInt(day));
        if (!model.scheduler.createAppointmentEventByMyDay(model.getDayByNames(Integer.parseInt(year),mt.monthsNameByInt.get(Integer.parseInt(month)-1), Integer.parseInt(day)), title, start, end, 0, "blue", tag)) {
            handleErrorFound();
            //CalendarModel.restore();
        }
        else{
            model.notifySubscribers();
        }
        model.notifySubscribers();
    }

    public void handleEventClicked(EventBase event, MyDay day) {
        model.showEventOverview(event, day);
    }

    public void handleAutoEvent(int yearStart, String monthStart, int dayStart, int yearEnd, String monthEnd, int dayEnd,
                                int days, String timeStart, String timeEnd, double coolDownHours, int frequency, String duration,
                                String title, String colour, String tag) {
        if (!model.scheduler.createAutomaticEventByDays(yearStart, monthStart, dayStart, yearEnd, monthEnd, dayEnd, days,
                timeStart, timeEnd, coolDownHours, frequency, duration, title, colour, tag)) {
            handleErrorFound();
        }
        else {
            this.model.createEvent();
        }
    }

    public void handleEditEventButtonClicked(ActionEvent event) {
        model.hideEditEventView();
    }

    public void handleDeleteEventButtonClicked(ActionEvent actionEvent) {
        model.showDeleteEventView();
        model.hideEventOverview();
        model.notifySubscribers();
    }

    public void handleDeleteCancelButtonClicked(ActionEvent actionEvent) {
        model.hideEventOverview();
        model.hideDeleteEventView();
        model.notifySubscribers();

    }

    public void handleConfirmCancelButtonClicked(ActionEvent actionEvent) {
        model.deleteEvent();
       // model.getDayByNames(imodel.getActualYear(),imodel.getMonthName(),Integer.parseInt(imodel.getDaySelected().day.getText())).deleteEvent(model.clickedEvent);
        model.hideDeleteEventView();
        model.hideEventOverview();


    }

    public void editEventButton(EventBase e, MyDay day, MyMonth month, MyYear year, EditEvent editView) {
        System.out.println(e);
        model.showEditEventView();
        model.hideEventOverview();
        editView.setFields(e, day, month, year);
        model.notifySubscribers();

    }

    /**
     * When "Create Event" button is clicked on AddEvent view, schedule the event
     * @param title name of the event
     * @param year year
     * @param month month
     * @param day day
     * @param start starting time
     * @param end ending time
     * @param tag filter tag to attach to event (type of event)
     */
    public void handleEditEventButton(String title, String year, String month, String day, String start, String end, String tag) {
        ModelTranslator mt = ModelTranslator.getInstance(false);
        MyDay dayObject = model.getDayByNames(Integer.parseInt(year), mt.monthsNameByInt.get(Integer.parseInt(month) - 1), Integer.parseInt(day));
        if (!model.scheduler.createAppointmentEventByMyDay(dayObject, title, start,end, 0, "blue", tag)) {
            handleErrorFound();
        }
        else{
            this.model.createEvent();
        }

    }


}
