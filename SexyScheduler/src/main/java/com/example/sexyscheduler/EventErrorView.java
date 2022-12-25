package com.example.sexyscheduler;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Pop-up error screen displayed when an error is encountered while adding events
 */
public class EventErrorView extends StackPane implements ModelListener{

    private Stage errorPopUp;

    public EventErrorView() {
        this.errorPopUp = new Stage();
        this.errorPopUp.setTitle("Error");
        this.errorPopUp.setAlwaysOnTop(true);

        ErrorView errorView = new ErrorView();
        Scene errorScene = new Scene(errorView);
        errorScene.getStylesheets().add(this.getClass().getResource("styles.css").toExternalForm());
        this.errorPopUp.setScene(errorScene);
        this.errorPopUp.hide();

        errorView.okayButton.setOnAction(e -> hideError());
    }

    /**
     * Show error pop-up
     */
    public void showError() {
        this.errorPopUp.show();
    }

    /**
     * Hide error pop-up
     */
    public void hideError() {
        this.errorPopUp.hide();
    }
    public void modelChanged() {}


}
