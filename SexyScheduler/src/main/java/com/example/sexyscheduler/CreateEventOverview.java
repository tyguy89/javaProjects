package com.example.sexyscheduler;

import javafx.event.*;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

public class CreateEventOverview extends StackPane implements ModelListener {
    private Stage ceoStage;
    private EventOverview eventOverview;
    private CalendarModel model;
    private IModel iModel;
    private Controller controller;
    private EditEvent editEventView;
    public CreateEventOverview(EditEvent editEventView) {
        this.ceoStage = new Stage();
        this.ceoStage.setTitle("Edit Event");
        this.ceoStage.hide();

        this.editEventView = editEventView;
        EventHandler<WindowEvent> closeRequest = new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                controller.closeEvents(windowEvent);
            }
        } ;

    }

    public void setModel(CalendarModel model) {
        this.model = model;
    }

    public void setiModel(IModel iModel) {
        this.iModel = iModel;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void modelChanged() {
        if (model.showEventOverview) {
            this.eventOverview = new EventOverview(model.clickedEvent, editEventView);
            this.eventOverview.setModel(model);
            this.eventOverview.setiModel(iModel);
            this.eventOverview.setController(controller);
            Scene createEventOverview = new Scene(this.eventOverview);
            createEventOverview.getStylesheets().add(this.getClass().getResource("styles.css").toExternalForm());
            this.ceoStage.setScene(createEventOverview);
            this.ceoStage.show();
        }
        else {
            this.ceoStage.hide();
        }
    }
}
