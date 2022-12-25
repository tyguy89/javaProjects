/*
tjb404
11294509
Tyler Boechler
CMPT 381
 */

package com.example.a3tjb404;

/**
 * Transition link object
 */
public class SMTransitionLink extends SelectableObject {

    public double x2, y2;

    public double boxX, boxY;

    public String title = "Transition";
    public String boxEvent = "No Events";
    public String boxEffects = "No Effects";
    public String boxContext = "No Context";

    SMStateNode startNode;
    SMStateNode endNode;

    public SMTransitionLink(double x, double y, SMStateNode start) {
        super(x, y);
        startNode = start;
    }

    /**
     * Add line connection
     * @param x
     * @param y
     */
    public void addLine(double x, double y) {
        x2 = x;
        y2 = y;
    }

    /**
     * Add connecting node
     * @param end
     */
    public void connection(SMStateNode end) {
        endNode = end;
    }

    /**
     * Update line start location
     * @param dx
     * @param dy
     */
    public void updateStart(double dx, double dy) {
        this.x += dx;
        this.y += dy;

        //boxX = x+(x2-x)/2;
        //boxY = y+(y2-y)/2;
    }

    /**
     * Update end of line location
     * @param dX
     * @param dY
     */
    public void updateEnd(double dX, double dY){
        x2 += dX;
        y2 += dY;

        //boxX = x+(x2-x)/2;
        //boxY = y+(y2-y)/2;
    }

    /**
     * Move link box
     * @param dx
     * @param dy
     */
    public void moveBox(double dx, double dy) {
        boxX += dx;
        boxY += dy;
    }

}
