/*
Tyler Boechler
 */

package com.example.a3tjb404;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

/**
 * Diagram view visual object
 */
public class DiagramView extends StackPane implements iModelListener, ModelListener {
    GraphicsContext gc;
    Canvas myCanvas;

    private InteractionModel iModel;
    private SMModel model;
    int mode = 0;
    private AppController controller;

    public Canvas myWorld;
    public GraphicsContext worldGC;

    private double x = 0;
    private double y = 0;

    /**
     * Creates new view object
     * @param width
     * @param height
     */
    public DiagramView(Double width, Double height) {
        myCanvas = new Canvas(1600.0, 1600.0);
        gc = myCanvas.getGraphicsContext2D();

        //Main canvas
        this.setPrefSize(width, height);
        myCanvas.setWidth(width);
        myCanvas.setHeight(height);
        gc.setGlobalAlpha(0.8);


        //Mini map stuff
        myWorld = new Canvas(1600.0, 1600.0);
        worldGC = myWorld.getGraphicsContext2D();

        worldGC.setGlobalAlpha(0.5);
        drawWorld();
        myWorld.setWidth(width);
        myWorld.setHeight(height);
        worldGC.setFill(Color.DARKSLATEGREY);

        myWorld.setScaleX(0.33);
        myWorld.setScaleY(0.33);

        drawWorld();

        this.getChildren().addAll(myWorld, myCanvas);

    }

    private void drawWorld() {  //Draw everthing with scale factor for minimap
        worldGC.setLineWidth(3);
        worldGC.clearRect(0,0, myWorld.getWidth(), myWorld.getHeight());
        if (model != null) {
            for (SMStateNode node : model.nodes) {
                double x = node.x/3;
                double y = node.y/3;

                if (node.equals(iModel.getSelectedObject())) {
                    worldGC.setStroke(Color.RED);
                }

                worldGC.strokeRect(x, y, node.width/3, node.height/3);
                worldGC.fillText(node.title, x + node.width/3 / 4, y + node.height/3 / 2);
                worldGC.setStroke(Color.BLACK);

            }
        }
        if (model != null && model.links != null) {
            for (SMTransitionLink link: model.links) {
                if (link.endNode != null) {

                    if (link.equals(iModel.getSelectedObject())) {
                        worldGC.setStroke(Color.RED);
                    }

                    worldGC.strokeLine(link.x/3, link.y/3, link.boxX/3, link.boxY/3);
                    worldGC.strokeLine(link.boxX/3, link.boxY/3, link.x2/3, link.y2/3);
                    worldGC.strokeOval(link.boxX/3-5, link.boxY/3-5, 10, 10);
                    worldGC.strokeOval(link.x2/3-5, link.y2/3-5, 10, 10);
                    worldGC.setStroke(Color.DARKCYAN);
                    worldGC.strokeRect(link.boxX/3, link.boxY/3, link.width/3, link.height/3);

                    worldGC.fillText(link.title+ "\nEvent: " + link.boxEvent + "\nContext: " + link.boxContext + "\nSide Effects: " + link.boxEffects, link.boxX/3, link.boxY/3+8);
                    worldGC.setStroke(Color.BLACK);
                }
                else {
                    worldGC.strokeLine(link.x/3, link.y/3, link.x2/3, link.y2/3);
                }
            }
        }

        worldGC.strokeRect(2, 2, myWorld.getWidth()-300, myWorld.getHeight()-150);
        if(iModel != null) {
            worldGC.strokeRect(iModel.viewX/3, iModel.viewY/3, this.getWidth()/3-5, this.getHeight()/3-5);
        }
        else {
            worldGC.strokeRect(4, 4, this.getWidth()/3-5, this.getHeight()/3-5);
        }
        worldGC.setLineWidth(1);

        myWorld.setLayoutX(0);
        myWorld.setLayoutY(0);
    }



    private void draw() { //Draw normal canvas
        gc.clearRect(0,0, myCanvas.getWidth(), myCanvas.getHeight());
        if (model != null) {
            for (SMStateNode node : model.nodes) { //Draw nodes
                double x = iModel.worldToViewX(node.x);
                double y = iModel.worldToViewY(node.y);

                if (node.equals(iModel.getSelectedObject())) {
                    gc.setStroke(Color.RED);
                }

                gc.strokeRect(x, y, node.width, node.height);
                gc.fillText(node.title, x + node.width / 4, y + node.height / 2);
                gc.setStroke(Color.BLACK);

            }
        }

        if (model != null && model.links != null) {
            for (SMTransitionLink link: model.links) {
                if (link.endNode != null) {

                    if (link.equals(iModel.getSelectedObject())) {
                        gc.setStroke(Color.RED);
                    }
                    //Draw link
                    gc.strokeLine(iModel.worldToViewX(link.x), iModel.worldToViewY(link.y), iModel.worldToViewX(link.boxX), iModel.worldToViewY(link.boxY));
                    gc.strokeLine(iModel.worldToViewX(link.boxX), iModel.worldToViewY(link.boxY), iModel.worldToViewX(link.x2), iModel.worldToViewY(link.y2));
                    gc.strokeOval(iModel.worldToViewX(link.boxX)-5, iModel.worldToViewY(link.boxY)-5, 10, 10);
                    gc.strokeOval(iModel.worldToViewX(link.x2)-5, iModel.worldToViewY(link.y2)-5, 10, 10);
                    gc.setStroke(Color.DARKCYAN);
                    gc.strokeRect(iModel.worldToViewX(link.boxX), iModel.worldToViewY(link.boxY), link.width, link.height);


                    gc.fillText(link.title+ "\nEvent: " + link.boxEvent + "\nContext: " + link.boxContext + "\nSide Effects: " + link.boxEffects, iModel.worldToViewX(link.boxX), iModel.worldToViewY(link.boxY)+8);


                    gc.setStroke(Color.BLACK);
                }

                else {
                    gc.strokeLine(iModel.worldToViewX(link.x), iModel.worldToViewY(link.y), iModel.worldToViewX(link.x2), iModel.worldToViewY(link.y2));
                }
            }
        }
    }



    public void setIModel(InteractionModel iModel) {
        this.iModel = iModel;
    }
    public void setModel(SMModel model) {
        this.model = model;
    }

    @Override
    public void modelChanged() {
        draw();
        drawWorld();
    }

    @Override
    public void iModelChanged() {
        mode = iModel.selectedTool.id;

        this.requestFocus();

        draw();
        drawWorld();
    }


    @Override
    public void editControllerState(AppController.State state) {
        controller.currentState = state;
    }

    public void setController(AppController controller) {
        this.controller = controller;
        myCanvas.setOnMousePressed(controller::handlePressed);
        myCanvas.setOnMouseDragged(controller::handleDragged);
        myCanvas.setOnMouseReleased(controller::handleReleased);

    }

    public void modelNotNull() {
        sceneProperty().get().setOnKeyPressed(controller::deleteItem);
    }

}
