/*
Tyler Boechler
tjb404, 11294509
CMPT 370 - Team Aviato
 */

package com.example.sexyscheduler;

import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

public class CalendarModel implements Serializable {

    public MyYear[] years;
    private ArrayList<ModelListener> subscribers = new ArrayList<ModelListener>();

    public static boolean testing = false;
    public MyYear currentYear;
    public MyMonth currentMonth;
    public MyWeek currentWeek;
    public MyDay currentDay;
    public Hashtable<Month,String> mapMonthToString = new Hashtable<>();

    public int startYear = java.time.LocalDate.now().getYear();

    private static final String saveFile = "src/main/resources/save.txt";


    @Serial
    private static final long serialVersionUID  = 3523513519031234L;


    public CalendarScheduler scheduler;

    public ArrayList<String> filterList;

    /**
     * Creates instance of calendar class and constructs all day objects for
     * @param y : Amount of years made from current year
     * @param filterList
     */
    public CalendarModel(int y, ArrayList<String> filterList) {
        this.filterList = filterList;
        years = new MyYear[y];

        for (int i = 0; i < y; i++) {
            //create year objects, making all month/week/days
            years[i] = new MyYear(java.time.LocalDate.now().getYear()+i, java.time.Year.of(java.time.LocalDate.now().getYear()+i).atDay(1).getDayOfWeek().toString(), java.time.Year.of(java.time.LocalDate.now().getYear()+i).isLeap());
        }

        currentYear = years[0];
        currentMonth = currentYear.months[java.time.LocalDate.now().getMonthValue()-1];
        currentWeek = this.getWeekHoldingDayByNames(java.time.LocalDate.now().getYear(), currentMonth.value, LocalDate.EPOCH.getDayOfMonth());
        currentDay = currentWeek.getDayByDateOfMonth(LocalDate.EPOCH.getDayOfMonth());
        mapMonthToString.put(Month.JANUARY,"January");
        mapMonthToString.put(Month.FEBRUARY,"February");
        mapMonthToString.put(Month.MARCH,"March");
        mapMonthToString.put(Month.APRIL,"April");
        mapMonthToString.put(Month.MAY,"May");
        mapMonthToString.put(Month.JUNE,"June");
        mapMonthToString.put(Month.JULY,"July");
        mapMonthToString.put(Month.AUGUST,"August");
        mapMonthToString.put(Month.SEPTEMBER,"September");
        mapMonthToString.put(Month.OCTOBER,"October");
        mapMonthToString.put(Month.NOVEMBER,"November");
        mapMonthToString.put(Month.DECEMBER,"December");
        scheduler = CalendarScheduler.getInstance(this);   //ONLY INSTANCE OF SCHEDULER FOR MODEL
    }



    /**
     * @param yearIndex the year you want
     * @param month the month you want
     * @return all the My Day objects that are in that month
     */

    public ArrayList<MyDay> getDaysInMonthOnly(int yearIndex, int month) {

        ModelTranslator mt = ModelTranslator.getInstance(java.time.Year.of(yearIndex + startYear).isLeap());
        ArrayList<MyDay> returnDays = new ArrayList<MyDay>();

        MyMonth monthToEval = years[yearIndex].months[month];
        for (int i = 0; i <5 ; i++) {
            try {
                returnDays.addAll(List.of(monthToEval.weeks[i].days));
            }catch (Exception e){}
        }

        return returnDays;
    }



    /*
    /**
     * Gets All the Day objects from 1-End for that specific month in an arraylist
     * @param yearIndex: How many years after current year
     * @param month: Month index 0-11
     * @return ArrayList<MyDay> returns all the days in the month in an array list
     */
    /*
    public ArrayList<MyDay> getDaysInMonthOnly(int yearIndex, int month) {

        ModelTranslator mt = ModelTranslator.getInstance(java.time.Year.of(yearIndex + startYear).isLeap());

        ArrayList<MyDay> returnDays = new ArrayList<MyDay>();
        MyMonth monthWithEndDays;
        returnDays.ensureCapacity(mt.daysInMonthByName.get(mt.monthNames[month]));

        MyMonth monthToEval = years[yearIndex].months[month];
        if (month == 11) {
            monthWithEndDays = years[yearIndex+1].months[0];
        }
        monthWithEndDays = years[yearIndex].months[month+1];
        if (monthWithEndDays.weeks[0].days[0].value < 7) {
            monthWithEndDays = null;
        }
        returnDays.addAll(monthToEval.weeks[0].getStartOrEndMonthDays(true));

        for (int i = 1; i < monthToEval.weeks.length; i++) {
            returnDays.addAll(List.of(monthToEval.weeks[i].days));
        }

        if (monthWithEndDays != null) {
            returnDays.addAll(monthWithEndDays.weeks[0].getStartOrEndMonthDays(false));
        }

        return returnDays;
    }

     */



    /**
     * Get Year object for int value
     * @param year: Year value
     * @return MyYear object of corresponding value
     */
    public MyYear getYearByYearValue(int year) {
        return years[year-startYear];
    }

    /**
     *Get year object for int value
     * @param index: Index after current year value
     * @return MyYear object of corresponding value
     */
    public MyYear getYearByIndex(int index) {
        return years[index];
    }

    /**
     * Gets month object based off year value and month name
     * @param year: value of year
     * @param name: name of month
     * @return MyMonth object
     */
    public MyMonth getMonthByNameYearVal(int year, String name) {
        ModelTranslator mt = ModelTranslator.getInstance(years[year-startYear].isLeap);
        return years[year-startYear].getMonthByName(name);
    }

    /**
     * Gets month object based off yearindex and month index
     * @param yearIndex: years after current year
     * @param monthIndex: 0-11
     * @return MyMonth object
     */
    public MyMonth getMonthByIndices(int yearIndex, int monthIndex) {
        ModelTranslator mt = ModelTranslator.getInstance(years[yearIndex].isLeap);
        return years[yearIndex].getMonthByInt(monthIndex);
    }


    /**
     * Get week object that holds day you are looking for
     * @param year: year name
     * @param month: month name
     * @param day: day of month
     * @return MyWeek object
     */
    public MyWeek getWeekHoldingDayByNames(int year, String month, int day) {
        ModelTranslator mt = ModelTranslator.getInstance(years[year-startYear].isLeap);
        MyWeek[] thisMonth = years[year-startYear].getMonthByName(month).weeks;

        for (int i = 0; i < thisMonth.length; i++) {
            if (thisMonth[i].hasDayByDateOfMonth(day)) {
                return thisMonth[i];    //get day in month
            }
        }
        //get in next month
        if (thisMonth[thisMonth.length-1].value != mt.daysInMonthByName.get(month)) {
            return years[year-startYear].getMonthByName(mt.monthsNameByInt.get(mt.monthsIntByName.get(month)+1)).weeks[0];
        }
        throw new IllegalArgumentException();
    }

    /**
     * Get Week object off values
     * @param yearI: years after current year
     * @param monthI: 0-11
     * @param weekI: 0-4/5
     * @return MyWeek object
     */
    public MyWeek getWeekByIndicies(int yearI, int monthI, int weekI) {
        return years[yearI].getMonthByInt(monthI).getWeekByInt(weekI);
    }

    /**
     * Get a day object off names
     * @param year: year value
     * @param month: month name
     * @param day: day of month
     * @return MyDay object
     */
    public MyDay getDayByNames(int year, String month, int day) {
        return getWeekHoldingDayByNames(year, month, day).getDayByDateOfMonth(day);
    }

    /**
     * Get day object with indicies
     * @param yearI: years after current year
     * @param monthI: 0-11
     * @param weekI: 0-4/5
     * @param dayI: 0-6
     * @return MyDay object
     */
    public MyDay getDayByIndicies(int yearI, int monthI, int weekI, int dayI) {
        return years[yearI].getMonthByInt(monthI).getWeekByInt(weekI).days[dayI];
    }

    /**
     * gets all Week objects that hold days from searched months
     * @param yearIndex: years after current year
     * @param month: 0-11
     * @return Arraylist of week objects
     */
    public ArrayList<MyWeek> getDaysInMonthAllWeeks(int yearIndex, int month) {
        ModelTranslator mt = ModelTranslator.getInstance(java.time.Year.of(yearIndex + startYear).isLeap());

        MyMonth monthWithEndDays;
        //Need to extract first week of days

        MyMonth monthToEval = years[yearIndex].months[month];
        //Need to extract all weeks of this month

        //december -> January
        if (month == 11) {
            monthWithEndDays = years[yearIndex+1].months[0];
        }else {
            monthWithEndDays = years[yearIndex].months[month + 1];
        }

        if (monthWithEndDays.weeks[0].days[0].value == 1) {
            monthWithEndDays = null;    //Perfect ending month
        }

        ArrayList<MyWeek> returnWeeks = new ArrayList<MyWeek>(List.of(monthToEval.weeks));

        if (monthWithEndDays != null) {
            returnWeeks.add(monthWithEndDays.weeks[0]);
        }

        return returnWeeks;

    }



    public void createEvent() {
        notifySubscribers();
    }
    public void createErrorEvent(EventErrorView error) {
        error.showError();
        //updateSubscriber(error);
    }

    public void notifySubscribers(){
        subscribers.forEach(ModelListener::modelChanged);
        save(this);
    }


    public boolean updateSubscriber(ModelListener m) {
        try {
            subscribers.get(subscribers.indexOf(m)).modelChanged();
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    public void addSubscribers(ModelListener m){
        subscribers.add(m);
    };

    public void addFilterList(ArrayList<String> fl) {filterList = fl;}

    /**
     *  Saves current instance of parameter in save file after clearing it
     * @param model: the model to save, should be "this"
     */
    public static void save(CalendarModel model) {
        ArrayList<ModelListener> save = null;

        if (model.subscribers != null) {
            save = model.subscribers;
            model.subscribers = new ArrayList<ModelListener>();
        }

        try (FileOutputStream fileOutputStream = new FileOutputStream(saveFile);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);) {
            fileOutputStream.write(("").getBytes());
            objectOutputStream.writeObject(model);
        } catch (FileNotFoundException e) {
            System.out.println("Save file not found");
            e.printStackTrace();
        }
        catch (IOException e) {
            System.out.println("Error in saving model file");
            e.printStackTrace();
        }

        model.subscribers = save;
    }

    /**
     * Restore save file into CalendarModel object upon opening
     * @return
     */
    public static CalendarModel restore() {
        CalendarModel model = null;

        try {
            try (FileInputStream fileInputStream = new FileInputStream(saveFile);
                 ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
                model = (CalendarModel) objectInputStream.readObject();
            } catch (FileNotFoundException e) {
                System.out.println("Error finding save file");
                //e.printStackTrace();
                return null;
            } catch (ClassNotFoundException e) {
                System.out.println("Error converting save file to object");
                //e.printStackTrace();
                return null;
            } catch (IOException e) {
                System.out.println("Error reading save file");
                //e.printStackTrace();
                return null;
            }
        } catch (Exception e) {
            return null;
        }

        return model;
    }


    @Override
    public String toString() {
        return "SexyModel {" +
                ", currentYear = " + currentYear +
                ", startYear = " + startYear +
                "years = " + Arrays.toString(years) +
                '}';
    }

    public static void main(String[] args) {

        //MAIN TESTING FOR MODEL, MYYEAR/MYMONTH/MYWEEK/MYDAY/EVENT/EVENTSCHEDULER/MODELTRANSLATOR
        if (testing) {
            int testScore = 0;
            int testMax = 21;
            int failedTests = 0;
            int criticalFailures = 0;


            CalendarModel sm  = new CalendarModel(2, new ArrayList<String>());

            if (sm != null) {
                testScore += 1;
            }
            else {
                failedTests++;
                criticalFailures++;
            }

            if (sm.scheduler != null) {
                testScore += 1;
            }
            else {
                failedTests++;
                criticalFailures++;
            }

            /*
            System.out.println("-=-=-=-=-=-=-=-=-=-");
            System.out.println(sm.getDaysInMonthAllWeeks(0, 0));

            System.out.println("-=-=-=-=-=-=-=-=-=-");
            System.out.println(sm.getDayByNames(2022, "January", 2));

            System.out.println("-=-=-=-=-=-=-=-=-=-");
            System.out.println(sm.getDaysInMonthAllWeeks(0, 9));

            System.out.println("-=-=-=-=-=-=-=-=-=-");
            System.out.println(sm.getDaysInMonthOnly(0, 9));

            System.out.println(sm.getDayByIndices(0, 0, 2, 3)); // year 0 = 2022, month 0 = Jan, week 2 = 3rd week in struc, 3rd day in week
            System.out.println(sm.getMonthByNameYearVal(2022, "November"));
            System.out.println(sm.getWeekHoldingDayByNames(2022, "September", 16));

            System.out.println(sm.getYearByIndex(1));

            System.out.println(sm.getYearByYearValue(2023));
            System.out.println(sm.getMonthByIndices(1, 2));
            System.out.println(sm.getWeekByIndices(0, 0, 0));
             */

            testScore += 11;


            //Testing that my call to get first day of week for year is correct
            String[] testSolution = ("SATURDAY,SUNDAY,MONDAY,WEDNESDAY,THURSDAY").split(",", 0);

            if (java.time.LocalDate.now().getYear() == 2022) {
                for (int i = 0; i < 5; i++) {
                    if (!(testSolution[i].equals(java.time.Year.of(java.time.LocalDate.now().getYear() + i).atDay(1).getDayOfWeek().toString()))) {
                        criticalFailures += 1;
                        failedTests++;
                    }
                    else {
                        testScore += 1;
                    }

                }
            }


            //Object indexing,
            if (!sm.scheduler.createAppointmentEventByMyDay(sm.currentYear.months[0].getWeekByInt(2).days[0], "hi", "02:00", "05:00", 0, "BLUE", "WORK")) {
                criticalFailures++;
                failedTests++;
            }

            //Accessing day, Deleting event
            MyDay specificDay = sm.getDayByNames(sm.currentYear.value, "January", 9);
            testScore++;

            try {
                boolean success = specificDay.deleteEvent(specificDay.events.get(0));
                if (success) {
                    testScore += 2;
                }
                else {
                    failedTests += 1;
                }
            }catch (Exception e) {
                failedTests += 1;
                criticalFailures += 1;
            }


            System.out.println("TESTING RESULTS: " + "Score: " + testScore + " / " + testMax);
            System.out.println("Failures: " + failedTests);
            System.out.println("Critical Failures: " + criticalFailures);

        }
    }
}
