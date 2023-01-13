package com.example.sexyscheduler;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class EventView extends StackPane implements iModelListener {

    WeekEventView weekEventView;
    DayEventView dayEventView;
    Label welcomeText;
    Label weekTitle;
    VBox root;

    CalendarModel model;
    IModel iModel;

    public EventView(CalendarModel model, IModel iModel,Controller controller) {
        this.iModel = iModel;
        this.model = model;
        root = new VBox(5);
        root.setAlignment(Pos.TOP_CENTER);

        weekEventView = new WeekEventView();
        dayEventView = new DayEventView();

        dayEventView.setIModel(iModel);
        dayEventView.setModel(model);
        weekEventView.setModel(model);
        weekEventView.setiModel(iModel);

        iModel.addSubscriber(dayEventView);
        iModel.addSubscriber(weekEventView);
        iModel.addSubscriber(this);

        weekEventView.setController(controller);
        dayEventView.setController(controller);

        model.addSubscribers(dayEventView);
        model.addSubscribers(weekEventView);

        welcomeText = new Label("Welcome to SexyScheduler (tm)!");
        welcomeText.setId("welcomeText");
        welcomeText.setAlignment(Pos.CENTER);

        weekTitle = new Label();
        weekTitle.setPrefWidth(450);
        weekTitle.setAlignment(Pos.CENTER);
        weekTitle.setId("week-day");
        weekTitle.setMinHeight(46.5);
        HBox.setHgrow(weekTitle,Priority.ALWAYS);

        root.getChildren().addAll(welcomeText, weekTitle);
 /*       this.setMinSize(Double.MIN_VALUE, Double.MIN_VALUE);
        this.setPrefSize(200, 540);
        this.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);*/

        this.getChildren().add(root);
        iModelChanged();
        this.getStylesheets().add(this.getClass().getResource("styles.css").toExternalForm());


    }

    /**
     * Switches between weekEventView and dayEventView depending on which part of CalendarView is selected.
     * weekSelected is true: weekEventView shows up, else, dayEventView shows up
     */
    @Override
    public void iModelChanged() {
        if (iModel.weekSelected) {
            root.getChildren().clear();
            if(iModel.week.value == 0){
                weekTitle.setText(model.getMonthByIndices(iModel.getYearIdx(), iModel.getMonthIndx()-1).getValue()
                        + " " + iModel.week.days[0].value + " - " + iModel.getMonthName() + " " +
                        iModel.week.days[6].value);
            }else {
                //if (iModel.week != null) {
                weekTitle.setText(model.getMonthByIndices(iModel.getYearIdx(), iModel.getMonthIndx()).getValue()
                        + " " + iModel.week.days[0].value + " - " + iModel.getMonthName() + " " +
                        iModel.week.days[6].value);
            }
                root.getChildren().addAll(weekTitle, weekEventView);
            //}
        }
        else {
            root.getChildren().clear();
            if (iModel.getDaySelected() != null)
                weekTitle.setText(iModel.getMonthName() + " " + iModel.getDaySelected().day.getText());
            root.getChildren().addAll(weekTitle, dayEventView);
        }
    }

    @Override
    public void monthChanged() {

    }

    @Override
    public void filtersChanged() {

    }

    public void changeHeight(Number newVal){
        this.setMaxHeight(newVal.doubleValue());
        dayEventView.changeHeight(newVal.doubleValue()-weekTitle.getHeight()-60);
        weekEventView.changeHeight(newVal.doubleValue()-weekTitle.getHeight()-60);

    }

}
