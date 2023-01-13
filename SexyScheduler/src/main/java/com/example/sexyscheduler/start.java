package com.example.sexyscheduler;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;;
import java.io.IOException;


public class start extends Application {
    @Override
    public void start(Stage stage) throws IOException{
        Font.loadFont(getClass().getResourceAsStream("Teko/Teko-Bold.ttf"),16);
        Font.loadFont(getClass().getResourceAsStream("Teko/Teko-light.ttf"),16);
        MainUI view = new MainUI();
        Scene scene = new Scene(view,800,600);
        view.prefHeightProperty().bind(scene.heightProperty());
        view.prefWidthProperty().bind(scene.widthProperty());
        stage.setTitle("Sexy");
        stage.setScene(scene);
        stage.setMinHeight(400);
        stage.setMinWidth(650);
        stage.widthProperty().addListener((listener, oldVal, newVal)->view.changeWidth(newVal));
        stage.heightProperty().addListener((listener, oldVal, newVal)->view.changeHeight(newVal));
        stage.show();

        /*stage.setOnShowing(new EventHandler<WindowEvent>() {
                               @Override
                               public void handle(WindowEvent windowEvent) {
                                   if (stage.isFullScreen()) {
                                        view.changeHeight(stage.widthProperty().get());
                                        view.changeWidth(stage.heightProperty().get());
                                   }
                               }

                               ;
                           });*/

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent windowEvent) {
                        System.exit(0);
                    }
                });

    }

    public static void main(String[] args) {
        launch();
    }

}
