/*
tjb404
11294509
Tyler Boechler
CMPT 381
 */

package com.example.a3tjb404;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeType;

import java.io.IOException;

/**
 * Main UI visual object
 */
public class MainUI extends BorderPane {

    public double applicationWidth = 1200.0;
    public double applicationHeight = 800;

    public Scene scene;
    public InteractionModel iModel;
    public SMModel model;
    public AppController controller;
    public ToolPalette tp;
    public DiagramView dv;
    public SelectedPropertyPanel spp;
    public Line line;


    public MainUI() throws IOException {
        //Set up main MVC
        this.setPrefSize(applicationWidth, applicationHeight);
        dv = new DiagramView(applicationWidth-200, applicationHeight);

        controller = new AppController();
        iModel = new InteractionModel(dv);
        model = new SMModel();

        iModel.setWorldSize(1600.0);
        controller.setIModel(iModel);
        controller.setModel(model);

        tp = new ToolPalette(70.0, applicationHeight);

        spp = new SelectedPropertyPanel(200.0, applicationHeight);

        spp.setiModel(iModel);
        tp.setIModel(iModel);
        dv.setIModel(iModel);

        dv.setModel(model);
        tp.setModel(model);

        dv.setController(controller);
        spp.setController(controller);

        iModel.addSubscriber(tp);
        iModel.addSubscriber(dv);
        iModel.addSubscriber(spp);

        model.addSubscriber(dv);
        model.addSubscriber(tp);

        line = new Line(tp.widthProperty().doubleValue()+5, 0, tp.widthProperty().doubleValue()+5, applicationHeight);
        line.setFill(new Color(0,0,0,0));

        HBox leftRoot = new HBox(tp, line);
        HBox rightRoot = new HBox(spp);

        this.rightProperty().set(rightRoot);

        this.leftProperty().set(leftRoot);

        this.centerProperty().set(dv);

        this.setStyle("-fx-border-color: red");
    }


    /**
     * Sets scene for mainUI object when scene is not null object, Also adds listeners for resizing
     * @param scene: scene from application
     */
    public void setScene(Scene scene) {
        this.scene = scene;
        iModel.setScene(scene);

        //Change y
        this.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                applicationHeight = number.doubleValue();
                setPrefSize(applicationWidth, applicationHeight);
                //Everything to new size
                tp.snapSizeY(number.doubleValue());
                dv.snapSizeY(number.doubleValue());
                spp.snapSizeY(number.doubleValue());
                dv.myCanvas.setHeight(number.doubleValue());
                line.setEndY(number.doubleValue());
                model.resize();//notify subscribers
            }
        });

        //change in x
        this.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                applicationWidth = number.doubleValue();

                setPrefSize(applicationWidth, applicationHeight);
                //Adjust diagram view size
                dv.snapSizeX(applicationWidth-250);
                dv.myCanvas.setWidth(applicationWidth-250);
                iModel.setViewSize(applicationWidth-250, applicationHeight);

                model.resize();//notify subscribers
            }
        });
    }
}
