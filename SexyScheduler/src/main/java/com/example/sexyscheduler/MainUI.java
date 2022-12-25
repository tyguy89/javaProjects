package com.example.sexyscheduler;

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

        // instantiate main views
        filterView = new Filter_View();


        // instantiate additional views
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
        CreateEventView ceView = new CreateEventView(addEventTabs);
        CreateEventButtonView bView = new CreateEventButtonView();

        // instantiate models
        IModel imodel = new IModel();
        CalendarModel save = CalendarModel.restore();
        model = Objects.requireNonNullElseGet(save, () -> new CalendarModel(3, filterView.filterList));

        System.out.println(model.toString());

        // set models and controller
        calendarView = new CalendarView(model,imodel);
        Controller controller = new Controller();
        controller.setModel(model);
        controller.setIModel(imodel);
        addView.setController(controller);
        addAutoView.setController(controller);
        editView.setController(controller);
        deadlineEventView.setController(controller);
        //repeatT.setController(controller);
        repeatEventView.setController(controller);
        //addAutoView.setController(controller);
        //addAutoView.setModel(model);
        bView.setController(controller);
        eventView = new EventView(model, imodel);
        eventView.setIModel(imodel);
        eventView.setModel(model);



        // add subscribers
        model.addSubscribers(calendarView);
        model.addSubscribers(ceView);
        //model.addSubscribers(eventView);

        // add filters
        filterView.add_filter("School");
        filterView.add_filter("Work");
        filterView.add_filter("Other");

        // align views on main screen
        eventContainer = new VBox(eventView, bView);
        eventContainer.setSpacing(5);


        root.getChildren().addAll(calendarView, eventContainer);
        this.getChildren().add(root);

    }

    public void changeHeight(Number newVal){
        calendarView.changeHeight(newVal);
        eventContainer.setMinHeight(newVal.doubleValue());
    }

    public void changeWidth(Number newVal){
        calendarView.changeWidth(calendarView.getWidth());
        eventContainer.setMinWidth(newVal.doubleValue() * (1.0 / 3.0));
    }

}
