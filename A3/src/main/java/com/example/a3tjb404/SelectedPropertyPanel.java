/*
tjb404
11294509
Tyler Boechler
CMPT 381
 */

package com.example.a3tjb404;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

/**
 * Selected Property Panel visual object
 */
public class SelectedPropertyPanel extends VBox implements iModelListener {

    public double width, height;

    private InteractionModel iModel;
    private AppController controller;
    public TextField tf = null;

    public SelectedPropertyPanel(double width, double height) {
        this.setSpacing(10);
        this.setPadding(new Insets(10));
        this.setMinSize(width, height);

        this.width = width;
        this.height = height;

        this.setPrefSize(width, height);
    }

    /**
     * Draws panel
     */
    private void draw() {
        if (iModel.getSelectedObject() != null) {
            this.getChildren().clear();
            this.setStyle("-fx-background-color: white");
            Class<? extends SelectableObject> a = iModel.getSelectedObject().getClass();

            if (a.getName().equals("com.example.a3tjb404.SMStateNode")) { //Check which kind of object
                SMStateNode node = (SMStateNode) iModel.getSelectedObject();
                StackPane baseRoot = new StackPane();
                baseRoot.setAlignment(Pos.TOP_CENTER);

                Rectangle r = new Rectangle(0, 0, width-20, 40);
                r.setFill(Color.CORAL);

                Label l = new Label("State");
                l.setFont(new Font("Arial", 25));
                l.setLayoutX(width / 3);
                l.setLayoutY(width / 2);

                baseRoot.getChildren().addAll(r, l);


                Label l2 = new Label("State Name:");
                l2.setFont(new Font("Arial", 20));

                tf = new TextField(node.title);
                tf.setPromptText("Enter Name");
                tf.setOnKeyPressed(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        controller.renameNodeText(event, tf.getText(), node);
                    }
                });

                this.getChildren().addAll(baseRoot, l2, tf);
            }
            else {

                StackPane baseRoot = new StackPane();
                baseRoot.setAlignment(Pos.TOP_CENTER);

                SMTransitionLink link = (SMTransitionLink) iModel.getSelectedObject();
                Rectangle r = new Rectangle(0, 0, width - 6, 40);
                r.setFill(Color.CORAL);

                Label l = new Label("Transition");
                l.setFont(new Font("Arial", 25));

                l.setLayoutX(width / 3);
                l.setLayoutY(width / 3);

                baseRoot.getChildren().addAll(r, l);

                Label l2 = new Label("Event:");
                l2.setFont(new Font("Arial", 20));

                tf.setPromptText("Enter Name");
                tf.setOnKeyPressed(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        controller.renameTransitionText(event, tf.getText(), link);
                    }
                });

                Label l3 = new Label("Context: ");
                TextArea ta1 = new TextArea(link.boxContext);
                ta1.setOnKeyPressed(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        controller.renameContext(event, ta1.getText(), link);
                    }
                });
                Label l4 = new Label("Side Effects:");
                TextArea ta2 = new TextArea(link.boxEffects);

                ta2.setOnKeyPressed(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        controller.renameEffects(event, ta2.getText(), link);
                    }
                });

                Button button = new Button("Submit");
                button.setPrefSize(20, 20);
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        controller.renameTransitionText(actionEvent, tf.getText(), link);
                        controller.renameContext(actionEvent, ta1.getText(), link);
                        controller.renameEffects(actionEvent, ta2.getText(), link);
                    }
                });


                this.getChildren().addAll(baseRoot, l2, tf, l3, ta1, l4, ta2, button);
            }
        }
    }

    public void setiModel(InteractionModel iModel) {
        this.iModel = iModel;
    }

    @Override
    public void iModelChanged() {
        if (iModel.getSelectedObject() == null) {
            this.setVisible(false);
        }
        else {
            this.setVisible(true);
            draw();
        }

    }

    @Override
    public void editControllerState(AppController.State state) {

    }

    public void setController(AppController controller) {
        this.controller = controller;
    }


}
