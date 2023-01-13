/*
Tyler Boechler
tjb404, 11294509
CMPT 370 - Team Aviato
 */

package com.example.sexyscheduler;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class for day object in calendar
 */
public class MyDay implements Serializable {

    public ArrayList<EventBase> events;
    public ArrayList<String> occupiedTime = new ArrayList<String>();

    public final int value;
    public final int correspondingDayOfWeek;
    public final String dayofWeek;

    private int eventCounter = 0;

    /**
     * Makes new instance of MyDay for calendar
     * @param dayOfWeek: String name of dow
     * @param correspondingDayOfWeek: int val of dow 0-6
     * @param value day of month
     */
    public MyDay(String dayOfWeek, int correspondingDayOfWeek, int value) {
        events = new ArrayList<EventBase>();

        this.value = value;
        this.dayofWeek = dayOfWeek;
        this.correspondingDayOfWeek = correspondingDayOfWeek;
    }

    /**
     * Adds an event to the days list and keeps track of what time it happens, will check for conflicts
     * @param e: Event to be added
     * @return boolean on success
     */
    public boolean addEvent(EventBase e) {
        //System.out.println(e);
        if (events.size() == 0) {
            //No need to check conflicts
            events.add(e);
            eventCounter++;
            if (e instanceof AppointmentEvent) {
                occupiedTime.add(((AppointmentEvent) e).occupiedTimeSerial);
            }
        }

        else {
            if (e instanceof AppointmentEvent) {
                if (isEventConflict((AppointmentEvent) e)==null) {
                    events.add(e);
                    occupiedTime.add(((AppointmentEvent) e).occupiedTimeSerial);
                    eventCounter++;
                    return true;
                } else {
                    return false;
                }
            }
            else {
                events.add(e);
                eventCounter++;
            }
        }

       /* System.out.println("Event Created Successfully: " + e);
        System.out.println(dayofWeek);
        System.out.println(events.toString());
        System.out.println(this.toString());*/
        return true;
    }

    /**
     * Deletes event in day by event object
     * @param e: Event to be deleted
     * @return boolean on success
     */
    public boolean deleteEvent(EventBase e) {

        if (events.contains(e)) {
            events.remove(e);


            if(e instanceof AppointmentEvent) {
                occupiedTime.remove(((AppointmentEvent) e).occupiedTimeSerial);
                if (((AppointmentEvent) e).siblings != null) {
                    eventCounter -= ((AppointmentEvent) e).siblings.size();
                    //events.removeAll(((AppointmentEvent) e).siblings);
                    for (AppointmentEvent s : ((AppointmentEvent) e).siblings) {
                        s.day.deleteEvent(s);
                        s = null;
                        e = null;
                    }
                }
            }else if(e instanceof  DeadlineEvent){
                events.remove(e);
            }

            eventCounter--;
            return true;
        }
        else {
            return false;
        }
    }


    /**
     * Returns boolean if events have any conflicts
     * @param e: Event to check
     * @return String of time conflict, null on sucess
     */
    public String isEventConflict(AppointmentEvent e) {
        for (String s : occupiedTime) {
            double eventStartTime = Integer.parseInt(s.split(",")[1].split(":")[0]) + Double.parseDouble(s.split(",")[1].split(":")[1]) / 60.0;
            double newEventStartTime = Integer.parseInt(e.occupiedTimeSerial.split(",")[1].split(":")[0]) + Double.parseDouble(e.occupiedTimeSerial.split(",")[1].split(":")[1]) / 60.0;

            double eventEndTime = Integer.parseInt(s.split(",")[2].split(":")[0]) + Double.parseDouble(s.split(",")[2].split(":")[1]) / 60.0;
            double newEventEndTime = Integer.parseInt(e.occupiedTimeSerial.split(",")[2].split(":")[0]) + Double.parseDouble(e.occupiedTimeSerial.split(",")[2].split(":")[1]) / 60.0;

            //new event ends in old event
            if((newEventEndTime>=eventStartTime)&&(newEventStartTime<eventStartTime)) {
                return s;
            }
            //new event starts before old ends
            if((newEventStartTime<eventEndTime)&&(newEventEndTime>eventEndTime)){
                return s;
            }
            //event is inside another
            if((eventStartTime<newEventStartTime)&&(newEventEndTime<eventEndTime)){
                return s;
            }
            /*test*/
            //event is containing the other
            if((newEventStartTime<=eventStartTime)&&(newEventEndTime>=eventEndTime)){
                return s;
            }

            return null;


            //Cases for conflict: Second event starts during event 1, Second event starts and does not finish before event one, third when event is inside another event
        }
        return null;
    }



    @Override
    public String toString() {
        return "\nMyDay{" +
                "events = " + events +
                ", value = " + value +
                ", dayofWeek = " + dayofWeek +
                "}";
    }


    public static void main(String[] args) {
        //TESTING


    }


}
