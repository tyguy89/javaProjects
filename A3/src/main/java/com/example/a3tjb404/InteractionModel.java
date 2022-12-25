/*
tjb404
11294509
Tyler Boechler
CMPT 381
 */

package com.example.a3tjb404;

import javafx.scene.Cursor;
import javafx.scene.Scene;

import java.util.ArrayList;

/**
 * Interaction model object
 */
public class InteractionModel {

    private ArrayList<iModelListener> subscribers;

    public double worldWidth;
    public double displayWidth;
    public double worldHeight;
    public double displayHeight;

    public double viewX = 0;
    public double viewY = 0;

    private Scene scene;

    public UITool selectedTool;
    public UITool lastSelected;
    private DiagramView dv;

    private SelectableObject selectedObject;

    public InteractionModel(DiagramView dv) {
        this.dv = dv;
        subscribers = new ArrayList<iModelListener>();
    }

    public void addSubscriber(iModelListener m) {
        subscribers.add(m);
    }

    public void setWorldSize(double world) {
        this.worldWidth = world;
        this.worldHeight = world;
    }

    public void setViewSize(double x, double y) {
        this.displayWidth = x;
        this.displayHeight = y;
    }

    /**
     * Move view in world
     * @param dX
     * @param dY
     */
    public void changeLocation(double dX, double dY) {
        if(this.viewX >= 0 && this.viewY >= 0) {
            if (dX > 0) {
                if (!(this.viewX + dX + dv.myCanvas.getWidth()/2 > this.worldWidth)) {
                    this.viewX += dX;
                }
            } else {
                if ((this.viewX + dX > 0)) {
                    this.viewX += dX;
                }
            }
            if (dY > 0) {
                if (!(this.viewY + dY + scene.getHeight()/1.9 > this.worldHeight)) {
                    this.viewY += dY;
                }
            } else {
                if ((this.viewY + dY > 0)) {
                    this.viewY += dY;
                }
            }
        }
        else {
            this.viewX = 0;
            this.viewY = 0;
        }
        notifySubscribers();
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void setTool(UITool tool) {
        lastSelected = selectedTool;
        selectedTool = tool;
        if (!tool.isBiggest) {
            if (tool.id == 0) {
                scene.setCursor(Cursor.DEFAULT);
                subscribers.forEach(sub -> sub.editControllerState(AppController.State.READY));
            }
            else if (tool.id == 1) {
                scene.setCursor(Cursor.MOVE);
                subscribers.forEach(sub -> sub.editControllerState(AppController.State.READY));
            }
            else {
                scene.setCursor(Cursor.CROSSHAIR);
                subscribers.forEach(sub -> sub.editControllerState(AppController.State.READY));
            }
            selectedTool.setBiggest();
            lastSelected.isBiggest = false;
        }
        notifySubscribers();

    }

    public SelectableObject getSelectedObject() {
        return selectedObject;
    }

    public void setSelected(SelectableObject o) {
        selectedObject = o;
        notifySubscribers();
    }

    public void unselect() {
        selectedObject = null;
        notifySubscribers();
    }

    /**
     * Translate world x coordinate to view
     * @param x
     * @return
     */
    public double worldToViewX(double x) {
        double t = x / worldWidth;
        return t * worldWidth - viewX;
    }

    /**
     * Translate world y coordinate to view
     * @param y
     * @return
     */
    public double worldToViewY(double y) {
        double t = y / worldHeight;
        return t * worldHeight - viewY;
    }

    /**
     * Translate view x coordinate to world
     * @param x
     * @return
     */
    public double viewToWorldX(double x) {
        return x + viewX;
    }

    /**
     * Translate view y coordinate to world
     * @param y
     * @return
     */
    public double viewToWorldY(double y) {
        return y + viewY;
    }


    private void notifySubscribers() {subscribers.forEach(sub -> sub.iModelChanged());}
}
