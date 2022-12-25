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

    public EventView(CalendarModel model, IModel iModel) {
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

        welcomeText = new Label("Welcome to SexyScheduler (tm)!");
        welcomeText.setId("welcomeText");
        welcomeText.setAlignment(Pos.CENTER);

        weekTitle = new Label();
        weekTitle.setPrefWidth(450);
        weekTitle.setAlignment(Pos.CENTER);
        weekTitle.setId("cal-bot");
        weekTitle.setMinHeight(46.5);
        HBox.setHgrow(weekTitle,Priority.ALWAYS);

        root.getChildren().addAll(welcomeText, weekTitle);
        this.setMinSize(Double.MIN_VALUE, Double.MIN_VALUE);
        this.setPrefSize(200, 540);
        this.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        this.getChildren().add(root);
    }

    public void setModel(CalendarModel model) {
        this.model = model;
    }

    public void setIModel(IModel iModel) {
        this.iModel = iModel;
    }

    /**
     * Switches between weekEventView and dayEventView depending on which part of CalendarView is selected.
     * weekSelected is true: weekEventView shows up, else, dayEventView shows up
     */
    @Override
    public void iModelChanged() {
        if (iModel.weekSelected) {
            root.getChildren().clear();
            if (iModel.week != null) {
                if (iModel.week.days[0].value > iModel.week.days[6].value) {
                    weekTitle.setText(model.getMonthByIndices(iModel.getYearIdx(), iModel.getMonthIndx() - 1).getValue()
                            + " " + iModel.week.days[0].value + " - " + iModel.getMonthName() + " " +
                            iModel.week.days[6].value);
                }
                else if (Math.abs(iModel.week.days[0].value - iModel.week.days[6].value) > 25) {
                    weekTitle.setText(iModel.getMonthName() + " " + iModel.week.days[0].value + " - " +
                            model.getMonthByIndices(iModel.getYearIdx(), iModel.getMonthIndx() + 1).getValue() + " " +
                            iModel.week.days[6].value);
                }
                root.getChildren().addAll(weekTitle, weekEventView);
            }
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

    @Override
    public void colorsChanged() {

    }

}
