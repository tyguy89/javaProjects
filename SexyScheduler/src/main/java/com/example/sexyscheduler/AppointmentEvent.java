package com.example.sexyscheduler;

import java.io.Serializable;
import java.util.ArrayList;

public class AppointmentEvent extends EventBase implements Serializable {
    public String start, end, occupiedTimeSerial;
    public ArrayList<AppointmentEvent> siblings;
    public MyDay day;

    public AppointmentEvent(String title, String start, String end, String tag,MyDay day) {
        super(title, tag);
        this.start = start;
        this.end = end;
        this.day = day;
        occupiedTimeSerial = title + "," + start + "," + end + "," + this.serial; //Every event has unique trait for accesing individuals

    }

    @Override
    public String toString() {
        return "AppointmentEvent{" +
                "start='" + start + '\'' +
                ", end='" + end + '\'' +
                ", occupiedTimeSerial='" + occupiedTimeSerial + '\'' +
                ", day=" + day.value +
                '}';
    }
}
