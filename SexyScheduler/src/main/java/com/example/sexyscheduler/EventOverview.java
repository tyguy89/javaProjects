package com.example.sexyscheduler;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class EventOverview extends StackPane {
    private Label eventName;
    private Label eventDate;
    private Label eventTime;
    private Button deleteEvent;
    private EventBase event;
    private EditEvent editEventView;

    private CalendarModel model;
    private IModel iModel;

    public EventOverview(EventBase event, EditEvent editEvent) {
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);

        eventName = new Label(event.title);
        //eventDate = new Label(day.dayofWeek);
        if(event instanceof AppointmentEvent) {
            eventTime = new Label(((AppointmentEvent) event).start + " - " + ((AppointmentEvent) event).end);
        }

        if(event instanceof DeadlineEvent){
            eventTime = new Label(((DeadlineEvent) event).time);
        }
        this.event = event;
        this.editEventView = editEvent;

        HBox buttonContainer = new HBox(5);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setMinSize(300,100);
        buttonContainer.setPrefSize(300,100);
        buttonContainer.setMaxSize(300,100);

        deleteEvent = new Button("Delete Event");

        buttonContainer.getChildren().addAll(deleteEvent);

        root.getChildren().addAll(eventName, eventTime, buttonContainer);
        this.getStylesheets().add(this.getClass().getResource("styles.css").toExternalForm());
        root.setId("cust-background");
        deleteEvent.setId("cust-button");

        this.getChildren().add(root);
    }

    public void setModel(CalendarModel model) {
        this.model = model;
    }

    public void setiModel(IModel iModel) {
        this.iModel = iModel;
    }

    public void setController(Controller controller) {
        deleteEvent.setOnAction(controller::handleConfirmCancelButtonClicked);
    }

}
