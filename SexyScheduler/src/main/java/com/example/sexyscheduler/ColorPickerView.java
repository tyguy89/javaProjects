package com.example.sexyscheduler;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ColorPickerView extends StackPane {
    private Stage popUp;

    public ColorPickerView(BorderPane select){
        this.getChildren().add(select);
        this.popUp = new Stage();
        this.popUp.setTitle("Color Picker");
        Scene colorPicker = new Scene(this);
        this.popUp.setScene(colorPicker);
    }

    public void show(){
        if(!this.popUp.isShowing()){
            this.popUp.show();
        }else{
            this.popUp.hide();
        }
    }

}
