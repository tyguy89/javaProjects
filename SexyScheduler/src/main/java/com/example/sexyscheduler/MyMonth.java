/*
Tyler Boechler
tjb404, 11294509
CMPT 370 - Team Aviato
 */

package com.example.sexyscheduler;
import java.io.Serializable;
import java.util.Arrays;

/**
 * Instance of month object for calendar
 */
public class MyMonth implements Serializable {
    public String value;
    public int equivalentValue;

    public MyWeek[] weeks;
    private final ModelTranslator mt;
    private final MyYear containerYear;

    /**
     * Creates instance of month object
     * @param value: String name of month
     * @param equivalentValue: 0-11
     * @param startDay: start dow
     * @param leapYear: is leap year?
     * @param ct: container year
     */
    public MyMonth(String value, int equivalentValue, String startDay, boolean leapYear, MyYear ct) {
        containerYear = ct;
        mt = ModelTranslator.getInstance(leapYear);
        this.value = value;
        this.value = value;
        this.equivalentValue = equivalentValue;


        if (equivalentValue == 0) {
            if (mt.dayOfWeekIntByName.get(startDay) >= 4) {
                weeks = new MyWeek[5];
                fillWeeks(weeks, startDay, this, leapYear);
            }

            else {
                weeks = new MyWeek[4];
                fillWeeks(weeks, startDay, this, leapYear);
            }
        }


        else if (equivalentValue == 1) {
            weeks = new MyWeek[4];
            fillWeeks(weeks, startDay, this, leapYear);
        }

        else {
            MyWeek[] containerW = new MyWeek[4];
            this.weeks = containerW;
            fillWeeks(containerW, startDay, this, leapYear);
            if (containerW[3].days[6].value + 6 < mt.daysInMonthByName.get(this.value)) {
                MyWeek[] returnWeeks = new MyWeek[5];
                for (int i = 0; i < 4; i++) {
                    returnWeeks[i] = containerW[i];
                }
                returnWeeks[4] = new MyWeek(4, startDay, this, leapYear, containerYear);
                this.weeks = returnWeeks;
            }
        }
    }

    /**
     * Gets month value
     * @return string of month name
     */
    public String getValue() {
        return value;
    }

    /**
     * Gets the week by index
     * @param i: integer i as index
     * @return null on invalid index
     */
    public MyWeek getWeekByInt(int i) {
        if (i >= weeks.length) {
            return null;
        }
        return weeks[i];
    }

    /**
     * Helper building function to build and add week objects into array
     */
    private void fillWeeks(MyWeek[] weeks, String startDay, MyMonth month, boolean ly) {
        for (int i = 0; i < weeks.length; i++) {
            weeks[i] = new MyWeek(i, startDay, month, ly, containerYear);
        }

    }

    @Override
    public String toString() {
        return "MyMonth{" +
                "value = " + value +
                ", equivalentValue = " + equivalentValue +
                ", weeks = " + Arrays.toString(weeks)
                +"}";

    }
}
