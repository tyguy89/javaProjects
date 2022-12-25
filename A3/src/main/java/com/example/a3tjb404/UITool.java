/*
tjb404
11294509
Tyler Boechler
CMPT 381
 */

package com.example.a3tjb404;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Tool stackpane object to represent main tools
 */
public class UITool extends StackPane {
    public boolean isBiggest = false;
    public int id;
    public Image image;
    public double x;
    public double y;
    public double height;
    public double width;

    /**
     * Creates new tool
     * @param id: 0-2
     * @param image: image for button
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public UITool(int id, Image image, double x, double y, double width, double height) {
        this.layoutXProperty().set(x);
        this.layoutYProperty().set(y);
        this.widthProperty().add(width);
        this.heightProperty().add(height);
        this.setMinSize(width, height);
        this.image = image;
        this.id = id;

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        if (id == 0) {
            this.isBiggest = true;  //starts on this tool
        }
        draw();
    }

    public void setBiggest() {
        if (!this.isBiggest) {
            this.isBiggest = true;
        }
        draw();
    }

    public void draw() {
        this.getChildren().clear();
        Rectangle r = new Rectangle(x, y, width/1.4, height/1.4);
        ImageView iv = new ImageView();
        iv.imageProperty().set(image);
        iv.setFitHeight(height/4);
        iv.setFitWidth(width/4);

        r.setFill(Color.LAWNGREEN);
        r.setOpacity(0.7);

        if(this.isBiggest) {
            r.setHeight(height);
            r.setWidth(width);
        }
        else {
            r.setHeight(height/1.4);
            r.setWidth(width/1.4);
        }

        this.getChildren().addAll(iv, r);
        this.setVisible(true);
    }
}
