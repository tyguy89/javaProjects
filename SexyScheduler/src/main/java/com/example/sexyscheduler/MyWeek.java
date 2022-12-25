/*
Tyler Boechler
tjb404, 11294509
CMPT 370 - Team Aviato
 */

package com.example.sexyscheduler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class for a week of calendar class representation
 */
public class MyWeek implements Serializable {

    public int value;

    public MyDay[] days;

    private MyYear containerYear;

    /**
     * Initiaties a week object
     * @param value: value of week 0-3/4/5
     * @param startday: start day of week for year
     * @param month: parent month
     * @param leapYear: are we in a leap year?
     * @param ct: container year
     */
    public MyWeek(int value, String startday, MyMonth month, boolean leapYear, MyYear ct) {

        if (month.equivalentValue == 10) {
            System.out.println("yoyo");
        }
        ModelTranslator mt = ModelTranslator.getInstance(leapYear);

        containerYear = ct;
        days = new MyDay[7];

        int firststartDayIndex = mt.dayOfWeekIntByName.get(startday);

        this.value = value;

        if (this.value == 0 && month.equivalentValue == 0) {
            for (int i = 0; i < 7; i++) {
                if (i < firststartDayIndex) {
                    if (i > 0) {
                        MyDay prevDay = this.days[i - 1];
                        if (month.equivalentValue == 0) {
                            days[i] = new MyDay(mt.dayOfWeekNameByInt.get(i), i, prevDay.value + 1);
                        }
                    } else {
                        days[i] = new MyDay(mt.dayOfWeekNameByInt.get(i), i, 31 - firststartDayIndex + 1);
                    }
                }

                else {

                    for (int j = 0; j < 7-i; j++) {
                        days[i+j] = new MyDay(mt.dayOfWeekNameByInt.get(i+j), i+j, j+1);
                    }
                    return;
                }

            }
        }

        else  {
            for (int i = 0; i < 7; i++) {
                if(i > 0) {
                    MyDay tempBuilder = days[i-1];

                    if(this.value != 0) {
                        days[i] = new MyDay(mt.dayOfWeekNameByInt.get(i), i, tempBuilder.value+1);
                    }

                    else if (tempBuilder.value < mt.daysInMonthByName.get(mt.monthNames[mt.monthsIntByName.get(month.value)-1])) {
                        days[i] = new MyDay(mt.dayOfWeekNameByInt.get(i), i, tempBuilder.value+1);
                    }
                    else {
                        for (int j = i; j < 7; j++) {
                            days[j] = new MyDay(mt.dayOfWeekNameByInt.get(j), j, j-i+1);
                        }
                        return;
                    }
                }
                else {

                    if (this.value != 0) {

                        days[i] = new MyDay(mt.dayOfWeekNameByInt.get(i), i, month.weeks[this.value-1].days[6].value+1);
                    }
                    else {

                        if (containerYear.months[month.equivalentValue-1].weeks[containerYear.months[month.equivalentValue-1].weeks.length-1].days[6].value ==  mt.daysInMonthByName.get(mt.monthNames[mt.monthsIntByName.get(month.value)-1])) {
                            days[i] = new MyDay(mt.dayOfWeekNameByInt.get(i), i, 1);
                        }
                        else {
                            days[i] = new MyDay(mt.dayOfWeekNameByInt.get(i), i, containerYear.months[month.equivalentValue-1].weeks[containerYear.months[month.equivalentValue-1].weeks.length-1].days[6].value+1);
                        }

                    }
                }
            }
        }

    }

    /**
     * Does the week have the day of month searched for?
     * @param i: day of month
     * @return boolean
     */
    public boolean hasDayByDateOfMonth(int i) {
        for (int j = 0; j < 7; j++) {
            if(this.days[j].value == i) {
                return true;
            }
        }
        return false;

    }


    /**
     * Returns day object for searched day
     * @param i
     * @return
     * @throws IllegalArgumentException if day not in week
     */
    public MyDay getDayByDateOfMonth(int i) {
        for (int j = 0; j < 7; j++) {
            if(this.days[j].value == i) {
                return this.days[j];
            }
        }
        throw new IllegalArgumentException("Day not in searched week");
    }

    /**
     * Returns start days of the month in a week, or the last days of the month before depending on a boolean inout
     * @param startDays: the mode of the function (true = start days, false = end days)
     * @return array list of day objects wanted
     */
    public ArrayList<MyDay> getStartOrEndMonthDays(boolean startDays) {
        if(value != 0) {
            throw new IllegalCallerException("Start of month days, end of last month days, only exist in the first week [0]");
        }

        ArrayList<MyDay> start =  new ArrayList<MyDay>();
        ArrayList<MyDay> end =  new ArrayList<MyDay>();

        int counter = 0;
        while (counter < 7) {
            MyDay testday = days[counter];
            if (testday.value >= 1 && testday.value < 7) {
                start.add(testday);
            }
            else if (testday.value > 20) {
                end.add(testday);
            }
            counter++;
        }

        if (start.size() == 0 || end.size() == 0) {
            return null;
        }

        if (startDays) {
            return start;
        }
        else {
            return end;
        }

    }


    @Override
    public String toString() {
        return "\nMyWeek{" +
                "value = " + value +
                ", days = " + Arrays.toString(days) +
                "}";
    }
}
