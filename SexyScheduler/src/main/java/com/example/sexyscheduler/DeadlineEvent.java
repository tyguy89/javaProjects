package com.example.sexyscheduler;

import java.io.Serializable;

public class DeadlineEvent extends EventBase implements Serializable {

    public String time;

    public AppointmentEvent siblingWorkTime;

    boolean linked, workTime;


    public DeadlineEvent(String title, String tag, String time) {
        super(title, tag);
        this.time = time;
    }
}
