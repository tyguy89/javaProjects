package com.example.sexyscheduler;

import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class MainUI extends HBox {
    CalendarView calendarView;
    DayEventView dayEventView;
    EventView eventView;
    Filter_View filterView;
    VBox eventContainer;
    CalendarModel model;


    /**
     * Constructor for main class
     */
    public MainUI() {
        HBox root = new HBox(10);

        // instantiate models
        IModel imodel = new IModel();
        CalendarModel save = CalendarModel.restore();
        model = Objects.requireNonNullElseGet(save, () -> new CalendarModel(3));
        //System.out.println(model);
        filterView = new Filter_View(imodel,model);

        // instantiate main views

        // instantiate additional views
        // instantiate additional views
        Notif notify = new Notif(model);
        AddEvent addView = new AddEvent(filterView);
        AddAutoEvent addAutoView = new AddAutoEvent(filterView);
        //RepeatEvent repeatEventView = new RepeatEvent(filterView);
        DeadlineEventView deadlineEventView = new DeadlineEventView(filterView);
        CreateEventViewItems createView = new CreateEventViewItems(filterView);
        //RepeatEventTabs repeatTabs = new RepeatEventTabs(filterView);
        RepeatEvent repeatEventView = new RepeatEvent(filterView);
        EventTabsView addEventTabs = new EventTabsView(createView, addView, addAutoView, repeatEventView, deadlineEventView);
        EditEventView editView = new EditEventView(filterView);
        EditEvent editWindow = new EditEvent(editView);
        CreateEventView ceView = new CreateEventView(addEventTabs,model);
        CreateEventButtonView bView = new CreateEventButtonView();
        CreateEventOverview createEventOverview = new CreateEventOverview(editWindow);
        DeleteEventView deleteEventView = new DeleteEventView();



        // set models and controller
        calendarView = new CalendarView(model,imodel,filterView);
        Controller controller = new Controller();
        controller.setModel(model);
        controller.setIModel(imodel);
        bView.setController(controller);
        eventView = new EventView(model, imodel,controller);
        editView.setController(controller);

        //ceView.setModel(model);

        createEventOverview.setModel(model);
        createEventOverview.setiModel(imodel);
        createEventOverview.setController(controller);
        deleteEventView.setModel(model);
        deleteEventView.setController(controller);
        //editWindow.setModel(model);


        addView.setController(controller);
        addAutoView.setController(controller);
        editView.setController(controller);
        deadlineEventView.setController(controller);
        //repeatT.setController(controller);
        repeatEventView.setController(controller);
        //addAutoView.setController(controller);
        //addAutoView.setModel(model);
        editWindow.setModel(model);
        editView.setModel(model);



        // add subscribers
        model.addSubscribers(calendarView);
        model.addSubscribers(ceView);
        imodel.addSubscriber(createView);
        //model.addSubscribers(deleteEventView);
        model.addSubscribers(createEventOverview);
        model.addSubscribers(editWindow);
        //model.addSubscribers(eventView);

        // align views on main screen
        eventContainer = new VBox(eventView, bView);
        eventContainer.setSpacing(5);


        root.getChildren().addAll(calendarView, eventContainer);
        this.getChildren().add(root);
        this.getStylesheets().add(this.getClass().getResource("styles.css").toExternalForm());
        this.setId("cust-background");
        this.setPadding(new Insets(10,10,10,0));
        changeHeight(this.getHeight());
        changeWidth(this.getWidth());


    }

    public void changeHeight(Number newVal){
        calendarView.changeHeight(newVal);
        eventView.changeHeight(newVal);
        eventContainer.setPrefHeight(newVal.doubleValue());

    }

    public void changeWidth(Number newVal){
        calendarView.changeWidth(calendarView.getWidth());
        eventContainer.setMinWidth(newVal.doubleValue() * (1.0 / 3.0));
    }

}
