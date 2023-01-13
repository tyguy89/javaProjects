/*
Tyler Boechler
tjb404, 11294509
CMPT 370 - Team Aviato
 */

package com.example.sexyscheduler;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Singleton scheduler class for calendar model
 */
public class CalendarScheduler implements Serializable {
    private static ModelTranslator mt;
    private static CalendarScheduler single_instance = null;
    private CalendarModel model;

    /**
     * Creates new scheduler object
     *
     * @param useModel: database model object
     */
    public CalendarScheduler(CalendarModel useModel) {

        model = useModel;
    }

    /**
     * Creates new event object with a day object
     *
     * @param day     day object to add event
     * @param title:  name of event
     * @param start:  start of event in XX:XX 24HR
     * @param end:    end of event^
     * @param repeat: times it repeats
     * @param colour: colour
     * @param tag:    the tag of the class
     * @return boolean on success
     */
    public boolean createAppointmentEventByMyDay(MyDay day, String title, String start, String end, int repeat, String colour, String tag) {
        boolean didWork = day.addEvent(new AppointmentEvent(title, start, end, tag, day));
        model.notifySubscribers();
        return didWork;
    }


    public boolean createDeadlineEventByDay(MyDay day, String title, String time, String tag) {
        DeadlineEvent d = new DeadlineEvent(title, tag, time);
        boolean didWork = day.addEvent(d);
        model.notifySubscribers();
        return didWork;
    }

    /**
     * Creates event on a day
     * @param year: year of event
     * @param month: name of month of event
     * @param week: week id of event
     * @param day: day of event
     * @param title: name of event
     * @param start: start time of event same as above method
     * @param end: end ^
     * @param repeat: does repeat
     * @param colour: string colour
     * @param tag: tag
     * @return boolean on success
     */
/*    public boolean createEventByNames(int year, int month, int week, int day, String title, String start, String end, int repeat, String colour, String tag) {
        //Get day without model methods
        MyYear currentYear = null;
        for (MyYear myYear : model.years) {
            if (myYear.value == year) {
                currentYear = myYear;
            }
        }
        if (currentYear == null) {
            throw new IllegalArgumentException();
        }
        boolean didWork = currentYear.getMonthByInt(month).getWeekByInt(week).getDayByDateOfMonth(day).addEvent(new AppointmentEvent(title, start, end, tag));
        model.notifySubscribers();
        return didWork;
        //return currentYear.getMonthByName(month).getWeekByInt(week).getDayByDateOfMonth(day).addEvent(new Event(title, start, end, repeat, colour, tag));
    }*/


    /**
     * Creates new Repeatable event over period of time
     *
     * @param mode:       the mode events should be added in MODES 0 = Every day, 1 = Every week, 2 = Every month
     * @param :           the base repeatable event to be repeated
     * @param startYear:  start year value
     * @param startMonth: start month value
     * @param startDay:   start day value
     * @param endYear
     * @param endMonth
     * @param endDay
     * @return: boolean on success, true, false if any error
     *//*
    public boolean createRepeatableEvent(int mode, AppointmentEvent baseEvent, int startYear, String startMonth, int startDay, int endYear, String endMonth, int endDay, boolean weird) {
        //MODES 0 = Every day, 1 = Every week, 2 = Every month

        ArrayList<AppointmentEvent> sib = new ArrayList<>();
        boolean ichangedMonth = false;

        //Iterate from start to end and add events at intervals depending on mode, break when done or failure
        while (true) {
            if (mode == 0) {//every day
//                if (weird) {
//                    for (int i = model.getDayByNames(startYear, startMonth, startDay).correspondingDayOfWeek; i < 7-model.getDayByNames(startYear, startMonth, startDay).correspondingDayOfWeek; i++) {
//                        System.out.println(startDay);
//                        model.getWeekHoldingDayByNames(startYear, startMonth, startDay).days[i].addEvent(new AppointmentEvent(baseEvent.title, baseEvent.start, baseEvent.end,baseEvent.tag, model.getDayByNames(startYear,startMonth,startDay)));
//                        startDay++;
//
//                        if (startDay > ModelTranslator.daysInMonthByName.get(ModelTranslator.monthsNameByInt.get(ModelTranslator.monthsIntByName.get(startMonth)-1))) {
//                            startDay = 1;
//                        }
//                    }
//                    startMonth = ModelTranslator.monthsNameByInt.get(ModelTranslator.monthsIntByName.get(startMonth)+1);
//                    weird = false;
//                }
                System.out.println(startDay + startMonth);
                AppointmentEvent rp = new AppointmentEvent(baseEvent.title, baseEvent.start, baseEvent.end,baseEvent.tag, model.getDayByNames(startYear,startMonth,startDay));
                if (!model.getDayByNames(startYear, startMonth, startDay).addEvent(rp)) {
                    deleteEventByDay(model.getDayByNames(startYear, startMonth, startDay), rp);//conflict
                    return false;
                }
                sib.add(rp);

                if (startYear == endYear && startMonth.equals(endMonth) && startDay == endDay) {
                    break;
                }

                startDay++;

                if (!ichangedMonth && startDay > ModelTranslator.daysInMonthByName.get(ModelTranslator.monthsNameByInt.get(ModelTranslator.monthsIntByName.get(startMonth)))) {
                    startDay = 1;
                    startMonth = ModelTranslator.monthsNameByInt.get(ModelTranslator.monthsIntByName.get(startMonth) + 1);//next month
                    if (startMonth.equals("January")) {
                        startYear++;//next year
                    }
                    weird = false;

                }

                if (ichangedMonth && startDay > ModelTranslator.daysInMonthByName.get(ModelTranslator.monthsNameByInt.get(ModelTranslator.monthsIntByName.get(startMonth)-1))) {
                    startDay = 1;
                    ichangedMonth = false;

                }

                if (!startMonth.equals(model.getWeekHoldingDayByNames(startYear, startMonth, startDay).month.value) && !weird) {

                    startMonth = ModelTranslator.monthsNameByInt.get(ModelTranslator.monthsIntByName.get(startMonth) + 1);//next month
                    ichangedMonth = true;
                    if (startMonth.equals("January")) {
                        startYear++;//next year
                    }
                }


//                if (startDay > ModelTranslator.daysInMonthByName.get(ModelTranslator.monthsNameByInt.get(ModelTranslator.monthsIntByName.get(startMonth)-1))) {
//                    startDay = model.getDayByNames(startYear, ModelTranslator.monthsNameByInt.get(ModelTranslator.monthsIntByName.get(startMonth)-1), startDay).value;
//                    if (model.getDayByNames(startYear, ModelTranslator.monthsNameByInt.get(ModelTranslator.monthsIntByName.get(startMonth)-1), startDay).correspondingDayOfWeek == 6) {
//                        startMonth = ModelTranslator.monthsNameByInt.get(ModelTranslator.monthsIntByName.get(startMonth)+1);
//                    };
//                }
            }

             else if (mode == 1) {//every week
                AppointmentEvent rp = new AppointmentEvent(baseEvent.title, baseEvent.start, baseEvent.end,baseEvent.tag, model.getDayByNames(startYear,startMonth,startDay));
                if (!model.getDayByNames(startYear, startMonth, startDay).addEvent(rp)) {
                    deleteEventByDay(model.getDayByNames(startYear, startMonth, startDay), rp); //conflict
                    return false;
                }
                sib.add(rp);
                startDay += 7;
                if (startDay > ModelTranslator.daysInMonthByName.get(startMonth)) {
                    if (startMonth.equals(endMonth) && startYear == endYear) {
                        break;
                    } else {
                        startMonth = ModelTranslator.monthsNameByInt.get(ModelTranslator.monthsIntByName.get(startMonth) + 1);
                        if (startMonth.equals("January")) {
                            startYear++;
                        }
                    }
                }


                if (startYear >= endYear && startMonth.equals(endMonth) && startDay > endDay) {

                    break;
                }



            } else if (mode == 2) {//every month
                if (startYear >= endYear && startMonth.equals(endMonth) && startDay > endDay) {
                    break;
                } else {
                    startMonth = ModelTranslator.monthsNameByInt.get(ModelTranslator.monthsIntByName.get(startMonth) + 1);
                    if (startMonth.equals("January")) {
                        startYear++;
                    }
                    if (startDay <= ModelTranslator.daysInMonthByName.get(startMonth)) {
                        AppointmentEvent rp = new AppointmentEvent(baseEvent.title, baseEvent.start, baseEvent.end,baseEvent.tag, model.getDayByNames(startYear,startMonth,startDay));
                        if (!model.getDayByNames(startYear, startMonth, startDay).addEvent(rp)) {
                            deleteEventByDay(model.getDayByNames(startYear, startMonth, startDay), rp);
                            return false;//conflict
                        }
                        sib.add(rp);
                    }
                    else {//last day of month if past days in month
                        AppointmentEvent rp = new AppointmentEvent(baseEvent.title, baseEvent.start, baseEvent.end,baseEvent.tag, model.getDayByNames(startYear,startMonth,startDay));
                        if (!model.getDayByNames(startYear, startMonth, ModelTranslator.daysInMonthByName.get(startMonth)).addEvent(rp)) {
                            deleteEventByDay(model.getDayByNames(startYear, startMonth, ModelTranslator.daysInMonthByName.get(startMonth)), rp);
                            return false;
                        }
                        sib.add(rp);
                    }
                }

            } else {
                throw new IllegalArgumentException("Must be 0-2");
            }

        }
        for (AppointmentEvent r: sib) {
            r.siblings = sib;   //set siblings
        }
        return true;
    }*/
    public boolean createRepeatableEvent(int mode, int startYear, String startMonth, int startDay, int endYear, String endMonth, int endDay, String title, String tag, String startTime, String endTime) {
        //MODES 0 = Every day, 1 = Every week, 2 = Every month
        System.out.println(startYear + startMonth + startDay);
        ArrayList<AppointmentEvent> sib = new ArrayList<>();
        MyDay start, end;
        try {
            if (model.getWeekHoldingDayByNames(startYear, startMonth, startDay).value == 0) {
                if (startDay < 15) {
                    startMonth = ModelTranslator.monthsNameByInt.get(ModelTranslator.monthsIntByName.get(startMonth));

                }
                else {
                    startMonth = ModelTranslator.monthsNameByInt.get(ModelTranslator.monthsIntByName.get(startMonth)+1);
                }
            }
            ;
            start = model.getDayByNames(startYear, startMonth, startDay);
            System.out.println(startMonth + start);

        } catch (Exception e) {
            if (startMonth.equals("January")) {
                start = model.getDayByNames(startYear - 1, "December", startDay);
                startYear--;

            } else {
                start = model.getDayByNames(startYear, ModelTranslator.monthsNameByInt.get(ModelTranslator.monthsIntByName.get(startMonth)), startDay);
                startMonth = ModelTranslator.monthsNameByInt.get(ModelTranslator.monthsIntByName.get(startMonth));
            }
        }

        try {
            if (model.getWeekHoldingDayByNames(endYear, endMonth, endDay).value == 0) {
                if (endDay < 25) {
                    endMonth = ModelTranslator.monthsNameByInt.get(ModelTranslator.monthsIntByName.get(endMonth));

                } else {
                    endMonth = ModelTranslator.monthsNameByInt.get(ModelTranslator.monthsIntByName.get(endMonth) + 1);

                }

            }
            end = model.getDayByNames(endYear, endMonth, endDay);

        } catch (Exception e) {
            if (endMonth.equals("January")) {
                end = model.getDayByNames(endYear - 1, "December", endDay);
                endYear--;
            } else {
                end = model.getDayByNames(endYear, ModelTranslator.monthsNameByInt.get(ModelTranslator.monthsIntByName.get(endMonth)), endDay);
                endMonth = ModelTranslator.monthsNameByInt.get(ModelTranslator.monthsIntByName.get(endMonth));
            }
        }

        ArrayList<MyYear> years = new ArrayList<>();


        for (int i = 0; i < endYear - startYear + 1; i++) {
            if (model.getYearByYearValue(startYear + i).equals(model.getYearByYearValue(endYear))) {

                years.add(i, model.getYearByYearValue(startYear + i));
                break;
            } else {
                /*if(startMonth.equals("January") && !years.contains((model.getYearByYearValue(startYear-1)))) {
                    years.add(model.getYearByYearValue(startYear-1));
                }*/
                years.add(i, model.getYearByYearValue(startYear + i));
            }
        }


        ArrayList<MyDay> allDays = new ArrayList<>();
        boolean fill = false;

        int dayOfWeekIndex = start.correspondingDayOfWeek;

        System.out.println(dayOfWeekIndex);
        System.out.println(startMonth);
        System.out.println(start);

        int dayofMonthIndex = start.value;

        boolean done = false;

        for (MyYear year : years) {
            if (done) {
                break;
            }
            for (MyMonth month : year.months) {
                if (done) {
                    break;
                }
                for (MyWeek week : month.weeks) {
                    if (done) {
                        break;
                    }

                    for (MyDay day : week.days) {
                        System.out.println(day);
                        if (done) {
                            break;
                        }

                        if (mode == 0) {
                            if (fill || day.equals(start)) {
                                fill = true;
                                allDays.add(day);
                                if (day.equals(end)) {
                                    done = true;
                                }
                            }
                        } else if (mode == 1) {
                            if (fill || day.equals(start)) {

                                fill = true;

                                if (day.correspondingDayOfWeek == dayOfWeekIndex) {
                                    System.out.println(day);
                                    allDays.add(day);
                                }

                                if (day.equals(end)) {
                                    done = true;
                                }
                            }

                        } else if (mode == 2) {
                            if (fill || day.equals(start)) {

                                fill = true;
                                if (day.value == dayofMonthIndex) {
                                    allDays.add(day);
                                }

                                if (day.equals(end)) {
                                    done = true;
                                }
                            }
                        }
                    }
                }
            }
        }


        boolean success;
        for (MyDay day : allDays) {
            AppointmentEvent eventAdding = new AppointmentEvent(title, startTime, endTime, tag, day);
            success = day.addEvent(eventAdding);

            if (!success) {
                day.deleteEvent(eventAdding.siblings.get(0));
                model.notifySubscribers();
                return false;
            }

            sib.add(eventAdding);
            eventAdding.siblings = sib;

        }
        model.notifySubscribers();
        return true;
    }


    public ArrayList<MyDay> automaticEventGetDays(int yearStart, String monthStart, int dayStart, int yearEnd, String monthEnd, int dayEnd) {
        ArrayList<AppointmentEvent> sib = new ArrayList<>();
        MyDay start, end;
        try {
            if (model.getWeekHoldingDayByNames(yearStart, monthStart, dayStart).value == 0) {
                monthStart = ModelTranslator.monthsNameByInt.get(ModelTranslator.monthsIntByName.get(monthStart) + 1);
            }
            ;
            start = model.getDayByNames(yearStart, monthStart, dayStart);

        } catch (Exception e) {
            System.out.println("Yo");
            if (monthStart.equals("January")) {
                start = model.getDayByNames(yearStart - 1, "December", dayStart);
                yearStart--;

            } else {
                start = model.getDayByNames(yearStart, ModelTranslator.monthsNameByInt.get(ModelTranslator.monthsIntByName.get(monthStart)), dayStart);
                monthStart = ModelTranslator.monthsNameByInt.get(ModelTranslator.monthsIntByName.get(monthStart));
            }
        }

        try {
            if (model.getWeekHoldingDayByNames(yearEnd, monthEnd, dayEnd).value == 0) {
                monthEnd = ModelTranslator.monthsNameByInt.get(ModelTranslator.monthsIntByName.get(monthEnd) + 1);
            }
            ;
            end = model.getDayByNames(yearEnd, monthEnd, dayEnd);
        } catch (Exception e) {
            if (monthEnd.equals("January")) {
                end = model.getDayByNames(yearEnd - 1, "December", dayEnd);
                yearEnd--;
            } else {
                end = model.getDayByNames(yearEnd, ModelTranslator.monthsNameByInt.get(ModelTranslator.monthsIntByName.get(monthEnd)), dayEnd);
                monthEnd = ModelTranslator.monthsNameByInt.get(ModelTranslator.monthsIntByName.get(monthStart));
            }
        }

        ArrayList<MyYear> years = new ArrayList<>();


        for (int i = 0; i < yearEnd - yearStart + 1; i++) {
            if (model.getYearByYearValue(yearStart + i).equals(model.getYearByYearValue(yearEnd))) {

                years.add(i, model.getYearByYearValue(yearStart + i));
                break;
            } else {
                /*if(startMonth.equals("January") && !years.contains((model.getYearByYearValue(startYear-1)))) {
                    years.add(model.getYearByYearValue(startYear-1));
                }*/
                years.add(i, model.getYearByYearValue(yearStart + i));
            }
        }


        ArrayList<MyDay> allDays = new ArrayList<>();
        boolean fill = false;
        int dayOfWeekIndex = start.correspondingDayOfWeek;
        int dayofMonthIndex = start.value;

        boolean done = false;

        for (MyYear year : years) {
            if (done) {
                break;
            }
            for (MyMonth month : year.months) {
                if (done) {
                    break;
                }
                for (MyWeek week : month.weeks) {
                    if (done) {
                        break;
                    }

                    for (MyDay day : week.days) {
                        if (done) {
                            break;
                        }
                        if (fill || day.equals(start)) {
                            fill = true;
                            allDays.add(day);
                            if (day.equals(end)) {
                                done = true;
                            }

                        }

                    }
                }
            }
        }

        return allDays;
    }


    /**
     * @param timeStart:     time to start scheduling
     * @param timeEnd:       time to not go past scheduling
     * @param coolDownHours: how many hours inbetween events
     * @param frequency:     amount of events in the period
     * @param duration:      how long the events are "1, 15" (hours, mins)
     * @param title:         event title
     * @param colour:        event colour
     * @param tag:           event tag
     * @return
     */
    public boolean createAutomaticEventByDays(int yearStart, String monthStart, int dayStart, int yearEnd, String monthEnd, int dayEnd, int days, String timeStart, String timeEnd, double coolDownHours, int frequency, String duration, String title, String colour, String tag) {
        ArrayList<MyDay> allDays = automaticEventGetDays(yearStart, monthStart, dayStart, yearEnd, monthEnd, dayEnd);

        ArrayList<AppointmentEvent> siblings = new ArrayList<>();
        AppointmentEvent e = new AppointmentEvent(title, timeStart, timeEnd, tag, allDays.get(0));    //New events always for unique serials

        if (frequency < 1) {
            throw new IllegalArgumentException("Frequency must be positive");
        }
       /*
        if (frequency == 1) {
            AutomaticEvent autoScheduled = tryScheduleBlock(e, start, end, timeStart, timeEnd, coolDownHours, duration);
            if (autoScheduled == null) {
                model.updateSubscribers();
                return false;
            }
            else {
                autoScheduled.siblings = siblings;
                model.updateSubscribers();
                return true;
            }
        }
        */
        else {
            int gap = 0;
            if (frequency < allDays.size()) { //If can, space the events out

                if (coolDownHours / 24.0 > 1) { //If the gap MUST be larger than a day
                    gap = (int) Math.ceil(coolDownHours / 24.0);
                } else {
                    gap = (int) Math.floor(allDays.size()/frequency);

                }

                boolean didWork = trySchedulePerDay(allDays, e, frequency, gap, duration);
                if (!didWork) {
                    deleteEventByDay(e.day, e); //Delete all siblings if failed
                }
                model.notifySubscribers();

                return didWork;


            } /*else if (coolDownHours < 6 && gap != 999) {
                boolean didWork = tryScheduleTwoPerDay(allDays, e, (int) Math.ceil(coolDownHours / 24.0), frequency, duration, coolDownHours);

                if (!didWork) {
                    deleteEventByDay(e.day, e); //Delete all siblings if failed
                }
                model.notifySubscribers();

                return didWork;
            }*/ else {
                return false;
            }

        }
        /*else {
            while (frequency > 0) {
                e.day = allDays.get(0);
                AppointmentEvent autoScheduled = tryScheduleBlock(e, timeEnd, duration);   //Try to fill events as compact as possible with cooldown


                if (autoScheduled != null) {
                    siblings.add(autoScheduled);
                    frequency--;
                    model.notifySubscribers();
                }
                else {
                    allDays.remove(0);
                    e.day = allDays.get(0);
                }

                if (allDays.size() >= 1 && frequency > 0 && autoScheduled == null) {
                    deleteEventByDay(e.day, e); //Made it to the end and not all events scheduled
                    model.notifySubscribers();
                    return false;
                }
                else if (allDays.size() <= 1 || frequency == 0) {
                    for (AppointmentEvent event: siblings) {
                        event.siblings = siblings;
                    }
                    return true;    //Made it and events scheduled in blocks
                }


*//*                    else {
                    double hours = Integer.parseInt(e.start.split(":")[0]) + coolDownHours;  //Update time in day
                    e.start += hours;
                    e.end += hours;
                }*//*
            }

        }
        e.siblings = siblings;      //set siblings
        return true;*/
    }



    /**
     *
     */
    private boolean trySchedulePerDay(ArrayList<MyDay> days, AppointmentEvent base, int frequency, int gap, String duration) {

        ArrayList<AppointmentEvent> sib = new ArrayList<>();
        boolean success;
        for (int i = 0; i < days.size(); i += gap) {
            if (frequency == 0) {
                return true;
            }
            AppointmentEvent e = new AppointmentEvent(base.title, base.start, base.end, base.tag, days.get(i));
            success = days.get(i).addEvent(e);
            if (!success) {
                AppointmentEvent tryMove = moveTime(e.day, e, e.end, duration);
                if (tryMove == null) {
                    return false;

                }
                else {
                    days.get(i).addEvent(tryMove);
                    frequency--;
                    sib.add(e);
                }
            }
            else {
                sib.add(e);
                frequency--;
            }

        }


        for (AppointmentEvent event: sib) {
            event.siblings = sib;
        }
        return frequency==0;
    }


       /* while (frequency > 0) {

            e = new AppointmentEvent(e.title, e.start, e.end, e.tag, start);

            if (!start.addEvent(e)) {   //Add at beginning
                AutomaticEvent newAuto = (AutomaticEvent) moveTime(start, e, timeEnd, duration);    //find next time on this day
                if (newAuto != null) {
                    start.addEvent(newAuto);
                    siblings.add(newAuto);
                    e = newAuto;
                    frequency--;
                }
            }
            else {
                siblings.add(e);    //we good
                frequency--;
            }

            if (start.equals(end) && frequency > 0) {
                return false;   //failure
            }

            if (start.value + gap >  ModelTranslator.daysInMonthByName.get(monthStart)) {
                if (ModelTranslator.monthsIntByName.get(monthStart) == 11) {
                    yearStart++;
                    monthStart = ModelTranslator.monthNames[0]; //next year
                }
                else {
                    monthStart = ModelTranslator.monthsNameByInt.get(ModelTranslator.monthsIntByName.get(monthStart)+1); //next month
                }
                start = model.getDayByNames(yearStart, monthStart, start.value+gap - ModelTranslator.daysInMonthByName.get(monthStart)); //update day

            }
            else {
                if (gap == 0) {
                    gap = 1;
                }
                start = model.getDayByNames(yearStart, monthStart, start.value+gap); //next day
            }
        }

        for (AppointmentEvent ae: siblings) {
            ae.siblings = siblings;
        }
        return true;
    }*/


    /**
     *Does two copies of schedule one per day with one offset
     * @return success
     */
    private boolean tryScheduleTwoPerDay(ArrayList<MyDay> days, AppointmentEvent e, int gap, int frequency, String duration, double cooldown) {
        boolean half1 = trySchedulePerDay(days, e, frequency, gap, duration);
        boolean half2 = trySchedulePerDay(days, new AppointmentEvent(e.title, String.valueOf(Integer.parseInt(e.start.split(":")[0])+cooldown+Integer.parseInt(duration.split(":")[0]))+":"+e.start.split(":")[1], String.valueOf(Integer.parseInt(e.end.split(":")[0])+cooldown+Integer.parseInt(duration.split(":")[0]))+":"+e.end.split(":")[1], e.tag, e.day), frequency, gap, duration);
        if (half1 && half2) {
            return true;

        }
        else {
            deleteEventByDay(e.day, e);
            return false;
        }
    }


    /**
     *
     * @param e: event
     * @param duration: event length
     * @return event that was added
     */
    private AppointmentEvent tryScheduleBlock(AppointmentEvent e, String timeEnd, String duration) {
        e = new AppointmentEvent(e.title, e.start, e.end, e.tag, e.day);

        if (e.day.addEvent(e)) {
            return e;
        }

        else {
            e = moveTime(e.day, e, timeEnd, duration);
            if (e.day.addEvent(e)) {
                return e;
            }
            else {
                return null; //too many conflicts
            }
        }
    }


    /**
     * Find next available slot to add event on day to be able to add "blocks"
     * @param d: day
     * @param e: event
     * @param endTime: end time
     * @param duration: event duration
     * @return: success as event with time setup or null on failure
     */
    private AppointmentEvent moveTime(MyDay d, AppointmentEvent e, String endTime, String duration) {
        if(d.events.size() == 0) {
            return new AutomaticEvent(e.title, e.tag, true, e.start, e.end);   //no conflicts
        }
        else {
            String conflict = d.isEventConflict(e);
            if (conflict==null) {
                return new AutomaticEvent(e.title, e.tag, true, e.start, e.end);//check conflicts
            }
            else {
                for (EventBase event: d.events) {
                    if (event instanceof AppointmentEvent) {
                        if (((AppointmentEvent) event).occupiedTimeSerial.equals(conflict)) {   //Start at next possible start time
                            e.start = ((AppointmentEvent) event).end;
                            int hours = Integer.parseInt(e.start.split(":")[0]);
                            int mins = Integer.parseInt(e.start.split(":")[1]);

                            if (mins + Integer.parseInt(duration.split(",")[1]) > 59) {
                                mins = (60- mins + Integer.parseInt(duration.split(",")[1]));
                                hours++;
                            }

                            if (hours + Integer.parseInt(duration.split(",")[0]) > 23) {
                                return null;
                            }
                            if (hours >= Integer.parseInt(endTime.split(":")[0]) && mins > Integer.parseInt(endTime.split(":")[1])) {
                                return null;
                            }

                            e.end = String.valueOf(hours)+":"+String.valueOf(mins);

                            return new AppointmentEvent(e.title, e.start, e.end, e.tag, e.day);
                        }
                    }

                }
            }
            throw new RuntimeException("Error somewhere in events");
        }
    }


    /**
     * Delete events
     * @param d
     * @param e
     * @return
     */
    public boolean deleteEventByDay(MyDay d, EventBase e) {
        boolean didWork = d.deleteEvent(e);
        model.notifySubscribers();
        return didWork;
    }






    /**
     * Only one instance of scheduler is needed, creates instance on first call, returns otherwise
     * @param model
     * @return CalendarScheduler
     */
    public static CalendarScheduler getInstance(CalendarModel model) {
        if (single_instance == null) {
            single_instance = new CalendarScheduler(model);
        }
        return single_instance;
    }

    /**
     * Different param method
     * @return instance
     */
/*
    public static CalendarScheduler getInstance() {
        return single_instance;
    }
*/



}
