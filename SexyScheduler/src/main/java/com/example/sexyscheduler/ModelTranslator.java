/*
Tyler Boechler
tjb404, 11294509
CMPT 370 - Team Aviato
 */

package com.example.sexyscheduler;

import java.io.Serializable;
import java.util.Hashtable;

/**
 * Singleton class with two variations on models on boolean
 */
public class ModelTranslator implements Serializable {

    //All hashtables for easy/fast data access
    public static Hashtable<String, Integer> dayOfWeekIntByName;
    public static Hashtable<Integer, String> dayOfWeekNameByInt;
    public static Hashtable<Integer, String> monthsNameByInt;
    public static Hashtable<String, Integer> monthsIntByName;
    public static Hashtable<String, Integer> daysInMonthByName;


    private static ModelTranslator single_instance = null;
    private static ModelTranslator single_instance_leap_year = null;

    static String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    /**
     * Creates new ModelTranslator object
     * @param leapYear
     */
    public ModelTranslator(boolean leapYear) {
        daysInMonthByName = new Hashtable<String, Integer>(12);
        for (int i = 0; i < 12; i++) {
            if (i == 0 || i == 2 || i == 4 || i == 6 || i == 7 || i == 9 || i == 11) {
                daysInMonthByName.put(monthNames[i], 31);
            }
            else if (i == 3 || i == 5 || i == 8 || i == 10) {
                daysInMonthByName.put(monthNames[i], 30);
            }
            else {
                if (leapYear) {
                    daysInMonthByName.put(monthNames[i], 29);
                }
                else {
                    daysInMonthByName.put(monthNames[i], 28);
                }
            }

        }


        monthsNameByInt = new Hashtable<Integer, String>(12);
        for (int i = 0; i < 12; i++) {
            monthsNameByInt.put(i, monthNames[i]);
        }
        monthsNameByInt.put(12, "January");//rollover reduces errors

        monthsIntByName = new Hashtable<String, Integer>(12);
        for (int i = 0; i < 12; i++) {
            monthsIntByName.put(monthNames[i], i);
        }


        dayOfWeekIntByName = new Hashtable<String, Integer>(7);
        dayOfWeekIntByName.put("SUNDAY", 0);
        dayOfWeekIntByName.put("MONDAY", 1);
        dayOfWeekIntByName.put("TUESDAY", 2);
        dayOfWeekIntByName.put("WEDNESDAY", 3);
        dayOfWeekIntByName.put("THURSDAY", 4);
        dayOfWeekIntByName.put("FRIDAY", 5);
        dayOfWeekIntByName.put("SATURDAY", 6);

        dayOfWeekNameByInt = new Hashtable<Integer, String>(7);
        dayOfWeekNameByInt.put(-1, "Saturday");//rollover reduces errors
        dayOfWeekNameByInt.put(0, "Sunday");
        dayOfWeekNameByInt.put(1, "Monday");
        dayOfWeekNameByInt.put(2, "Tuesday");
        dayOfWeekNameByInt.put(3, "Wednesday");
        dayOfWeekNameByInt.put(4, "Thursday");
        dayOfWeekNameByInt.put(5, "Friday");
        dayOfWeekNameByInt.put(6, "Saturday");
        dayOfWeekNameByInt.put(7, "Sunday");    //rollover reduces errors

    }

    /**
     * Returns only one/2 modeltranslator object at a time
     * @param isLeap: is leap year boolean
     * @return object ModelTranslator
     */
    public static ModelTranslator getInstance(boolean isLeap) {
        if (isLeap) {
            if (single_instance_leap_year == null) {
                single_instance_leap_year = new ModelTranslator(isLeap);
            }
            return single_instance_leap_year;
        }
        else {
            if (single_instance == null) {
                single_instance = new ModelTranslator(isLeap);
            }
            return single_instance;
        }

    }
}
