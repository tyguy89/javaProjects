/*
tjb404
11294509
Tyler Boechler
CMPT 381
 */

package com.example.a3tjb404;

/**
 * Object that can be selected (My own implementation of SMItem)
 */
public abstract class SelectableObject {

    public double x;
    public double y;

    public double width;
    public double height;


    public SelectableObject(double a, double b) {
        x = a;
        y = b;

    }

    public void set(double width, double height) {
        this.width = width;
        this.height = height;
    }


}
