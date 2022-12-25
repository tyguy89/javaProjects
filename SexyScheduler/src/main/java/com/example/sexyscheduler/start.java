package com.example.sexyscheduler;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;;
import java.io.IOException;


public class start extends Application {
    @Override
    public void start(Stage stage) throws IOException{
        Font.loadFont(getClass().getResourceAsStream("Teko/Teko-Bold.ttf"),16);
        Font.loadFont(getClass().getResourceAsStream("Teko/Teko-light.ttf"),16);
        MainUI view = new MainUI();
        Scene scene = new Scene(view,800,600);
        stage.setTitle("Sexy");
        stage.setScene(scene);
        stage.setMinHeight(300);
        stage.setMinWidth(350);
        stage.widthProperty().addListener((listener, oldVal, newVal)->view.changeWidth(newVal));
        stage.heightProperty().addListener((listener, oldVal, newVal)->view.changeHeight(newVal));
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }

}
