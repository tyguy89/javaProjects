/*
Tyler Boechler
tjb404, 11294509
CMPT 370 - Team Aviato
 */

package com.example.sexyscheduler;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Instance of a year in the calendar model
 */
public class MyYear implements Serializable {

    public int value;
    public boolean isLeap;
    public MyMonth[] months;

    private ModelTranslator mt;

    /**
     * Creates new instance of year object
     * @param value: int value for year
     * @param startDay: start dow for year
     * @param leapYear: is leap year?
     */
    public MyYear(int value, String startDay, boolean leapYear) {

        isLeap = leapYear;
        months = new MyMonth[12];

        mt = ModelTranslator.getInstance(isLeap);

        this.value = value;


        for (int i = 0; i < 12; i++) {
            months[i] = new MyMonth(mt.monthNames[i], i, startDay, leapYear, this);
        }
    }

    /**
     * Gets year value for object
     * @return int year
     */
    public int getValue() {
        return value;
    }

    /**
     * Gets a month by int 0-11
     * @param i: int index
     * @return month object
     * @throws IndexOutOfBoundsException on illegal index
     */
    public MyMonth getMonthByInt(int i) {
        if (i >= 12) {
            throw new IndexOutOfBoundsException("Months are 0-11 in the system sorry");
        }
        return months[i];
    }

    /**
     * Gets a month object by month name BY MODEL TRANSLATOR
     * @param s: string name
     * @return the month object
     */
    public MyMonth getMonthByName(String s) {
        return months[mt.monthsIntByName.get(s)];
    }

    @Override
    public String toString() {
        return "MyYear{" +
                "value = " + value +
                ", months = " + Arrays.toString(months) +
                "}";
    }
}
