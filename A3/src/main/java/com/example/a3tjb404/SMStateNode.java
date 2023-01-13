/*
Tyler Boechler
 */

package com.example.a3tjb404;

/**
 * State node object
 */
public class SMStateNode extends SelectableObject {

    public String title = "Default";
    public double width;
    public double height;

    /**
     * Creates new state node
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public SMStateNode(double x, double y, double width, double height) {
        super(x, y);
        this.width = width;
        this.height = height;
    }

    /**
     * Move node
     * @param dx
     * @param dy
     */
    public void clickedAndDragged(Double dx, Double dy) {
        this.x += dx;
        this.y += dy;
    };

}
